package com.example.tiktok.repositories;

import com.example.tiktok.entities.User;
import com.example.tiktok.models.dto.UserImageDTO;
import com.example.tiktok.models.responses.UserInformationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "update users u set u.bio = :bio,u.nickname = :nickname,u.username = :username where u.id = :userId", nativeQuery = true)
    void updateProfileUser(@Param("username") String username, @Param("nickname") String nickname, @Param("bio") String bio, @Param("userId") Long userId);

    @Query("select u from User u where  u.id = :userId")
    List<User> findByUserId(@Param("userId") Long userId);

    @Query("select u.id,u.nickname,u.username,u.email,u.bio," +
            "ui.id as userImgageId,ui.name as imageName,ui.fileCode," +
            "r.id as roleId,r.name as roleName from User u " +
            "join u.userImages ui " +
            "join u.roles r " +
            "where  u.id = :userId")
    List<Object[]> getInformationByUserId(@Param("userId") Long userId);


}