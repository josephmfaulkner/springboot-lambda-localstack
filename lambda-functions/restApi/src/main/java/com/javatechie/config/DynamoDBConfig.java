package com.javatechie.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
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
public class DynamoDBConfig {

    @Value("${aws.endpoint}")
    private String AWS_ENDPOINT;

    @Value("${aws.region}")
    private String AWS_REGION;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
        if(AWS_REGION.isBlank() || AWS_ENDPOINT.isBlank())
        {
            return AmazonDynamoDBClientBuilder.standard().build();
        }
        return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration(
                AWS_ENDPOINT, 
                AWS_REGION
            )
        )
        .build();
	}
}