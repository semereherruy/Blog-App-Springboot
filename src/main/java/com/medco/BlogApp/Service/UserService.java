package com.medco.BlogApp.Service;

import com.medco.BlogApp.DTO.Request.*;
import com.medco.BlogApp.DTO.Response.*;
import com.medco.BlogApp.Entity.User;
import org.hibernate.sql.Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserSignUpResponseDto signup(UserSignUpRequestDto userSignUpRequestDto);
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);
    ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
    VerifyResetCodeResponseDto verifyResetCode(VerifyResetCodeRequestDto verifyResetCodeRequestDto);
    ResetPasswordResponseDto resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);
    UpdateProfileResponseDto    updateProfile(Long userId, UpdateProfileRequestDto updateProfileRequestDto);
    List<UserSignUpResponseDto> getAllUsers();
    UserSignUpResponseDto      getUserById(Long id);
    void deleteUser(Long id);
}
