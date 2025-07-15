package com.bronzegiant.socialarchivr.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findByUserId(Long id);
    Archive findByName(String name);
}

