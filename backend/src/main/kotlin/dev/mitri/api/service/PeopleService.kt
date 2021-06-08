package dev.mitri.api.service

import dev.mitri.api.model.db.Person
import dev.mitri.api.repository.PeopleRepository
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Stream

@Service
class PeopleService(
    private val peopleRepository: PeopleRepository
) {
    fun insert(person: Person): Person {
        return peopleRepository.insert(person)
    }

    fun findAll(): Stream<Person> {
        return peopleRepository.findAll()
    }

    fun findById(id: UUID): Person? {
        return peopleRepository.findById(id) ?: throw ResourceNotFoundException()
    }

    fun deleteById(id: UUID) {
        val idFound = peopleRepository.deleteById(id)
        if (!idFound)
            throw ResourceNotFoundException()
    }

    fun update(person: Person) {
        val idFound = peopleRepository.update(person)
        if (!idFound)
            throw ResourceNotFoundException()
    }
}