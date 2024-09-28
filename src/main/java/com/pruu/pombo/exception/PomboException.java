package com.pruu.pombo.exception;

import org.springframework.http.HttpStatus;

public class PomboException extends Exception{

    HttpStatus statusCode;

    public PomboException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
