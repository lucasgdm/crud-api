package dev.mitri.api.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import dev.mitri.api.model.db.Person
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Stream

@Repository
class PeopleRepository (
    private val dynamoDB: DynamoDB,
    private val dynamoDBMapper: DynamoDBMapper
) {

    fun insert(person: Person): Person {
        person.id = UUID.randomUUID()
        person.createdAt = Date()
        dynamoDBMapper.save(person)
        return person
    }

    fun update(updatedPerson: Person) {
        updatedPerson.updatedAt = Date()
        //TODO prevent updating id that does not exist
//        val saveExp = DynamoDBSaveExpression()
//            .withExpected(
//                mapOf("id" to ExpectedAttributeValue(true))
//            )
        dynamoDBMapper.save(updatedPerson)
    }

    fun findById(id: UUID): Person? {
        return dynamoDBMapper.load(Person::class.java, id)
    }

    fun findAll(): Stream<Person> {
        val scanExp = DynamoDBScanExpression()
        val scanList = dynamoDBMapper.scan(Person::class.java, scanExp)
        return scanList.stream()
    }

    fun deleteById(id: UUID) {
        val person = Person()
        person.id = id
        dynamoDBMapper.delete(person)
    }
}