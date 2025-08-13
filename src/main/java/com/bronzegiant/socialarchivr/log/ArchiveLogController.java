package com.bronzegiant.socialarchivr.log;

import com.bronzegiant.socialarchivr.SocialMediaPlatform;
import com.bronzegiant.socialarchivr.archive.Archive;
import com.bronzegiant.socialarchivr.archive.ArchiveRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/archiveLogs")
public class ArchiveLogController {

    private final ArchiveLogRepository archiveLogRepository;
    private final ArchiveRepository archiveRepository;

    public ArchiveLogController(
    		ArchiveLogRepository archiveLogRepository, 
    		ArchiveRepository archiveRepository) {
        this.archiveLogRepository = archiveLogRepository;
        this.archiveRepository = archiveRepository;
    }

    @GetMapping
    public List<ArchiveLog> getAll() {
        return archiveLogRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchiveLog> getById(@PathVariable Long id) {
        return archiveLogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArchiveLog> create(@RequestBody ArchiveLogRequest request) {

    	Archive target = archiveRepository.findByName(request.getArchiveName());
    	if(target == null) {
    		return ResponseEntity.badRequest().build();
    	}
    	
        ArchiveLog archiveLog = new ArchiveLog(target, 
        		request.getArchiveTriggerType(),
        		request.getSocialMediaPlatform(),
        		request.getSocialMediaUsername());
        archiveLogRepository.save(archiveLog);

        return ResponseEntity.ok(archiveLog);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ArchiveLog> markComplete(@PathVariable Long id) {
        Optional<ArchiveLog> logOpt = archiveLogRepository.findById(id);
        if (logOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ArchiveLog log = logOpt.get();
        log.setArchiveDateCompleted(java.time.LocalDateTime.now());
        archiveLogRepository.save(log);

        return ResponseEntity.ok(log);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!archiveLogRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        archiveLogRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // DTO for request body
    public static class ArchiveLogRequest {
    	private String archiveName;
    	private String archiveTriggerType;
    	private SocialMediaPlatform socialMediaPlatform;
    	private String socialMediaUsername;

        // Getters and Setters
        public String getArchiveName() {
            return archiveName;
        }
        public void setArchiveName(String archiveName) {
            this.archiveName = archiveName;
        }
		public String getArchiveTriggerType() {
			return archiveTriggerType;
		}
		public void setArchiveTriggerType(String archiveTriggerType) {
			this.archiveTriggerType = archiveTriggerType;
		}
		public SocialMediaPlatform getSocialMediaPlatform() {
			return socialMediaPlatform;
		}
		public void setSocialMediaAccount(SocialMediaPlatform socialMediaPlatform) {
			this.socialMediaPlatform = socialMediaPlatform;
		}
		public String getSocialMediaUsername() {
			return socialMediaUsername;
		}
		public void setSocialMediaUsername(String socialMediaUsername) {
			this.socialMediaUsername = socialMediaUsername;
		}
    }
}

