package com.bronzegiant.socialarchivr.socialaccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    List<SocialAccount> findByArchiveId(Long archiveId);
    SocialAccount findByArchiveIdAndUsernameAndPlatform(Long archiveId, String username, SocialMediaPlatform platform);
}
