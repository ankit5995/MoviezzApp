package com.niit.bej.UserMovieService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "movie does NOT exits!")
public class MovieDoesNotExistsException extends Exception{
    public MovieDoesNotExistsException(String message) {
        super(message);
    }
}
