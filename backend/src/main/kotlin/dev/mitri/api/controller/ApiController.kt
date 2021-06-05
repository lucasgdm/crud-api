package dev.mitri.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping
    fun get(): String {
        return "ok"
    }
}
