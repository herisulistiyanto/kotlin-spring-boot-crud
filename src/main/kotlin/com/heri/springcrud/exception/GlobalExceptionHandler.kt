package com.heri.springcrud.exception

import com.heri.springcrud.extension.generateResponseEntity
import com.heri.springcrud.vo.ErrorVO
import com.heri.springcrud.vo.ResultVO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException, response: HttpServletResponse): ResponseEntity<ResultVO> {
        val result = ResultVO().apply {
            status = StatusCode.ERROR.name
            data = null
            error = ErrorVO(
                    ex.errorMessage,
                    ex.code.value()
            )
        }
        return result.generateResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [Exception::class, RuntimeException::class])
    fun defaultErrorHandler(ex: Exception): ResponseEntity<ResultVO> {
        val result = ResultVO().apply {
            status = StatusCode.ERROR.name
            data = null
            error = ErrorVO(
                    ex.localizedMessage,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            )
        }
        return result.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

}