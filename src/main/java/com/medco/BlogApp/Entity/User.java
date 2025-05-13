package com.medco.BlogApp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Size(max = 50)
    @Column(length = 50, nullable=false)
    private String firstName;

    @Size(max=50)
    @Column(length = 50, nullable = false)
    private String lastName;

    @Email
    @Size(max = 50)
    @Column(length = 100, nullable =false, unique = true)
    private String email;

    @Size(min = 10, max = 15)
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Size(min = 8, max = 255)
    @Column(nullable = false)
    private String password;

    @Transient   // This annotation indicates that this field is not to be persisted in the database
    private String confirmPassword;

    @Column(name = "profile_picture")
    private String imagePath;

    @Column(length = 1000)
    private String bio;

    @Column(name = "reset_code")
    private String passwordResetCode;

    @Column(name = "resetCodeExpiry_time")
    private LocalDateTime resetCodeExpiry;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BlogPost> blogPosts  = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reaction> reactions = new ArrayList<>();


    public void setImage(String imagePath) {
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
