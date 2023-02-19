package com.tpe.dto;

import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {


    private Long id;

    @NotNull(message = "first name cannot be null")
    @NotBlank(message ="first name cannot be white space")
    @Size(min=2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long")

    private String name;


    private String lastName;


    @Email //xxx@yy.com
    private String email;

    private String phoneNumber;

    private Integer grade;


    private LocalDateTime createdDate = LocalDateTime.now();


    public StudentDTO (Student student){
        this.id = student.getId();
        this.name = student.getFirstName();
        this.lastName = student.getLastName();
        this.grade = student.getGrade();
        this.createdDate  =student.getCreatedDate();
        this.email  = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
    }


}
