package com.niit.bej.UserMovieService.proxy;

import com.niit.bej.UserMovieService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userAuthService", url = "localhost:8089/")
public interface UserProxy {
    @PostMapping("/userAuth/register")
    public ResponseEntity<?> registerProxyUser(@RequestBody User user);
}
