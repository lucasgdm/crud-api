package dev.mitri.api.configuration

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DynamoDBConfig {
    @Value("\${aws.accesskey}")
    private val awsAccessKey: String? = null

    @Value("\${aws.secretkey}")
    private val awsSecretKey: String? = null

    @Value("\${aws.region}")
    private val awsRegion: String? = null

    fun amazonAWSCredentialsProvider(): AWSCredentialsProvider? {
        return AWSStaticCredentialsProvider(amazonAWSCredentials())
    }

    fun amazonAWSCredentials(): AWSCredentials? {
        return BasicAWSCredentials(awsAccessKey, awsSecretKey)
    }

    @Bean
    fun dynamoDBMapperConfig(): DynamoDBMapperConfig? {
        return DynamoDBMapperConfig.Builder()
            .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
            .build()
    }

    @Bean
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB?, config: DynamoDBMapperConfig?): DynamoDBMapper? {
        return DynamoDBMapper(amazonDynamoDB, config)
    }

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB? {
        return AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(amazonAWSCredentialsProvider())
            .withRegion(Regions.fromName(awsRegion)).build()
    }

    @Bean
    fun dynamoDB(client: AmazonDynamoDB): DynamoDB? {
        return DynamoDB(client)
    }
}