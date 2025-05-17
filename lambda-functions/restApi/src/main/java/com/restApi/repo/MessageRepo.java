package com.restApi.repo;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.restApi.dto.Message;

@EnableScan
public interface MessageRepo extends CrudRepository<Message, String> {
    Optional<Message> findById(String id);
}