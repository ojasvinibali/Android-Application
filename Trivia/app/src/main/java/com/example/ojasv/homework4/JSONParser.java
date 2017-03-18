package com.example.ojasv.homework4;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ojasv on 2/10/2017.
 */

public class JSONParser {
    static public class QuizJSONParser {
        static ArrayList<Quiz> parseQuestions(String in) throws JSONException {
            String id, text, image,answer;
            ArrayList<Quiz> questionlist = new ArrayList<>();
            ArrayList<String> choiceslist = new ArrayList<>();
            JSONObject root = new JSONObject(in);
            JSONArray questionArray = root.getJSONArray("questions");
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObject = questionArray.getJSONObject(i);
               JSONObject choices= questionObject.getJSONObject("choices");
                JSONArray choice = choices.getJSONArray("choice");
                List<String> list = new ArrayList<String>();
                for(int k = 0; k < choice.length(); k++){
                    list.add(choice.get(k).toString());
                }
                //JSONObject choices = new JSONObject(questionObject.getString("choices"));
               // answer = (String)choices.get("answer");
                //JSONArray choice =(JSONArray) choices.get("choice");
              //  String ch = choice.toString();
                //Log.i("answer",answer);

                Log.i("first",choice.toString());
                Quiz quiz = new Quiz();
                quiz.setId(questionObject.getString("id"));
                quiz.setText(questionObject.getString("text"));
                quiz.setAnswer(choices.getString("answer"));
                if(questionObject.has("image"))
                {
                    quiz.setImage(questionObject.getString("image"));
                }
                else
                {
                    quiz.setImage("");
                }

                quiz.setChoices((ArrayList<String>) list);

                questionlist.add(quiz);
            }



            /*Article article = new Article();
            article.setAuthor(newsObject.getString("author"));
            article.setTitle(newsObject.getString("title"));
            article.setDescription(newsObject.getString("description"));
            article.setUrlToImage(newsObject.getString("urlToImage"));
            article.setPublishedOn(newsObject.getString("publishedAt"));
            newslist.add(article);*/
 return questionlist;
            }
        }
    }

