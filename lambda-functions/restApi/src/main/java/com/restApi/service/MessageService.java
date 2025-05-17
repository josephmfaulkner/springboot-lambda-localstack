package com.restApi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restApi.dto.Message;
import com.restApi.repo.MessageRepo;

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
