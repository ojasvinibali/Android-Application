package com.example.mahi.homework2;

import java.io.Serializable;

/**
 * Created by ojasvini on 1/27/2017.
 */

public class Movie implements Serializable {
    String name;
    String description;
    String genre;
    Integer rating;
    Integer year;
    String imdb;

    public Movie() {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.imdb = imdb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", imdb='" + imdb + '\'' +
                '}';
    }

}
