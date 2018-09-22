package com.heri.springcrud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class SpringcrudApplication

fun main(args: Array<String>) {
    runApplication<SpringcrudApplication>(*args)
}
