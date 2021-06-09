package dev.mitri.crudapi.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(private val errors: List<String>): RuntimeException(errors.joinToString("; ")) {
    fun getErrors(): List<String> {
        return errors
    }
}