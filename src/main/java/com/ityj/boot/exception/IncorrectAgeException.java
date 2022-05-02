package com.ityj.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "年龄输入错误！")
public class IncorrectAgeException extends RuntimeException {
    public IncorrectAgeException(String msg) {
        super(msg);
    }

    public IncorrectAgeException() {
        super();
    }
}
