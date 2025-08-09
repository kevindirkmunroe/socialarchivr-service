package com.bronzegiant.socialarchivr.user.profileimage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bronzegiant.socialarchivr.user.User;

import java.util.Optional;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {

    Optional<UserProfileImage> findByUser(User user);

    void deleteByUser(User user);
}
