package com.heri.springcrud.validator

import com.heri.springcrud.entity.Student
import com.heri.springcrud.extension.isEmail
import com.heri.springcrud.extension.isNameFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class StudentValidator {

    @Autowired
    lateinit var messageSource: MessageSource

    private val errorName: String by lazy {
        messageSource.getMessage("error.name.format", null, LocaleContextHolder.getLocale())
    }

    private val errorEmail: String by lazy {
        messageSource.getMessage("error.email.format", null, LocaleContextHolder.getLocale())
    }

    private val errorFieldRequired: String by lazy {
        messageSource.getMessage("error.field.required", null, LocaleContextHolder.getLocale())
    }

    fun validateStudent(student: Student): String {
        return when {
            student.name.isEmpty() -> String.format(errorFieldRequired, "Nama")
            student.email.isEmpty() -> String.format(errorFieldRequired, "Email")
            !student.name.isNameFormat() -> String.format(errorName, "Nama")
            !student.email.isEmail() -> String.format(errorEmail, "Email")
            else -> ""
        }
    }

}