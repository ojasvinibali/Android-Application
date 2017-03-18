package com.example.mahi.homework07;

import java.io.Serializable;

public class Podcast implements Serializable {
    String title, description, pubDate, imgURL, duration, mp3url;
    static String mainImage=null;

    public Podcast(String title, String description, String pubDate, String imgURL, String duration, String mp3url, String mainImage) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.imgURL = imgURL;
        this.duration = duration;
        this.mp3url = mp3url;
        this.mainImage = mainImage;
    }

    public Podcast() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMp3url() {
        return mp3url;
    }

    public void setMp3url(String mp3url) {
        this.mp3url = mp3url;
    }


    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", duration='" + duration + '\'' +
                ", mp3url='" + mp3url + '\'' +
                ", mainImage='" + mainImage + '\'' +
                '}';
    }
}
