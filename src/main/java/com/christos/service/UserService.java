package com.christos.service;

import com.christos.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationDao verificationDao;

    public void register(SiteUser user) {

        // Here we set the default role for every new user
        // Each user role should start with ROLE_ otherwise it might not work.
        user.setRole("ROLE_USER");
/*
        // When we register an account, it first gets the password, it encodes it and then it saves it.
        ////////// WE DON'T need this anymore. Encryption happens in the Domain object from now on.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
*/
        userDao.save(user);
    }


    public void save(SiteUser user) {
        userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // We use the email as username. This is how we're going to find the user
        SiteUser user = userDao.findByEmail(email);

        // If the user can't be found
        if (user == null) {

            return null;
        }

        // GrantedAuthority represents a role. This AuthorityUtils is needed to add user roles as strings.
        List<GrantedAuthority> auth = AuthorityUtils
                .commaSeparatedStringToAuthorityList(user.getRole());

        String password = user.getPassword();

        Boolean enabled = user.isEnabled();

        return new User(email, password, enabled, true,
                true, true, auth);

    }


    public String createEmailVerificationToken(SiteUser user){
        // This line here creates a random string.
        VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user, TokenType.REGISTRATION);
            verificationDao.save(token);
        return token.getToken();
    }


    // Here we get the token from the database to compare it with the token the user has received on his email
    public VerificationToken getVerificationToken(String token) {
        return verificationDao.findByToken(token);
    }

    public void deleteToken(VerificationToken token) {
        verificationDao.delete(token);
    }
}
