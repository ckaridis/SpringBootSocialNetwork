package com.christos.model;

import com.christos.validation.PasswordMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
// This is our custom validation service. Checks if the password is matched on both fields during registration
@PasswordMatch(message = "{register.repeatpassword.mismatch}")
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "email", unique = true)
    @Email(message = "{register.email.invalid}")              // This is a basic email validation
    @NotBlank(message = "{register.email.invalid}")           // validation for not blank
    private String email;

    // This will store the unencrypted password for validation
    @Transient              // This means that it will not store anything on the database
    @Size(min = 5, max = 15, message = "{register.password.size}")
    private String plainPassword;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Transient
    private String repeatPassword;

    @Column (name = "role", length = 20)
    private String role;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPlainPassword() {

        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {

        this.password = new BCryptPasswordEncoder().encode(plainPassword);

        this.plainPassword = plainPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
