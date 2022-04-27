package com.chat.chat.repositories;

import com.chat.chat.entity.MessageEntity;
import com.chat.chat.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {

    Page<MessageEntity> findAllByLoginReceiver(UserEntity userEntity, Pageable pageable);
}
