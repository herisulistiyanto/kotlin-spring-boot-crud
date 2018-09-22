package com.heri.springcrud.vo

import com.heri.springcrud.exception.StatusCode

class ResultVO(var status: String = StatusCode.OK.name, var data: Any? = null, var error: ErrorVO? = null)