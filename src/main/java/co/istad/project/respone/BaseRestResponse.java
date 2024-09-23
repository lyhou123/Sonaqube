package co.istad.project.respone;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseRestResponse<T>(
        LocalDateTime timestamp,
        int status,
        T data,
        String message



) {
}
