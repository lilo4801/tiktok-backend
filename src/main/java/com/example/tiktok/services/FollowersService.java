package com.example.tiktok.services;

import com.example.tiktok.entities.Followers;
import com.example.tiktok.entities.User;
import com.example.tiktok.exceptions.CreateFailureException;
import com.example.tiktok.exceptions.NotFoundException;
import com.example.tiktok.models.dto.UserDTO;
import com.example.tiktok.repositories.FollowersRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.AuthUtil;
import com.example.tiktok.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowersService {

    @Autowired
    private FollowersRepository followersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void follow(Long toUserId) {
        Long currentUserId = AuthUtil.getLoggedUserId();

        User currentUser = User.builder().id(currentUserId).build();
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new NotFoundException("message.not.found"));

        Followers followers = followersRepository.findFirstByFromAndTo(currentUser, toUser);
        if (followers != null) {
            followers.setDeleted(!followers.isDeleted());
            followersRepository.save(followers);
            return;
        }

        try {
            followersRepository.save(Followers.builder()
                    .from(currentUser)
                    .to(toUser)
                    .build());
        } catch (DataAccessException e) {
            throw new CreateFailureException("message.create.failure");
        }
    }

    public List<UserDTO> getFollowers(Long userId) {
        List<User> users = followersRepository.getFollowersByUserId(userId);

        return users
                .stream()
                .map(userMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getFollowings(Long userId) {
        List<User> users = followersRepository.getFollowingByUserId(userId);

        return users
                .stream()
                .map(userMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
