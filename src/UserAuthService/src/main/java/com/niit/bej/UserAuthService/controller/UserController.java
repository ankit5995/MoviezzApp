package com.niit.bej.UserAuthService.controller;

import com.niit.bej.UserAuthService.domain.User;
import com.niit.bej.UserAuthService.exception.UserAlreadyExistException;
import com.niit.bej.UserAuthService.exception.UserNotFoundException;
import com.niit.bej.UserAuthService.security.SecurityTokenGenerator;
import com.niit.bej.UserAuthService.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/userAuth")
public class UserController {
    private final UserAuthService userAuthService;
    private final SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserAuthService userAuthService, SecurityTokenGenerator securityTokenGenerator) {
        this.userAuthService = userAuthService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registerUser = userAuthService.register(user);
            return new ResponseEntity<>(registerUser, HttpStatus.OK);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loginUser = userAuthService.login(user);
            Map<String, String> tokenMap = securityTokenGenerator.generateToken(loginUser);
            //token = adasabjkba.aasa.asfafaf
            System.out.println("value: "+tokenMap.values());
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
