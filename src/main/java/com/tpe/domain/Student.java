package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//@RequiredArgsConstructor

@Entity
@Table(name="t_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // no access for setting
    private Long id;

    @NotNull(message = "first name cannot be null")
    @NotBlank(message ="first name cannot be white space")
    @Size(min=2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long")

    /*final*/ private String firstName;

    @Column(nullable = false, length = 25)
    /*final*/ private String lastName;

    @Column(nullable = false, length = 50, unique = true)
    @Email //xxx@yy.com
    /*final*/ private String email;

    /*final*/ private String phoneNumber;

    /*final*/ private Integer grade;

    @Setter(AccessLevel.NONE) // no access for setting
    private LocalDateTime  createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();




}
