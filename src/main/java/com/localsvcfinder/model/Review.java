package com.localsvcfinder.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;       // 1 to 5 stars

    private String comment;

    // Constructors
    public Review() {}

    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
