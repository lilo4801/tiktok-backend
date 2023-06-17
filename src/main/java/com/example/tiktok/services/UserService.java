package com.example.tiktok.services;

import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.entities.User;
import com.example.tiktok.exceptions.CreateFailureException;
import com.example.tiktok.models.requests.users.SignUpRequest;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public UserDetails loadUserById(Long parseInt) {
        User user = userRepository.findById(parseInt).orElseThrow(
                () -> new UsernameNotFoundException("User " + parseInt + " not found."));
        return new CustomUserDetails(user);
    }

    public void register(SignUpRequest req) {
        User user = req.loadToEntity();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User newUser = userRepository.save(user);
        if (newUser.getId() == null)
            throw new CreateFailureException(LanguageUtils.getMessage("message.create.failure"));
    }

}
