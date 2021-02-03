package com.linn.web;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "sub")
    private String sub;
    @Column(name = "email")
    @Email
    private String email;
    @Column(name="picture")
    private String picture;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Image> imageList;
    public User(String name, String sub, @Email String email, String picture) {
        this.name = name;
        this.sub = sub;
        this.email = email;
        this.picture = picture;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
