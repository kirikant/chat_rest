package com.chat.chat.services;

import com.chat.chat.dto.UserDto;
import com.chat.chat.entity.Role;
import com.chat.chat.entity.Status;
import com.chat.chat.entity.UserEntity;
import com.chat.chat.repositories.UserRepository;
import com.chat.chat.utils.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, Mapper mapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }


    public Page<UserDto> findAll(int size,int page){
        Page<UserEntity> userEntities = userRepository.findAll(PageRequest
                .of(page, size, Sort.Direction.DESC, "login"));
        Page<UserDto> userDto = userEntities.map(x -> mapper.map(x, UserDto.class));
        return userDto;
    }

    public UserDto findById(Long id) throws EntityNotFoundException{
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no such user"));
        return mapper.map(user,UserDto.class);
    }


    public UserDto save(UserDto user){
        UserEntity entity = mapper.map(user, UserEntity.class);
        entity.setRole(Role.USER);
        entity.setStatus(Status.ACTIVE);
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setLastUpdate(LocalDateTime.now());
        UserEntity savedEntity = userRepository.save(entity);
        return mapper.map(savedEntity,UserDto.class);
    }


    public UserDto update(Long id,UserDto user){
        UserEntity user1 = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("There is no such user"));
        user1.setPassword(encoder.encode(user.getPassword()));
        user1.setLogin(user.getLogin());
        user1.setName(user.getName());
        user1.setBirthDay(user.getBirthDay());
        user1.setEmail(user.getEmail());
        user1.setLastUpdate(LocalDateTime.now());
        user1.setStatus(Status.ACTIVE);
        user1.setRole(Role.USER);
        UserEntity savedEntity = userRepository.save(user1);
        return mapper.map(savedEntity,UserDto.class);
    }


    public UserDto getByLogin(String login){
      return mapper.map(userRepository.findByLogin(login),UserDto.class);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}
