package com.javatechie.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.dto.Message;
import com.javatechie.repo.MessageRepo;

@Service
public class MessageService {

    @Autowired 
    private MessageRepo messageRepo;

    public Message addNewMessage(String newMessageText) {
        Message newMessage = Message.builder().message(newMessageText).build();
        return messageRepo.save(newMessage);
    }

    public Optional<Message> getMessageById(String messageId) {
        return messageRepo.findById(messageId);
    }

    public Iterable<Message> getMessages() {
        return messageRepo.findAll();
    }

}
