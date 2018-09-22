package com.heri.springcrud.entity

import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity
@Table(name = "student")
class Student @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @ApiModelProperty(hidden = true, dataType = "Long")
        @Column(name = "student_id", unique = true)
        val id: Long = 0,
        @ApiModelProperty(value = "Name of Student", example = "Heri", required = true, dataType = "String")
        @Column(name = "student_name")
        var name: String = "Anonymous",
        @ApiModelProperty(value = "Email of Student", example = "heri@gmail.com", required = true, dataType = "String")
        @Column(name = "student_email")
        var email: String = "anon@gmail.com"
)
