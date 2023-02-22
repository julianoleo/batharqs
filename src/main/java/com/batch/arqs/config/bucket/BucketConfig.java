package com.batch.arqs.config.bucket;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BucketConfig {

    @Value("${localstack.endpoint}")
    private String amazonUrl;

    public static final Logger LOGGER = LogManager.getLogger(BucketConfig.class);

    @Bean
    @Primary
    public AmazonS3 s3client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonUrl, Regions.SA_EAST_1.getName()))
                .withPathStyleAccessEnabled(true).build();
    }
}
