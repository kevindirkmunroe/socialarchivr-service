package com.bronzegiant.socialarchivr.archive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bronzegiant.socialarchivr.job.ArchiveJob;
import com.bronzegiant.socialarchivr.job.ArchiveJobRepository;
import com.bronzegiant.socialarchivr.job.ArchiveJobRequest;
import com.bronzegiant.socialarchivr.job.ArchiveJobService;
import com.bronzegiant.socialarchivr.log.ArchiveLog;
import com.bronzegiant.socialarchivr.log.ArchiveLogHistoryRepository;
import com.bronzegiant.socialarchivr.log.ArchiveLogRepository;
import com.bronzegiant.socialarchivr.post.Post;
import com.bronzegiant.socialarchivr.post.PostRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/archives")
@CrossOrigin(origins = "*")
public class ArchiveController {

    private final ArchiveRepository archiveRepository;
    private final ArchiveLogRepository archiveLogRepository;
    private final PostRepository postRepository;
    private final ArchiveLogHistoryRepository archiveLogHistoryRepository;
    
    private final ArchiveJobRepository archiveJobRepository;
    private final ArchiveJobService archiveJobService;
    
    private static final Logger LOGGER = Logger.getLogger(ArchiveController.class.getName());



    public ArchiveController(ArchiveRepository repository, 
    		ArchiveLogRepository archiveLogRepository,
    		PostRepository postRepository,
    		ArchiveLogHistoryRepository archiveLogHistoryRepository, 
    		ArchiveJobRepository archiveJobRepository,
    		ArchiveJobService archiveJobService) {
        this.archiveRepository = repository;
        this.archiveLogRepository = archiveLogRepository;
		this.postRepository = postRepository;
        this.archiveLogHistoryRepository = archiveLogHistoryRepository;
        this.archiveJobRepository = archiveJobRepository;
        this.archiveJobService = archiveJobService;
    }

    @GetMapping
    public List<Archive> getAllArchives() {
        return archiveRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Archive> getArchiveById(@PathVariable Long id) {
        return archiveRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/logs")
    public List<ArchiveLog> getArchiveLogs(@PathVariable Long id) {
        return archiveLogRepository.findByArchiveId(id);
    }
    
    @GetMapping("/{id}/posts")
    public List<Post> getArchivePosts(@PathVariable Long id) {
        return postRepository.findByArchiveId(id);
    }
    
    
    @GetMapping("{id}/history")
    public ResponseEntity<List<ArchiveLog>> getArchiveHistoryByPlatform(@PathVariable Long id) {
        return ResponseEntity.ok(archiveLogHistoryRepository.findLastLogPerPlatform(id));
    }


    @GetMapping("/user/{userId}")
    public List<Archive> getArchivesByUserId(@PathVariable Long userId) {
        return archiveRepository.findByUserId(userId);
    }    

    @PostMapping
    public Archive createArchive(@RequestBody Archive archive) {
        return archiveRepository.save(archive);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Archive> updateArchive(@PathVariable Long id, @RequestBody Archive updated) {
        return archiveRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setUserId(updated.getUserId());
                    return ResponseEntity.ok(archiveRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteArchive(@PathVariable Long id) {
        return archiveRepository.findById(id)
                .map(existing -> {
                    archiveRepository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/job")
    public ResponseEntity<?> startArchive(@RequestBody ArchiveJobRequest request) {
        ArchiveJob job = new ArchiveJob(request.getUsername(), request.getArchiveId());

        job = archiveJobRepository.save(job);

        archiveJobService.runArchiveJob(job);

        return ResponseEntity.accepted().body(Map.of("jobId", job.getId()));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getJobStatus(@PathVariable Long jobId) {
        LOGGER.log(Level.INFO, "Getting job status for jobId " + jobId);

        
        Optional<ArchiveJob> j = archiveJobRepository.findById(jobId);
        if(!j.isPresent()) {
        	return ResponseEntity.badRequest().build();
        }
        
        ArchiveJob foundJob = j.get();
        return ResponseEntity.ok(foundJob);
    }
}
