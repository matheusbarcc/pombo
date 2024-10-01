package com.pruu.pombo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class PomboException extends Exception{

    HttpStatus statusCode;

    public PomboException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
