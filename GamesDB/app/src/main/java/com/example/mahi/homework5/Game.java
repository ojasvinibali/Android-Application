package com.example.mahi.homework5;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ojasvini on 2/17/2017.
 */

public class Game implements Serializable{
    int id;
    String title,platform,releaseDate,overview,publisher,youtubeLink,genre,urlToImage = null;
    ArrayList<Integer> similar = new ArrayList<>();

    public ArrayList<Integer> getSimilar() {
        return similar;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", platform='" + platform + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", publisher='" + publisher + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", genre='" + genre + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }
}
