package com.cyptical.librarymanagementsystem.service;

import com.cyptical.librarymanagementsystem.models.Student;
import com.cyptical.librarymanagementsystem.repository.StudentRepository;
import com.cyptical.librarymanagementsystem.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public void create(StudentCreateRequest studentCreateRequest){
        Student student = studentCreateRequest.to();
        studentRepository.save(student);
    }

    public Student findStudentByStudentId(int sId){
        return studentRepository
                .findById(sId).orElse(null);
    }
}
