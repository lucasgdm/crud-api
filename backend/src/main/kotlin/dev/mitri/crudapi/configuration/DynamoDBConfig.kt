package dev.mitri.crudapi.configuration

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper
import dev.mitri.crudapi.model.Person
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class DynamoDBConfig {
    @Value("\${aws.accesskey}")
    private val awsAccessKey: String? = null

    @Value("\${aws.secretkey}")
    private val awsSecretKey: String? = null

    @Value("\${aws.region}")
    private val awsRegion: String? = null

    @Value("\${dynamodb.endpoint}")
    private val dynamoEndpoint: String? = null

    fun amazonAWSCredentialsProvider(): AWSCredentialsProvider? {
        return AWSStaticCredentialsProvider(amazonAWSCredentials())
    }

    fun amazonAWSCredentials(): AWSCredentials? {
        return BasicAWSCredentials(awsAccessKey, awsSecretKey)
    }

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB? {
        val builder = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(amazonAWSCredentialsProvider())
        if (StringUtils.isNotBlank(dynamoEndpoint))
            builder.withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(dynamoEndpoint, awsRegion))
        else
            builder.withRegion(Regions.fromName(awsRegion))
        return builder.build()
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
    fun dynamoDBTableMapper(dynamoDBMapper: DynamoDBMapper): DynamoDBTableMapper<Person, UUID, Any> {
        return dynamoDBMapper.newTableMapper(Person::class.java)
    }
}