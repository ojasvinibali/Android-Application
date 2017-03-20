package com.example.ojasvini.radiotedhour;

/**
 * Created by Ojasvini on 3/18/2017.
 */

public class Podcast {
    String title , description , imgURL ,mediaURL, pubdate;
    int duration;

    public Podcast() {

    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", mediaURL='" + mediaURL + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", duration=" + duration +
                '}';
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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Podcast(String title, String description, String imgURL, String mediaURL, String pubdate, int duration) {

        this.title = title;
        this.description = description;
        this.imgURL = imgURL;
        this.mediaURL = mediaURL;
        this.pubdate = pubdate;
        this.duration = duration;
    }
}
