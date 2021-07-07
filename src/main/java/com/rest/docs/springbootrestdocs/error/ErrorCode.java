package com.rest.docs.springbootrestdocs.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error")
    ;

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}