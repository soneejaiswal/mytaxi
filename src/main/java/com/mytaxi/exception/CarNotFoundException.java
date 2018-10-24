package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The car is not found.")
public class CarNotFoundException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -3427288148948321775L;


    public CarNotFoundException()
    {
        super();
    }


    public CarNotFoundException(String message)
    {
        super(message);
    }
}
