package com.bronzegiant.socialarchivr.user.profileimage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bronzegiant.socialarchivr.user.User;
import com.bronzegiant.socialarchivr.user.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserProfileImageService {

    private final UserProfileImageRepository imageRepository;
    private final UserRepository userRepository;

    public UserProfileImageService(UserProfileImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserProfileImage uploadProfileImage(Long userId, MultipartFile imageFile) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Delete existing image if present
        imageRepository.findByUser(user).ifPresent(imageRepository::delete);
        imageRepository.flush();

        // Create new UserProfileImage entity
        UserProfileImage profileImage = new UserProfileImage();
        profileImage.setUser(user);
        profileImage.setFileName(imageFile.getOriginalFilename());
        profileImage.setContentType(imageFile.getContentType());
        profileImage.setImageData(imageFile.getBytes());

        return imageRepository.save(profileImage);
    }

    public Optional<UserProfileImage> getProfileImage(Long userId) {
        return userRepository.findById(userId)
                .flatMap(imageRepository::findByUser);
    }
}
