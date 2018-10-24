package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "CarCriteria not set properly")
public class InvalidCriteriaException extends Exception {

  private static final long serialVersionUID = -9116205588595264524L;

    public InvalidCriteriaException(String message) {
        super(message);
    }
}

