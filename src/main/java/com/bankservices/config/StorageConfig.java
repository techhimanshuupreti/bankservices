package com.bankservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bankservices.utils.PropertiesManager;

@Configuration
public class StorageConfig {

	/*@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;
	
    @Value("${cloud.aws.region.static}")
    private String region;*/
    
	@Autowired
	PropertiesManager propertiesManager;
	
    @Bean
    public AmazonS3 s3Client() {
		 
        AWSCredentials credentials = new BasicAWSCredentials(propertiesManager.getSystemProperty("CLOUD_AWS_CREDENTIALS_ACCESS_KEY"),
        													 propertiesManager.getSystemProperty("CLOUD_AWS_CREDENTIALS_SECRET_KEY"));
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion("ap-south-1").build();
    }

}
