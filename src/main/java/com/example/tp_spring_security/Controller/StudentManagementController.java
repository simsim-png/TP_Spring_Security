package com.example.tp_spring_security.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS= Arrays.asList(
            new Student(1, "Adam"),
            new Student(2,"Anna"),
            new Student(3,"Mouad")
    );
    @GetMapping
    public List<Student> getAllStudent(){
        return STUDENTS;
    }
    @PostMapping
    public void registerNewStudent (@RequestBody Student student)
    { System.out.println(student);
    }
    @DeleteMapping(path="{studentId}")
    public void deleteStudent (@PathVariable("studentId")
                               Integer studentId) {
        System.out.println(studentId);
    }
    @PutMapping (path="{studentId}")
    public void updateStudent (@PathVariable("studentId")
                               Integer studentId,@RequestBody Student student) {
        System.out.println(String.format("%s %s",studentId, student));
    }
}
