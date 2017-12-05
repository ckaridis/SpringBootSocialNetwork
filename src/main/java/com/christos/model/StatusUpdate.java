package com.christos.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

// Models are data related stuff.
// This is a domain object
// We will use JPA annotations, but in backstage hibernate will be used
// as implementation engine.

@Entity // This is something we'll store in a database
@Table(name = "status_update") // Make sure we don't use any names which might conflict with SQL
public class StatusUpdate {

    @Id // This is our primary key
    @Column(name = "id") // This is optional.
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID by hibernate
    private Long id; // We need this as a class type, not primitive.

    @NotNull //This can't be saved in the database if it has no text
    @Size(min = 5, max = 255, message = "{addstatus.text.size}") // This is the minimum / maximum amount of text
    @Column(name = "text") // This is also optional
    private String text;

    @Column(name = "added") // This is also optional
    @Temporal(TemporalType.TIMESTAMP) // This is some type related to date and/or time
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss") // This converts String to Date when needed using pattern
    private Date added;

    // We generate constructors
    // We generate a no parameter constructor (it's not set by default when we set our own constructors).
    public StatusUpdate() {
    }

    // We generate a constructor where we only pass the text.
    public StatusUpdate(String text) {
        this.text = text;
    }

    // We generate a constructor where we pass text and date.
    public StatusUpdate(String text, Date added) {
        this.text = text;
        this.added = added;
    }

    @PrePersist // This means that this method will run before the objects are stored in the database
    protected void onCreate() {
        // We validate that the date is null before assigning date automatically.
        if (added == null) {
            added = new Date();
        }
    }

    // We generate setters and getters
    // Hibernate needs setters and getters to work.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }


    // We generate equals and hashcodes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusUpdate that = (StatusUpdate) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return added != null ? added.equals(that.added) : that.added == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (added != null ? added.hashCode() : 0);
        return result;
    }

    // We override the "toString" method to display the result on the console
    @Override
    public String toString() {
        return "StatusUpdate{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", added=" + added +
                '}';
    }
}
