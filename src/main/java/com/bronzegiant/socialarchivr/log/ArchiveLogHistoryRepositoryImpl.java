package com.bronzegiant.socialarchivr.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class ArchiveLogHistoryRepositoryImpl implements ArchiveLogHistoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Value("${spring.profiles.active}")
    private String profile;
    
	public List<ArchiveLog> findLastLogPerPlatform(Long archiveId) {
		String sql;
	
        if ("dev".equals(profile)) {
            sql = "SELECT al.*\n"
            		+ "FROM archive_log al\n"
            		+ "JOIN (\n"
            		+ "    SELECT social_media_platform, social_media_username, MAX(archive_date_completed) AS max_date\n"
            		+ "    FROM archive_log\n"
            		+ "    GROUP BY social_media_platform, social_media_username\n"
            		+ ") sub ON\n"
            		+ "    al.social_media_platform = sub.social_media_platform AND\n"
            		+ "    al.social_media_username = sub.social_media_username AND\n"
            		+ "    al.archive_date_completed = sub.max_date;";
        } else {
            sql = "		SELECT DISTINCT ON (social_media_platform, social_media_username) *\n"
            		+ "		FROM archive_log\n"
            		+ "		ORDER BY social_media_platform, social_media_username, archive_date_completed DESC";
        }

        return em.createNativeQuery(sql, ArchiveLog.class).getResultList();
		
	}

}
