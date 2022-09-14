package com.ps.service;

import com.ps.dto.requestDTO.LoginRequestDTO;
import com.ps.model.Account;
import com.ps.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Account> loginUser(LoginRequestDTO loginRequestDTO){
        try{
            String username = loginRequestDTO.getUsername();
            String password = loginRequestDTO.getPassword();
            Account account =accountRepository.findByUsername(username);
            return new ResponseEntity<>(account,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
