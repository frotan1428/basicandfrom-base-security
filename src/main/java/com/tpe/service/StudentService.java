package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(Student student) {
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Student whose email is "+student.getEmail()+" already exists");
        }
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    //getStudent by its id
    public Student getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Student whose id is "+id+" not found"));
        return student;
    }


    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    public void updateStudent(Long id, StudentDTO studentDTO) { //studentDTO.getEmail()  = aaa@gamil.com
        Student existingStudent = getStudentById(id);
        boolean emailExists = studentRepository.existsByEmail(studentDTO.getEmail()); //true


        //1. check email exist in DB
        //2. if true, if the email in DB belongs to the same student who is being updated


        /*

        //  DB emailList:  [aaa@gamil.com, bbb@gmail.com, ccc@gmail.com]

            1.  studdentDTO.getEmail() -- aaa@gamil.com
                True && !True ==>False
                update the existingStudent

            2. studdentDTO.gethemail() -- aaa@gamil.com
                True && ! False ==>True
                result: exception message

            3. studdentDTO.getEmail() -- xxx@gamil.com
                False &&

                result: update the existingStudent
         */

        if(emailExists && !studentDTO.getEmail().equals(existingStudent.getEmail())){
            throw new ConflictException("Student whose email is "+studentDTO.getEmail()+" already exists");
        }



        existingStudent.setFirstName(studentDTO.getName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setGrade(studentDTO.getGrade());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        existingStudent.setEmail(studentDTO.getEmail());

        studentRepository.save(existingStudent);

    }

    //service method for returning students with page
    public Page<Student> getAllStudentsWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


    //service method to bring students by lastname
    public List<Student> getStudentByLastName(String lastName) {

        return studentRepository.findStudentByLastName(lastName);
    }

    //service method to get students by grade using JPQL
    public List<Student> getStudentByGrade(Integer grade) {
        return studentRepository.findStudentByGrade(grade);
    }

    public StudentDTO getStudentDTOById(Long id) {
       return studentRepository.finStudentDTOById(id).orElseThrow(()->
                new ResourceNotFoundException("Student whose id is "+id+" not found"));
    }
}
