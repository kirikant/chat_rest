package com.chat.chat.controllers;

import com.chat.chat.dto.MessageDto;
import com.chat.chat.exception.WrongParametersException;
import com.chat.chat.services.MailSenderService;
import com.chat.chat.services.MessageService;
import com.chat.chat.utils.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final Mapper mapper;
    private final MessageService messageService;
    private final MailSenderService mailSenderService;

    public MessageController(MessageService messageService, Mapper mapper, MailSenderService mailSenderService) {
        this.messageService = messageService;
        this.mapper = mapper;
        this.mailSenderService = mailSenderService;

    }

    @GetMapping("/simple/{id}/all")
    public ResponseEntity<?> getAllMessages(@PathVariable Long id,
            @RequestParam Integer page) throws EntityNotFoundException, WrongParametersException {
        if (page<0) throw new WrongParametersException("received parameters are wrong");
        Page<MessageDto> allBySenderId = messageService.findAllBySenderId(id, 5, page);
        return ResponseEntity.ok(allBySenderId);
    }

    @PostMapping("/simple/add")
    public ResponseEntity<?> add(@RequestBody MessageDto messageDto) {
        MessageDto messageDto1 = messageService.saveMessage(messageDto);
        return ResponseEntity.ok(messageDto1);
    }

    @PutMapping("/admin/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody MessageDto messageDto){
        MessageDto messageDto1 = messageService.updateMessage(id,messageDto);
        return ResponseEntity.ok(messageDto1);
    }

    @DeleteMapping("/simple/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException{
        messageService.findById(id);
        messageService.delete(id);
        return ResponseEntity.ok().build();
    }

}
