package com.bronzegiant.socialarchivr.post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long id);
    List<Post> findByArchiveId(Long archiveId);
}

