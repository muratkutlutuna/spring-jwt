package com.tpe.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "enter first name")
    private String firstName;
    @NotBlank(message = "enter last name")
    private String lastName;
    @NotBlank(message = "enter user name")
    @Size(min = 5, max = 10,message = "Please provide a username between {min} and {max}")
    private String userName;
    @NotBlank(message = "enter password")
    private String password;


}
