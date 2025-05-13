package com.medco.BlogApp.ServiceImpl;

import com.medco.BlogApp.DTO.Request.*;
import com.medco.BlogApp.DTO.Response.*;
import com.medco.BlogApp.Entity.User;
import com.medco.BlogApp.Repository.UserRepository;
import com.medco.BlogApp.Service.EmailService;
import com.medco.BlogApp.Service.FileStorageService;
import com.medco.BlogApp.Service.UserService;
import com.medco.BlogApp.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository     userRepository;
    private final PasswordEncoder    passwordEncoder;
    private final EmailService       emailService;
    private final FileStorageService fileStorageService;

    @Override
    public UserSignUpResponseDto signup(UserSignUpRequestDto userSignUpRequestDto) {


        if (userRepository.existsByEmail(userSignUpRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if (!userSignUpRequestDto.getPassword().equals(userSignUpRequestDto.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        String imgPath = null;
        if (userSignUpRequestDto.getImage() != null && !userSignUpRequestDto.getImage().isEmpty()) {
            imgPath = fileStorageService.saveProfileImage(userSignUpRequestDto.getImage());
        }

        User user = new User();
        user.setUsername(userSignUpRequestDto.getUsername());
        user.setFirstName(userSignUpRequestDto.getFirstName());
        user.setLastName(userSignUpRequestDto.getLastName());
        user.setEmail(userSignUpRequestDto.getEmail());
        user.setPhoneNumber(userSignUpRequestDto.getPhoneNumber());
        user.setBio(userSignUpRequestDto.getBio());
        user.setImagePath(imgPath);
        user.setPassword(passwordEncoder.encode(userSignUpRequestDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());


        User saved = userRepository.save(user);

        return mapToSignUpDto(saved);

    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail());
        if (user == null) throw new UserNotFoundException("User not found");
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Invalid credentials");
        }
        return new UserLoginResponseDto(user.getId(), user.getEmail());
    }

    @Override
    public ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        User user = userRepository.findByEmail(forgotPasswordRequestDto.getEmail());
        if (user == null) throw new UserNotFoundException("User not found");

        String code = UUID.randomUUID().toString().substring(0, 6);
        user.setPasswordResetCode(code);
        user.setResetCodeExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        String subject = "Password reset code";
        String body    = "<p>Your reset code: <strong>" + code + "</strong><p>Expires in 10 minutes.</p>";
        emailService.sendEmail(user.getEmail(), subject, body);

        return new ForgotPasswordResponseDto("Password reset email sent");
    }

    @Override
    public VerifyResetCodeResponseDto verifyResetCode(VerifyResetCodeRequestDto verifyResetCodeRequestDto) {
        User user = userRepository.findByEmail(verifyResetCodeRequestDto.getEmail());
        if (user == null) throw new UserNotFoundException("User not found");
        if (user.getResetCodeExpiry() == null || user.getResetCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new InvalidResetCodeException("Reset code expired");
        }
        if (!user.getPasswordResetCode().equals(verifyResetCodeRequestDto.getCode())) {
            throw new InvalidResetCodeException("Invalid reset code");
        }
        return new VerifyResetCodeResponseDto("Reset code is valid");
    }

    @Override
    public ResetPasswordResponseDto resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        User user = userRepository.findByEmail(resetPasswordRequestDto.getEmail());
        if (user == null) throw new UserNotFoundException("User not found");
        if (!resetPasswordRequestDto.getNewPassword().equals(resetPasswordRequestDto.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }
        if (user.getResetCodeExpiry() == null || user.getResetCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new InvalidResetCodeException("Reset code expired");
        }
        if (!user.getPasswordResetCode().equals(resetPasswordRequestDto.getResetCode())) {
            throw new InvalidResetCodeException("Invalid reset code");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
        user.setPasswordResetCode(null);
        user.setResetCodeExpiry(null);
        userRepository.save(user);

        return new ResetPasswordResponseDto("Password has been reset");
    }

    @Override
    public UpdateProfileResponseDto updateProfile(Long userId, UpdateProfileRequestDto updateProfileRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        //image replace
        if (updateProfileRequestDto.getImage() != null && !updateProfileRequestDto.getImage().isEmpty()) {
            user.setImagePath(fileStorageService.saveProfileImage(updateProfileRequestDto.getImage()));
        }

        //Check email uniqueness
        String newEmail = updateProfileRequestDto.getEmail().trim().toLowerCase();
        if (!newEmail.equals(user.getEmail())) {
            // if another user already has this email, block it
            if (userRepository.existsByEmail(newEmail)) {
                throw new EmailAlreadyExistsException("Email already in use by another account");
            }
            user.setEmail(newEmail);
        }

        //Update the rest of the profile fields ———
        user.setFirstName(updateProfileRequestDto.getFirstName());
        user.setLastName(updateProfileRequestDto.getLastName());
        user.setPhoneNumber(updateProfileRequestDto.getPhoneNumber());
        user.setBio(updateProfileRequestDto.getBio());
        user.setUpdatedAt(LocalDateTime.now());


        if (updateProfileRequestDto.getNewPassword() != null && !updateProfileRequestDto.getNewPassword().isBlank()) {
            if (!passwordEncoder.matches(updateProfileRequestDto.getCurrentPassword(), user.getPassword())) {
                throw new PasswordMismatchException("Current password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(updateProfileRequestDto.getNewPassword()));
        }

        User updated = userRepository.save(user);
        return mapToUpdateDto(updated);
    }

    @Override
    public List<UserSignUpResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToSignUpDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserSignUpResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToSignUpDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ─── Helpers ───────────────────────────────────────

    private UserSignUpResponseDto mapToSignUpDto(User user) {

        String imageUrl = null;
        if (user.getImagePath() != null) {
            String filename = Paths.get(user.getImagePath()).getFileName().toString();

            //imageUrl = "http://192.168.100.222:8900/profile-pictures/" + filename;  //manually

            //Dynamically
            imageUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/profile-pictures/")
                    .path(filename)
                    .toUriString();
        }

        return new UserSignUpResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBio(),
                imageUrl
        );
    }

    private UpdateProfileResponseDto mapToUpdateDto(User user) {

        String imageUrl = null;
        if (user.getImagePath() != null) {
            String filename = Paths.get(user.getImagePath()).getFileName().toString();

            //imageUrl = "http://192.168.100.222:8900/profile-pictures/" + filename;  //manually

            //Dynamically
            imageUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/profile-pictures/")
                    .path(filename)
                    .toUriString();
        }

        return new UpdateProfileResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBio(),
                imageUrl
        );
    }
}
