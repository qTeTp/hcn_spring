package com.hcn.hcn.repository;

import com.hcn.hcn.model.ChatHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatHeaderRepository extends JpaRepository<ChatHeader, Long> {

    @Query("SELECT c FROM ChatHeader c WHERE c.from_id = :userId OR c.to_id = :userId")
    List<ChatHeader> findByUserId(@Param("userId") String userId);
}
