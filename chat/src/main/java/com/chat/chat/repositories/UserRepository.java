package com.chat.chat.repositories;

import com.chat.chat.entity.MessageEntity;
import com.chat.chat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
      Optional<UserEntity> findByLogin(String login);
      UserEntity findByIdAndLastUpdate(Long id, LocalDateTime localDateTime);

//      @Query("UPDATE UserEntity SET ")
//      UserEntity
}
