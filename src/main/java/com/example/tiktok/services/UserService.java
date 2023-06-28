package com.example.tiktok.services;


import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.entities.User;
import com.example.tiktok.exceptions.CreateFailureException;
import com.example.tiktok.exceptions.NotFoundException;
import com.example.tiktok.exceptions.UpdateFailtureException;
import com.example.tiktok.models.requests.users.SignUpRequest;
import com.example.tiktok.models.requests.users.UpdateProfileRequest;
import com.example.tiktok.models.requests.users.UploadImageRequest;
import com.example.tiktok.models.responses.UserInformationResponse;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.LanguageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private ModelMapper mapper;

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);


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
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new CreateFailureException(LanguageUtils.getMessage("message.create.failure"));
        }

    }

    @Transactional
    public void updateProfile(UpdateProfileRequest req, MultipartFile file) throws IOException {
        User user = req.loadToEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            user.setId(userDetails.getUser().getId());
        }

        userImageService.uploadImage(UploadImageRequest.builder().userId(user.getId()).file(file).build());
        try {
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            // Set the new values for the desired fields, including null values
            existingUser.setUsername(user.getUsername());
            existingUser.setBio(user.getBio());
            existingUser.setNickname(user.getNickname());

            // Save the modified entity
            userRepository.save(existingUser);
        } catch (DataAccessException e) {
            throw new UpdateFailtureException(LanguageUtils.getMessage("message.update.failure"));
        }


    }

    public UserInformationResponse getUserInformation(Long userId) {
        UserInformationResponse res = new UserInformationResponse();
        try {
            List<User> users = userRepository.findByUserId(userId);
            if (users.size() == 0)
                throw new NotFoundException("message.not.found");
            res = mapper.map(users.get(0), UserInformationResponse.class);

        } catch (DataAccessException e) {
            LOGGER.error("Error executing service, getUserInformation method: ", e.getMessage());
            throw new NotFoundException("message.not.found");
        }
        return res;
    }
}
