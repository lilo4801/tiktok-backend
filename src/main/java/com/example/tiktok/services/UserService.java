package com.example.tiktok.services;

import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.entities.User;
import com.example.tiktok.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(int parseInt) {
        User user = userRepository.findById(parseInt).orElseThrow(
                () -> new UsernameNotFoundException("User " + parseInt + " not found."));
        return new CustomUserDetails(user);
    }
}
