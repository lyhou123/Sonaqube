package co.istad.project.features.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthRequest(

    @NotNull(message = "Email is required")
    @Email(message = "Email format is not correct!")
    String email,

    @NotNull(message = "Password is required")
    String password
            )
{
}
