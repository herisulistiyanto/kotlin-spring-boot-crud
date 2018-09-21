package com.heri.springcrud.exception

import org.springframework.http.HttpStatus

class AppException @JvmOverloads constructor(val errorMessage: String = "",
                                             val status: StatusCode = StatusCode.ERROR,
                                             val code: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) : RuntimeException(errorMessage)