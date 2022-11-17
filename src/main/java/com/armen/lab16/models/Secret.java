package com.armen.lab16.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Secret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String body;
    private Date createdAt;
    @ManyToOne
    SiteUser theUser;
    protected Secret() {
    }

    public Secret(String name, String body, SiteUser theUser, Date createdAt) {
        this.name = name;
        this.body = body;
        this.theUser = theUser;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    } //getters used under the hood in spring controllers

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public SiteUser getTheUser() {
        return theUser;
    }
}
