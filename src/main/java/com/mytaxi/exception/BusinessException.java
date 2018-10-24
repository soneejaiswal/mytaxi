package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "car is already assigned to other dirver ...")
public class BusinessException extends Exception{

  private static final long serialVersionUID = -454879615786144548L;

    public BusinessException(String message) {
        super(message);
    }
}
