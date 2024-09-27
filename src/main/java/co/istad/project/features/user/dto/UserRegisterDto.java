package co.istad.project.features.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record UserRegisterDto(
        String userName,
        @Email
        String email,
        String password,
        String confirmPassword
)
{ }
