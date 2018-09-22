package com.heri.springcrud.controller

import com.heri.springcrud.entity.Student
import com.heri.springcrud.exception.AppException
import com.heri.springcrud.handler.AbstractRequestHandler
import com.heri.springcrud.repository.StudentRepo
import com.heri.springcrud.validator.StudentValidator
import com.heri.springcrud.vo.ResultVO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1/student"])
@Api(value = "Student API", description = "CRUD student API")
class StudentController {


    @Autowired
    lateinit var studentRepo: StudentRepo

    @Autowired
    lateinit var studentValidator: StudentValidator

    @ApiOperation("Get all student records")
    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAll(): ResponseEntity<ResultVO> {

        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.findAll()
            }
        }
        return handler.getResult()
    }

    @ApiOperation("Get specific student record by student id")
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getStudent(
            @ApiParam("Specific Student id")
            @PathVariable id: Long
    ): ResponseEntity<ResultVO> {
        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.getOne(id)
            }
        }
        return handler.getResult()
    }

    @ApiOperation("Save Student record on db")
    @Transactional
    @PostMapping(value = ["/"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addStudent(
            @ApiParam("Student object to be save on db")
            @RequestBody @Valid student: Student
    ): ResponseEntity<ResultVO> {
        val msg = studentValidator.validateStudent(student)
        if (msg.isNotEmpty()) throw AppException(errorMessage = msg, code = HttpStatus.BAD_REQUEST)

        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                return studentRepo.save(student)
            }
        }
        return handler.getResult()
    }

    @ApiOperation("Delete Student record based by student id")
    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteStudent(
            @ApiParam("Specific Student id to be removed from db")
            @PathVariable id: Long
    ): ResponseEntity<ResultVO> {
        if (!studentRepo.existsById(id)) throw AppException(errorMessage = "Student with id : $id not found", code = HttpStatus.NOT_FOUND)

        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                studentRepo.deleteById(id)
                return null
            }
        }
        return handler.getResult()
    }

    @ApiOperation("Update Student record based by student id")
    @Transactional
    @PutMapping(value = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateStudent(
            @ApiParam("Specific student id to be update")
            @PathVariable id: Long,
            @ApiParam("New Student info")
            @RequestBody @Valid student: Student): ResponseEntity<ResultVO> {
        if (!studentRepo.existsById(id)) throw AppException(errorMessage = "Student with id : $id not found", code = HttpStatus.NOT_FOUND)

        val msg = studentValidator.validateStudent(student)
        if (msg.isNotEmpty()) throw AppException(errorMessage = msg, code = HttpStatus.BAD_REQUEST)

        val handler = object : AbstractRequestHandler() {
            override fun processRequest(): Any? {
                val toUpdate: Student = studentRepo.findById(id).orElseThrow {
                    AppException("Student with id : $id not found", code = HttpStatus.NOT_FOUND)
                }
                toUpdate.name = student.name
                toUpdate.email = student.email
                return studentRepo.saveAndFlush(toUpdate)
            }
        }
        return handler.getResult()

    }
}