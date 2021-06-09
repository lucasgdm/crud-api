package dev.mitri.crudapi.controller

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import dev.mitri.crudapi.model.Person
import dev.mitri.crudapi.service.PeopleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Stream
import javax.validation.Valid


@RestController
@CrossOrigin(origins = ["http://localhost:4200", "http://crudapi.s3-website-us-east-1.amazonaws.com"], maxAge = 3600)
class ApiController(
    private val peopleService: PeopleService
) {

    @GetMapping
    fun get(): Any {
        return mapOf("ok" to true)
    }

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    fun insert(@Valid @RequestBody person: Person): Person {
        peopleService.insert(person)
        return person
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun badRequestException(exception: MethodArgumentNotValidException): Any {
        val errorMessages = exception.bindingResult.allErrors
            .stream()
            .map { error -> error.defaultMessage }
        return mapOf("errors" to errorMessages)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnrecognizedPropertyException::class)
    fun badRequestException(exception: UnrecognizedPropertyException): Any {
        return mapOf("errors" to (listOf("Unknown field ${exception.propertyName}")))
    }
}
