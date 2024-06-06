package com.hcn.hcn.repository;

import com.hcn.hcn.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByHeaderId(Long headerId); //headerId로 메시지를 검색
}
