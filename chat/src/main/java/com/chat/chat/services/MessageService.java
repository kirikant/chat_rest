package com.chat.chat.services;

import com.chat.chat.dto.MessageDto;
import com.chat.chat.entity.MessageEntity;
import com.chat.chat.entity.UserEntity;
import com.chat.chat.repositories.MessageRepository;
import com.chat.chat.repositories.UserRepository;
import com.chat.chat.utils.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, Mapper mapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    public Page<MessageDto> findAllBySenderId(Long id,int size, int page) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        UserEntity user1 = user.orElseThrow(() -> new EntityNotFoundException("There is no such user"));
        Page<MessageDto> messageDtoPage = messageRepository.findAllByLoginReceiver(user1, PageRequest.of(page, size))
                .map(x -> mapper.map(x, MessageDto.class));
        return messageDtoPage;
    }

    public MessageDto findById(Long id) throws EntityNotFoundException{
        Optional<MessageEntity> messageEntity = messageRepository.findById(id);
        MessageEntity messageEntity1 = messageEntity
                .orElseThrow(() -> new EntityNotFoundException("There is no such message"));
        return mapper.map(messageEntity1,MessageDto.class);
    }

   public MessageDto saveMessage(MessageDto messageDto){
       UserEntity userEntity = userRepository.findByLogin(messageDto.getReceiverLogin())
               .orElseThrow(()->new EntityNotFoundException("There is no such user"));
       MessageEntity messageEntity = mapper.map(messageDto, MessageEntity.class);
       messageEntity.setLoginReceiver(userEntity);
       MessageEntity savedEntity = messageRepository.save(messageEntity);
       return mapper.map(savedEntity,MessageDto.class);
   }

   public MessageDto updateMessage(Long id,MessageDto messageDto){
       MessageEntity messageEntity = messageRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("There is no such message"));
       UserEntity userEntity = userRepository.findByLogin(messageDto.getReceiverLogin())
               .orElseThrow(()->new EntityNotFoundException("There is no such user"));
       messageEntity.setDateTime(messageDto.getDateTime());
       messageEntity.setLoginReceiver(userEntity);
       messageEntity.setLoginSender(messageDto.getLoginSender());
       messageEntity.setText(messageDto.getText());
       MessageEntity savedEntity = messageRepository.save(messageEntity);
       return mapper.map(savedEntity,MessageDto.class);
   }

    public void delete(Long id){
        messageRepository.deleteById(id);
    }


}
