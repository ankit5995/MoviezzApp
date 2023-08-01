package com.niit.bej.UserAuthService.security;

import com.niit.bej.UserAuthService.domain.User;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String, String> generateToken(User user);
}
