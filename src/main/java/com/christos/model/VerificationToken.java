package com.christos.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    // Here we set where the original database is
    @OneToOne(targetEntity = SiteUser.class)
    // This is a foreign key
    @JoinColumn(name="user_id", nullable = false)
    private SiteUser user;

    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP) // This is some type related to date and/or time
    private Date expiry;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)     // It's going to use String values from our enum (automatically)
    private TokenType type;

    @PrePersist         // This is executed before filling the fields in the database
    private void setDate(){
        Calendar c = Calendar.getInstance();        // We create a calendar with the current date/time
        c.add(Calendar.HOUR, 24);           // We add 24 hours
        expiry = c.getTime();                       // We  set the expire on 24 hours from now
    }

    public VerificationToken(){
        // We need a no parameter constructor for Hibernate to use
    }

    public VerificationToken(String token, SiteUser user, TokenType type) {
        this.token = token;
        this.user = user;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SiteUser getUser() {
        return user;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}
