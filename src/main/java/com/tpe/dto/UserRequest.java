package com.tpe.dto;

import com.tpe.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {


    @NotBlank(message = "Please enter first name")
    private String firstName;

    @NotBlank(message = "Please enter last name")
    private String lastName;

    @NotBlank(message = "Please enter user name")
    @Size(min=5, max=50, message="User name must be between 5 and 50 characters ")
    private String userName;


    @NotBlank(message = "Please enter password")
    private String password;

}
