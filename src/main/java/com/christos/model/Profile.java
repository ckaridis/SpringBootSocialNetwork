package com.christos.model;

import javax.persistence.*;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // For every row in users table, we'll have an associated user as well.
    @OneToOne(targetEntity = SiteUser.class)
    @JoinColumn(name = "user_id", nullable = false)
    private SiteUser user;

    @Column(name = "about", length = 5000, nullable = true)
    private String about;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteUser getUser() {
        return user;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    // We create a new blank profile and we fill the info from the database. This profile is used
    // for security reasons. It contains non critical information, which can be sent to the JSP.
    public void safeCopyFrom(Profile other) {

        // We copy ONLY the "about" field of the profile and not the rest sensitive information.
        if (other.about != null) {
            this.about = other.about;
        }

    }
}
