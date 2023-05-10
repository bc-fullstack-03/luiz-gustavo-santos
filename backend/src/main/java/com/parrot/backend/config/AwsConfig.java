package com.parrot.backend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
  @Value("${app-config.api-env}")
  private String apiEnv;

  @Bean
  public AmazonS3 amazonS3() {
    String localStackUrl = "http://parrot-localstack:4566";

    if(apiEnv.equals("local")) {
      localStackUrl = "https://s3.localhost.localstack.cloud:4566";
    }
    return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("mykey", "mykey")))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackUrl, Regions.US_WEST_2.getName()))
            .withPathStyleAccessEnabled(true)
            .build();
  }
}
