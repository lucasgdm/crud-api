package dev.mitri.api.model.db

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@DynamoDBTable(tableName = "person")
@JsonIgnoreProperties(ignoreUnknown = false)
class Person {
    @DynamoDBHashKey
    var id: UUID? = null
    var name: String? = null
    var email: String? = null
    var taxpayerId: String? = null
    @DynamoDBTypeConvertedJson
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var birthday: Date? = null
    @DynamoDBTypeConvertedJson
    var createdAt: Date? = null
    @DynamoDBTypeConvertedJson
    var updatedAt: Date? = null
}