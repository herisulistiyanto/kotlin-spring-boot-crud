package com.heri.springcrud.repository

import com.heri.springcrud.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepo : JpaRepository<Student, Long>