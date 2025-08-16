package com.bronzegiant.socialarchivr.socialaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/social-accounts")
public class SocialAccountController {

    @Autowired
    private SocialAccountRepository repository;

    @PostMapping
    public SocialAccount save(@RequestBody SocialAccount account) {
        return repository.save(account);
    }

    @GetMapping("/{archiveId}")
    public List<SocialAccount> getAccounts(@PathVariable Long archiveId) {
        return repository.findByArchiveId(archiveId);
    }
    
    @GetMapping("/{archiveId}/by-username")
    public SocialAccount getAccountByUsername(@PathVariable Long archiveId, 
    											@RequestParam String username,
    											@RequestParam SocialMediaPlatform platform) {
        return repository.findByArchiveIdAndUsernameAndPlatform(archiveId, username, platform);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

