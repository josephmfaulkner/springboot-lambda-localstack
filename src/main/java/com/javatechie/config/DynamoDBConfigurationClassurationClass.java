package com.javatechie.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.javatechie.repo.MessageRepo;

@Configuration
@EnableDynamoDBRepositories(
    basePackageClasses = MessageRepo.class
)
public class DynamoDBConfigurationClassurationClass {

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB config = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                        /*System.getenv("LOCALSTACKURL")*/ "https://localhost.localstack.cloud:4566", 
                        /*System.getenv("REGION")*/"us-west-2"
                    )
                )
                .build();
        return config;

	}
}