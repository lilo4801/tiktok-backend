package com.example.tiktok.repositories;

import com.example.tiktok.entities.Followers;
import com.example.tiktok.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long> {

    @Query("select u from User u join Followers fl on fl.from.id = u.id where fl.to.id = :userId  and fl.deleted = false ")
    List<User> getFollowersByUserId(@Param("userId") Long userId);

    @Query("select u from User u join Followers fl on fl.to.id = u.id where fl.from.id = :userId  and fl.deleted = false ")
    List<User> getFollowingByUserId(@Param("userId") Long userId);

    @Query("select count(*) from User u join Followers fl on fl.from.id = u.id where fl.to.id = :userId and fl.deleted = false ")
    Long countNumberOfFollowerByUserId(@Param("userId") Long userId);

    @Query("select count(*) from User u join Followers fl on fl.to.id = u.id where fl.from.id = :userId  and fl.deleted = false ")
    Long countNumberOfFollowingByUserId(@Param("userId") Long userId);

    boolean existsByFromAndToAndDeleted(User from, User to, boolean deleted);

    boolean existsByFromAndTo(User from, User to);

    Followers findFirstByFromAndTo(User from, User to);

}
