package com.medco.BlogApp.Controller;

import com.medco.BlogApp.DTO.Request.*;
import com.medco.BlogApp.DTO.Response.*;
import com.medco.BlogApp.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<UserSignUpResponseDto> signup(
            @ModelAttribute @Valid UserSignUpRequestDto userSignUpRequestDto

    ) {
        UserSignUpResponseDto userSignUpResponseDto = userService.signup(userSignUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(
            @RequestBody @Valid UserLoginRequestDto userLoginRequestDto
    ) {
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponseDto> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequestDto forgotPasswordRequestDto
    ) {
        ForgotPasswordResponseDto forgotPasswordResponseDto = userService.forgotPassword(forgotPasswordRequestDto);
        return ResponseEntity.ok(forgotPasswordResponseDto);
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<VerifyResetCodeResponseDto> verifyResetCode(
            @RequestBody @Valid VerifyResetCodeRequestDto verifyResetCodeRequestDto
    ) {
        VerifyResetCodeResponseDto verifyResetCodeResponseDto = userService.verifyResetCode(verifyResetCodeRequestDto);
        return ResponseEntity.ok(verifyResetCodeResponseDto);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponseDto> resetPassword(
            @RequestBody @Valid ResetPasswordRequestDto resetPasswordRequestDto
    ) {
        ResetPasswordResponseDto resetPasswordResponseDto = userService.resetPassword(resetPasswordRequestDto);
        return ResponseEntity.ok(resetPasswordResponseDto);
    }

    @PutMapping(
            value    = "/update-profile/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(
            @PathVariable Long id,
            @ModelAttribute @Valid UpdateProfileRequestDto updateProfileRequestDto
    ) {
        UpdateProfileResponseDto updateProfileResponseDto = userService.updateProfile(id, updateProfileRequestDto);
        return ResponseEntity.ok(updateProfileResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserSignUpResponseDto>> getAllUsers() {
        List<UserSignUpResponseDto> userSignUpResponseDto = userService.getAllUsers();
        return ResponseEntity.ok(userSignUpResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSignUpResponseDto> getUserById(@PathVariable Long id) {
        UserSignUpResponseDto userSignUpResponseDto = userService.getUserById(id);
        return ResponseEntity.ok(userSignUpResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
