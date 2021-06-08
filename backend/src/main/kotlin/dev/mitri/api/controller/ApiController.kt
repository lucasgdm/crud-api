package dev.mitri.api.controller

import dev.mitri.api.model.db.Person
import dev.mitri.api.service.PeopleService
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
    fun insert(@RequestBody person: Person): Person {
        return peopleService.insert(person)
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
