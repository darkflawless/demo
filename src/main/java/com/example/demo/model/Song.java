package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "song")
public class Song {

    public Song() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String link;
    private String cover;
    private String author;
    private String category;
    private String description;
    private int view ;

    public Song(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getCover() {
        return cover;
    }
    public String getName() {
        return name;
    }

    public void setFileName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCover(String s) {
        this.cover = s;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getView() {
        return view;
    }
    public void setView(int view) {
        this.view = view;
    }

}