package com.heri.springcrud.controller

import com.heri.springcrud.entity.Student
import com.heri.springcrud.exception.AppException
import com.heri.springcrud.handler.AbstractRequestHandler
import com.heri.springcrud.repository.StudentRepo
import com.heri.springcrud.validator.StudentValidator
import com.heri.springcrud.vo.ResultVO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1/student"])
class StudentController {

    val logger = LoggerFactory.getLogger(StudentController::class.java)

    @Autowired
    lateinit var studentRepo: StudentRepo

    @Autowired
    lateinit var studentValidator: StudentValidator

    @GetMapping(value = ["/"])
    fun getAll(): ResponseEntity<ResultVO> {
        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.findAll()
            }
        }
        return handler.getResult()
    }

    @GetMapping(value = ["/{id}"])
    fun getStudent(@PathVariable id: Long): ResponseEntity<ResultVO> {
        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.getOne(id)
            }
        }
        return handler.getResult()
    }

    @Transactional
    @PostMapping(value = ["/"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addStudent(@RequestBody @Valid student: Student): ResponseEntity<ResultVO> {
        val msg = studentValidator.validateStudent(student)
        if (msg.isNotEmpty()) throw AppException(errorMessage = msg, code = HttpStatus.BAD_REQUEST)

        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.save(student)
            }
        }
        return handler.getResult()
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteStudent(@PathVariable id: Long): ResponseEntity<ResultVO> {
        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.deleteById(id)
            }
        }
        return handler.getResult()
    }

    @Transactional
    @PutMapping(value = ["/{id}"])
    fun updateStudent(@PathVariable id: Long, @RequestBody @Valid student: Student): ResponseEntity<ResultVO> {
        val msg = studentValidator.validateStudent(student)
        if (msg.isNotEmpty()) throw AppException(errorMessage = msg, code = HttpStatus.BAD_REQUEST)

        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                val toUpdate: Student = studentRepo.findById(id).orElseThrow {
                    AppException("Student Not Found", code = HttpStatus.NOT_FOUND)
                }
                logger.debug("$toUpdate")
                toUpdate.name = student.name
                toUpdate.email = student.email
                return studentRepo.saveAndFlush(toUpdate)
            }
        }
        return handler.getResult()

    }
}