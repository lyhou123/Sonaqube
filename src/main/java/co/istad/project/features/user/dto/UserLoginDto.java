package co.istad.project.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record UserLoginDto(
        @Email(message = "Email should be valid")
        String email,

        String password
) {
}
