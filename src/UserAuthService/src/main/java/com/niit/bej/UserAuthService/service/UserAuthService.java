package com.niit.bej.UserAuthService.service;

import com.niit.bej.UserAuthService.domain.User;
import com.niit.bej.UserAuthService.exception.UserAlreadyExistException;
import com.niit.bej.UserAuthService.exception.UserNotFoundException;

public interface UserAuthService {
    User login(User user) throws UserNotFoundException;

    User register(User user) throws UserAlreadyExistException;
}
