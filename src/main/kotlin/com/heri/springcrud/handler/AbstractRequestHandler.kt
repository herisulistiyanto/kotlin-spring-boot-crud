package com.heri.springcrud.handler

import com.heri.springcrud.exception.AppException
import com.heri.springcrud.exception.StatusCode
import com.heri.springcrud.extension.generateResponseEntity
import com.heri.springcrud.vo.ErrorVO
import com.heri.springcrud.vo.ResultVO
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.lang.NumberFormatException

abstract class AbstractRequestHandler {

    fun getResult(): ResponseEntity<ResultVO> {
        val result = ResultVO()
        try {
            when (processRequest()) {
                null -> {
                    result.status = StatusCode.OK.name
                    result.data = null
                }
                else -> {
                    result.status = StatusCode.OK.name
                    result.data = processRequest()
                }
            }
        } catch (ex: AppException) {
            result.data = null
            result.error = ErrorVO(ex.errorMessage, ex.code.value())
        }
        return result.generateResponseEntity()
    }

    abstract fun processRequest(): Any?

}