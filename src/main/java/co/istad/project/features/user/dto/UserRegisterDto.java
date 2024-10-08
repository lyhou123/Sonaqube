package co.istad.project.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record UserRegisterDto(
        @NotNull(message = "Username is required")
        String userName,
        @Email(message = "Email format is not correct!")
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Password is required")
        String password,
        @NotNull(message = "Confirm Password is required")
        String confirmPassword
)
{ }
