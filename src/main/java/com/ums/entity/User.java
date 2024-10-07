package com.ums.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First Name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must be exactly 10 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Email ID is required")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
}
