package com.cyptical.librarymanagementsystem.repository;

import com.cyptical.librarymanagementsystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
