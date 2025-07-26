package com.bronzegiant.socialarchivr.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArchiveLogRepository extends JpaRepository<ArchiveLog, Long> {
	List<ArchiveLog> findByArchiveId(Long archiveId);
	
	@Query(value = """
		SELECT DISTINCT ON (social_media_platform, social_media_username) *
		FROM archive_log
		ORDER BY social_media_platform, social_media_username, archive_date_completed DESC	
			""", nativeQuery = true)
	List<ArchiveLog> findLastLogPerPlatform(@Param("archiveId") Long archiveId);
}
