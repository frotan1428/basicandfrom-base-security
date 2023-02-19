package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/students") //http://localhost:8080/students
public class StudentController {


    Logger logger = LoggerFactory.getLogger(StudentController.class); //


    @Autowired
    private StudentService studentService;

    /*
        {
    "firstName": "Vusani",
    "lastName" : "Siph0kazi",
    "grade": 80,
    "phoneNumber": "8888",
    "email": "bbb@bbb.com"
}
     */

    @PostMapping  //http://localhost:8080/students + Post
    public ResponseEntity<Map<String, String>> saveStudent(@Valid @RequestBody Student student){
        //@Valid --> validates the fields of Student object
        //@RequestBody --> maps the  json data to Student obj
        //ResponseEntity --> for return type and http status code

        studentService.saveStudent(student);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is created successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }



    @PreAuthorize("hasRole('ADMIN')")  //hasRole can omit "ROLE_" part //click Run and go tp postman
    @GetMapping() //http://localhost:8080/students + Get
    public ResponseEntity<List<Student>> getAllStudents(){
       List<Student> students = studentService.getAllStudents();
       return ResponseEntity.ok(students); // new ResponseEntity<>(students, HttpStatus.OK)
    }



    @GetMapping("/query") //http://localhost:8080/students/query?id=3 + Get
    public ResponseEntity<Student> getStudentById(@RequestParam("id") Long id){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}") //http://localhost:8080/students/2 + Get
    public ResponseEntity<Student> getStudentByIdUsingPathVariable(@PathVariable ("id") Long id){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")   //http://localhost:8080/students/2 + DELETE
    public ResponseEntity<Map<String, String>> deleteStudentById(@PathVariable ("id") Long id){
        studentService.deleteStudent(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is deleted successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK); //return ResponseEntity.ok(map);
    }

    //update existing students
    @PutMapping("{id}")   //http://localhost:8080/students/2 + PUT + JSON
    public ResponseEntity<Map<String, String>> updateStudent(@PathVariable("id") Long id,
                                                             @Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id, studentDTO);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is updated successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    //get students using pageable

    @GetMapping("/page") //http://localhost:8080/students/page =GET

    public ResponseEntity<Page<Student>> getStudentWithPage(@RequestParam("page") int page, //required - page numbers starting from 0
                                                            @RequestParam("size") int size, //required - number of students per page
                                                            @RequestParam("sort") String prop, //optional- on which field sortnig should be done
                                                            @RequestParam("direction")Sort.Direction direction){ //optional- ASCENDING or DESCENDING

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<Student> studentPage =  studentService.getAllStudentsWithPage(pageable);
        return ResponseEntity.ok(studentPage);

    }

    //method to bring student by their lastName  AAA

    @GetMapping("/querylastname")  //http://localhost:8080/students/querylastname  + GET
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam ("lastName") String lastName){
        List<Student> students = studentService.getStudentByLastName(lastName);
        return ResponseEntity.ok(students);
    }

    // get student by grade using JPQL -- Java Persistence Query Language

    @GetMapping("grade/{grade}")   //http://localhost:8080/students/grade/89  + GET

    public ResponseEntity<List<Student>> getStudentByGrade(@PathVariable("grade") Integer grade){
        List<Student> students = studentService.getStudentByGrade(grade);
        return ResponseEntity.ok(students);
    }

    //let s get DTO from Repository using JPQL

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/query/dto")  //http://localhost:8080/students/query/dto?id=1  + GET
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id){
        StudentDTO studentDTO = studentService.getStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }


    @GetMapping("/welcome")    //http://localhost:8080/students/welcome  + GET
    public String welcome(HttpServletRequest request){
        logger.warn("--------------Welcome{}", request.getServletPath());
        return "Welcome to Student Controller Class";
    }




}
