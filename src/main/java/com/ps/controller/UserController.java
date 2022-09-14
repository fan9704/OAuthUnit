package com.ps.controller;

import com.ps.dto.requestDTO.LoginRequestDTO;
import com.ps.model.Account;
import com.ps.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.loginUser(loginRequestDTO);
    }
}
