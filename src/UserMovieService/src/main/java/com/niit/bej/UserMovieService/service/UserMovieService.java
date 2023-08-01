package com.niit.bej.UserMovieService.service;

import com.niit.bej.UserMovieService.domain.User;
import com.niit.bej.UserMovieService.exception.MovieAlreadyExistException;
import com.niit.bej.UserMovieService.exception.MovieDoesNotExistsException;
import com.niit.bej.UserMovieService.exception.UserAlreadyExistException;
import com.niit.bej.UserMovieService.exception.UserNotFoundException;

public interface UserMovieService {
    User registerUser(User user) throws UserAlreadyExistException;

    String addMovieToFavourites(String movieId, User user) throws MovieAlreadyExistException;

    User deleteMovieFromFavourites(String movieId, User user) throws MovieDoesNotExistsException;

    String addMovieToWishList(String movieId, User user) throws MovieAlreadyExistException;

    User deleteMovieFromWishList(String movieId, User user) throws MovieDoesNotExistsException;

    User getUser(String emailId) throws UserNotFoundException;
}
