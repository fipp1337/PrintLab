package com.printlab.orderservice.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final AppErrorCode errorCode;

    private AppException(AppErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static AppException of(AppErrorCode errorCode, Object... args) {
        String message = args.length == 0 ? errorCode.getMessage() : errorCode.getMessage().formatted(args);
        return new AppException(errorCode, message);
    }

}
