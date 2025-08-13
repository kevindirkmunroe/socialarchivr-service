package com.bronzegiant.socialarchivr.socialaccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    List<SocialAccount> findByArchiveId(Long archiveId);
    SocialAccount findByArchiveIdAndUsername(Long archiveId, String username);
}
