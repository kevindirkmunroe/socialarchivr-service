package com.bronzegiant.socialarchivr.log;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface ArchiveLogHistoryRepository {
	List<ArchiveLog> findLastLogPerPlatform(@Param("archiveId") Long archiveId);

}
