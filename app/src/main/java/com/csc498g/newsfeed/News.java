package com.csc498g.newsfeed;

public class News {

    private String headline;
    private String description;
    private String author;
    private String location;
    private String published_at;

    public News() {
        this.headline = "";
        this.description = "";
        this.author = "";
        this.location = "";
        this.published_at = "";
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    @Override
    public String toString() {
        return "News{" +
                "headline='" + headline + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", location='" + location + '\'' +
                ", published_at='" + published_at + '\'' +
                '}';
    }
}
