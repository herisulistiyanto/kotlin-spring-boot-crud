package com.heri.springcrud.entity

import javax.persistence.*

@Entity
@Table(name = "student")
class Student @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "student_id", unique = true)
        val id: Long = 0,
        @Column(name = "student_name")
        var name: String = "Anonymous",
        @Column(name = "student_email")
        var email: String = "anon@gmail.com"
)
