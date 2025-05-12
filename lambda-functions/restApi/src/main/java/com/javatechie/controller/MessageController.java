package com.javatechie.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.dto.Message;
import com.javatechie.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Message> addCourse(@RequestBody String newMessageText) {
        Message newMessage = messageService.addNewMessage(newMessageText);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable<Message>> getAllCourses() {
        Iterable<Message> messages = messageService.getMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping(value = "/{messageId}", produces = "application/json")
    public ResponseEntity<Message> getCourseById(@PathVariable String messageId) {
        Optional<Message> course = messageService.getMessageById(messageId);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
