package com.niit.bej.UserAuthService.service;

import com.niit.bej.UserAuthService.domain.User;
import com.niit.bej.UserAuthService.exception.UserAlreadyExistException;
import com.niit.bej.UserAuthService.exception.UserNotFoundException;
import com.niit.bej.UserAuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;

    @Autowired
    public UserAuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(User user) throws UserNotFoundException {
        if (userRepository.findById(user.getEmailId()).isPresent()) {
            User loggedInUser = userRepository.findById(user.getEmailId()).get();
            if (loggedInUser.getPassword().equals(user.getPassword()) && loggedInUser.getEmailId().equals(user.getEmailId())) {
                return loggedInUser;
            } else {
                throw new UserNotFoundException("User is not found!");
            }
        } else {
            throw new UserNotFoundException("User not found!");
        }
    }

    @Override
    public User register(User user) throws UserAlreadyExistException {
        Optional<User> userOptional = userRepository.findById(user.getEmailId());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("user is Already Exist!");
        } else {
            return userRepository.save(user);
        }
    }
}
