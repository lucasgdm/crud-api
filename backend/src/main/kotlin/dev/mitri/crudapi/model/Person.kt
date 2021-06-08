package dev.mitri.crudapi.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@DynamoDBTable(tableName = "person")
@JsonIgnoreProperties(ignoreUnknown = false)
class Person {
    @DynamoDBHashKey
    var id: UUID? = null
    @Size(min = 3, message = "name must have at least 3 characters")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "name must consist of alphabetic characters")
    var name: String? = null
    @Email(message = "invalid email")
    var email: String? = null
    @Pattern(regexp = "^[0-9]{11}$", message = "taxpayerId must have 11 digits")
    var taxpayerId: String? = null
    @DynamoDBTypeConvertedJson
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "birthday must be in the past")
    var birthday: Date? = null
    @DynamoDBTypeConvertedJson
    var createdAt: Date? = null
    @DynamoDBTypeConvertedJson
    var updatedAt: Date? = null
}