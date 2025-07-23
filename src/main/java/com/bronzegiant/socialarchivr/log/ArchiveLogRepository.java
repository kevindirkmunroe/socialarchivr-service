package com.bronzegiant.socialarchivr.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArchiveLogRepository extends JpaRepository<ArchiveLog, Long> {
	List<ArchiveLog> findByArchiveId(Long archiveId);
	
	@Query(value = """
		SELECT al.* FROM archive_log al
        INNER JOIN (
            SELECT social_media_account, MAX(archive_date_completed) AS archive_date_completed
            FROM archive_log
            WHERE archive_id = :archiveId
            GROUP BY social_media_account, social_media_username
        ) latest ON al.social_media_account = latest.social_media_account 
				      AND al.archive_date_completed = latest.archive_date_completed	
			""", nativeQuery = true)
	List<ArchiveLog> findLastLogPerPlatform(@Param("archiveId") Long archiveId);
}
