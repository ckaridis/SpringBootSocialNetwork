package com.christos.configuration;

import com.christos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

// THIS is our Spring Security configuration.

@Configuration // We tell Spring that this is a bean used for configuration
@EnableWebSecurity // This tells Spring to enable and manage the security on our site
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // We autowire a user here
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override // We override the default HttpSecurity configuration
    protected void configure(HttpSecurity http) throws Exception {

        // Here we set which URls should have security. /** means ALL URLs.
        // Permit All means that we authorize everyone to access the URLs before.

        http                                    // === START ===
                .authorizeRequests()            // Authorization begins
                .antMatchers(                   // When it matches some conditions
                        "/",        // When it matches "/" (Home Page)
                        "/about",               // For the About page
                        "/register",            // Anyone can access the Registration form
                        "/registrationConfirmed",
                        "/invalidUser",
                        "/expiredToken",
                        "/verifyEmail",
                        "confirmRegistration"
                )                               // Match ends
                .permitAll()                    // All users can have access
                .antMatchers(                   // Add additional rules
                        "/js/*",    // Anyone can see the content of CSS file (not sub folders)
                        "/css/*",               // Anyone can see the CSS folder
                        "/img/*"                // Anyone can see the img folder
                )                               // Additional Rules end
                .permitAll()                    // Permit All matching the previous patterns
                .antMatchers(
                        "/addStatus",
                        "/editStatus",
                        "/deleteStatus",        // Only ADMINS Should be able to view these pages
                        "/viewStatus"
                )
                .hasAnyRole("ADMIN")
                .anyRequest()                   // Any other request
                .denyAll()                      // Will be denied. Redirected to Login Screen
                .and()
                .formLogin()                    // This enables the Login form
                .loginPage("/login")            // Login form address
                .defaultSuccessUrl("/")         // Default page for logged in users
                .permitAll()                    // Everyone can access the Login page
                .and()
                .logout()                       // We set here who can access the logout link
                .permitAll()                    // We allow everyone to logout
        ;                                       // ===== END =====

    }

/*
    @Autowired // Spring gives us the auth object
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // We hardcode a username and password for test purposes
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER");

    }
*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Now before the authentication we encode the password
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}


