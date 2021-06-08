package dev.mitri.crudapi.controller

import dev.mitri.crudapi.model.Person
import dev.mitri.crudapi.service.PeopleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Stream

@RestController
@CrossOrigin(origins = ["http://localhost:4200"], maxAge = 3600)
class ApiController(
    private val peopleService: PeopleService
) {

    @GetMapping
    fun get(): Any {
        return mapOf("ok" to true)
    }

    @PostMapping("/people")
    fun insert(@RequestBody person: Person): ResponseEntity<Person> {
        peopleService.insert(person)
        return ResponseEntity<Person>(person, HttpStatus.CREATED)
    }

    @GetMapping("/people")
    fun getAll(): Stream<Person> {
        return peopleService.findAll()
    }

    @GetMapping("/people/{id}")
    fun getById(@PathVariable id: UUID): Person? {
        return peopleService.findById(id)
    }

    @PatchMapping("/people/{id}")
    fun update(@PathVariable id: UUID, @RequestBody person: Person) {
        person.id = id
        peopleService.update(person)
    }

    @DeleteMapping("/people/{id}")
    fun delete(@PathVariable id: UUID) {
        return peopleService.deleteById(id)
    }
}
