package com.example.ojasv.homework4;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ojasv on 2/10/2017.
 */

public class Quiz implements Serializable{
    String id;
    String text;
    String image;
    ArrayList<String> choices;
    String answer;

    public Quiz() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public Quiz(String id, String text, String image, ArrayList<String> choices, String answer) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.choices = choices;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", choices='" + choices + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getChoices() {
        return (choices);
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
