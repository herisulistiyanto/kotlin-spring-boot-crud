package com.heri.springcrud.repository

import com.heri.springcrud.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface StudentRepo : JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>