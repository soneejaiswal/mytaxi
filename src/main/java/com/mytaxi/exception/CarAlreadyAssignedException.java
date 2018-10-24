package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A car has already being assigned to another driver.")
public class CarAlreadyAssignedException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -6307562822969568540L;


    public CarAlreadyAssignedException(String exceptionMessage)
    {
        super(exceptionMessage);
    }

}
