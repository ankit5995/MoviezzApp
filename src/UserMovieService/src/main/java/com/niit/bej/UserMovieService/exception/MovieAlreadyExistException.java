package com.niit.bej.UserMovieService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "movie is already exits!")
public class MovieAlreadyExistException extends Exception {
    public MovieAlreadyExistException(String message) {
        super(message);
    }
}
