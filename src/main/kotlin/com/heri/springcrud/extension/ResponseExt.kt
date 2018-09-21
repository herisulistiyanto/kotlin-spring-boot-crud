package com.heri.springcrud.extension

import com.heri.springcrud.vo.ResultVO
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

private val CONTENT_TYPE = "Content-Type"

fun ResultVO.generateResponseEntity(pStatus: HttpStatus = HttpStatus.OK): ResponseEntity<ResultVO> {
    val headers = HttpHeaders()
    headers.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    return ResponseEntity<ResultVO>(this, headers, pStatus)
}