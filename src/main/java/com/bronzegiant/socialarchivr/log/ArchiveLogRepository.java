package com.bronzegiant.socialarchivr.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveLogRepository extends JpaRepository<ArchiveLog, Long> {
	List<ArchiveLog> findByArchiveId(Long id);
}
