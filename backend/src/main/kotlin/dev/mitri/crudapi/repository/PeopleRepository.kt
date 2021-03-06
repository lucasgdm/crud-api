package dev.mitri.crudapi.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException
import dev.mitri.crudapi.model.Person
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Stream


@Repository
class PeopleRepository (
    private val dynamoDBTableMapper: DynamoDBTableMapper<Person, UUID, Any>,
) {

    fun insert(person: Person): Person {
        person.id = UUID.randomUUID()
        person.createdAt = Date()
        person.updatedAt = null
        dynamoDBTableMapper.save(person)
        return person
    }

    fun update(person: Person): Boolean {
        person.createdAt = null
        person.updatedAt = Date()
        try {
            dynamoDBTableMapper.saveIfExists(person)
        } catch (e: ConditionalCheckFailedException) {
            return false
        }
        return true
    }

    fun findById(id: UUID): Person? {
        return dynamoDBTableMapper.load(id)
    }

    fun findAll(): Stream<Person> {
        val scanExp = DynamoDBScanExpression()
        val scanList = dynamoDBTableMapper.scan(scanExp)
        return scanList.stream()
    }

    fun deleteById(id: UUID): Boolean {
        val person = Person()
        person.id = id
        try {
            dynamoDBTableMapper.deleteIfExists(person)
        } catch (e: ConditionalCheckFailedException) {
            return false
        }
        return true
    }
}