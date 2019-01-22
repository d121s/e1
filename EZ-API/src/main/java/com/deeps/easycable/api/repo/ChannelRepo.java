package com.deeps.easycable.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.Channel;

@Repository
public interface ChannelRepo extends JpaRepository<Channel,Long>{
	
	Page<Channel> findByNameContaining(String name,Pageable pageable);

	Page<Channel> findByGenre(String genre,Pageable pageable);
	
	Page<Channel> findByType(String type,Pageable pageable);
	
	Page<Channel> findByBroadcastername(String broadcastername,Pageable pageable);
	
	Page<Channel> findByLanguage(String language,Pageable pageable);
	
}
