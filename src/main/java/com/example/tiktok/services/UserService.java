package com.example.tiktok.services;


import com.example.tiktok.common.ImageFolder;
import com.example.tiktok.common.UserRole;
import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.entities.Image;
import com.example.tiktok.entities.Role;
import com.example.tiktok.entities.User;
import com.example.tiktok.exceptions.CreateFailureException;
import com.example.tiktok.exceptions.NotFoundException;
import com.example.tiktok.exceptions.UpdateFailtureException;
import com.example.tiktok.models.dto.ImageDTO;
import com.example.tiktok.models.dto.UserImageDTO;
import com.example.tiktok.models.requests.users.SignUpRequest;
import com.example.tiktok.models.requests.users.UpdateProfileRequest;
import com.example.tiktok.models.responses.UserInformationResponse;
import com.example.tiktok.models.responses.UserProfileResponse;
import com.example.tiktok.repositories.FollowersRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.AuthUtil;
import com.example.tiktok.utils.FileUploadUtils;
import com.example.tiktok.utils.LanguageUtils;
import com.example.tiktok.utils.TextUtils;
import com.example.tiktok.utils.mapper.ImageMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowersRepository followersRepository;

    @Autowired
    private ImageMapper imageMapper;

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
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().id(UserRole.ROLE_CUSTOMER_ID).build());
        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new CreateFailureException(LanguageUtils.getMessage("message.create.failure"));
        }

    }

    @Transactional
    public void updateProfile(UpdateProfileRequest req) throws IOException {


        Long currentUserId = AuthUtil.getLoggedUserId();

        ImageDTO imageDTO = FileUploadUtils.saveFile(req.getFile(), ImageFolder.USER_FOLDER);
        Image image = imageMapper.mapToEntity(imageDTO);

        try {
            User existingUser = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new NotFoundException("message.not.found"));

            // Set the new values for the desired fields, including null values
            if (!TextUtils.isNullOrEmpty(req.getUsername())) {
                existingUser.setUsername(req.getUsername());
            }
            if (!TextUtils.isNullOrEmpty(req.getBio())) {
                existingUser.setBio(req.getBio());
            }
            if (!TextUtils.isNullOrEmpty(req.getNickname())) {
                existingUser.setNickname(req.getNickname());
            }
            existingUser.setAvatar(image);

            // Save the modified entity
            userRepository.save(existingUser);
        } catch (DataAccessException e) {
            throw new UpdateFailtureException("message.update.failure");
        }


    }

    public UserProfileResponse getUserProfile(Long userId) {
        Long currentUserId = AuthUtil.getLoggedUserId();
        User currentUser = User.builder().id(currentUserId).build();

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("message.not.found"));

        ImageDTO avatar = null;

        if (existingUser.getAvatar() != null) {
            avatar = imageMapper.mapToDTO(existingUser.getAvatar());
        }

        return UserProfileResponse.builder()
                .email(existingUser.getEmail())
                .bio(existingUser.getBio())
                .avatar(avatar)
                .nickname(existingUser.getNickname())
                .username(existingUser.getUsername())
                .id(existingUser.getId())
                .numberOfFollower(followersRepository.countNumberOfFollowerByUserId(userId))
                .numberOfFollowing(followersRepository.countNumberOfFollowingByUserId(userId))
                .isFollowed(followersRepository.existsByFromAndToAndDeleted(currentUser, existingUser, false))
                .build();

    }

    public UserInformationResponse getUserInformation() {

        Long currentUserId = AuthUtil.getLoggedUserId();


        User existingUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("message.not.found"));

        ImageDTO avatar = null;

        if (existingUser.getAvatar() != null) {
            avatar = imageMapper.mapToDTO(existingUser.getAvatar());
        }

        return UserInformationResponse.builder()
                .email(existingUser.getEmail())
                .bio(existingUser.getBio())
                .avatar(avatar)
                .nickname(existingUser.getNickname())
                .username(existingUser.getUsername())
                .id(existingUser.getId())
                .roles(existingUser.getRoles())
                .build();

    }
}
