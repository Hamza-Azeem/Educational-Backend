package com.education.educational_platform.exception;

import java.time.LocalDateTime;

public record Error (
        String message,
        LocalDateTime timestamp,
        int code
) {

}
