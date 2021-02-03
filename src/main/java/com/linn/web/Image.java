package com.linn.web;

import javax.persistence.*;

@Entity
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    private Long id;
    @Column(name = "heading")
    private String heading;
    @Column(name = "image_encode_string")
    private String image;
    @Column(name = "google_id")
    private Long googleId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Image() {
    }

    public Image(String heading, String image, Long googleId, User user) {
        this.heading = heading;
        this.image = image;
        this.googleId = googleId;
        this.user = user;
    }

    public Long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getGoogleId() {
        return googleId;
    }

    public void setGoogleId(Long googleId) {
        this.googleId = googleId;
    }
}

