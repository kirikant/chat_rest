package com.chat.chat.controllers;

import com.chat.chat.aspect.NeedToLog;
import com.chat.chat.dto.UserDto;
import com.chat.chat.exception.WrongParametersException;
import com.chat.chat.services.MailSenderService;
import com.chat.chat.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping(value = "/user")
public class UserController {


    private final UserService userService;
    private final MailSenderService mailSenderService;

    public UserController(UserService userService, MailSenderService mailSenderService) {
        this.userService = userService;
        this.mailSenderService = mailSenderService;
    }

    @NeedToLog
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(
            @RequestParam Integer page) throws WrongParametersException {
        if (page<0) throw new WrongParametersException("received parameters are wrong");
        Page<UserDto> all = userService.findAll(5, page);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) throws EntityNotFoundException {
        UserDto user = userService.findById(id);
       return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody UserDto userDto) {
        UserDto user = userService.save(userDto);
        mailSenderService.send(userDto.getLogin()+" you are successfully registered",
                userDto.getLogin(), userDto.getEmail());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.update(id,userDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        userService.findById(id);
        userService.delete(id);
    return ResponseEntity.ok().build();
    }


}
