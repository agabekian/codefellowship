package com.armen.lab16.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Secret {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String body;


    private Date createdAt;
    @ManyToOne
    SiteUser theUser;

    protected Secret(){}
    public Secret(String name, String body, SiteUser theUser ) {
        this.name = name;
        this.body = body;
        this.theUser = theUser;
    }


    public String getName() {
        return name;
    }

    public String getText() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String description) {
        this.body = body;
    }
}
