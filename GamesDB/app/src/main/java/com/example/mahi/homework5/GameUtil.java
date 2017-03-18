package com.example.mahi.homework5;

import android.util.Log;

import com.example.mahi.homework5.Game;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class GameUtil {
    static public class GamePullParser{

        static Game parseGame(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");

            Game game = null;
            ArrayList<Integer> idList = new ArrayList<>();
            String urlBase = "";

            int event = parser.getEventType();
            int id_count = 0;
            int image_count = 0;
            int game_count = 0;


            while(event != XmlPullParser.END_DOCUMENT)
            {
                switch(event)
                {
                    case XmlPullParser.START_TAG:

                        if(parser.getName().equals("baseImgUrl"))
                        {
                            urlBase = parser.nextText().trim();
                        }
                        if(parser.getName().equals("Game"))
                        {
                            game_count++;
                            if(game_count==1)
                            {
                                game = new Game();
                            }

                        }
                        else if(parser.getName().equals("id"))
                        {
                            id_count++;
                            if(id_count>1)
                            {
                                idList.add(Integer.parseInt(parser.nextText().trim()));
                            }
                            else
                            {
                                game.setId(Integer.parseInt(parser.nextText().trim()));
                            }

                        }
                        else if(parser.getName().equals("GameTitle"))
                        {
                            game.setTitle(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("ReleaseDate"))
                        {
                            game.setReleaseDate(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("Platform"))
                        {
                            game.setPlatform(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("Overview"))
                        {
                            game.setOverview(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("genre"))
                        {

                            game.setGenre(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("Youtube"))
                        {
                            game.setYoutubeLink(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("Publisher"))
                        {
                            game.setPublisher(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("boxart"))
                        {
                            image_count++;
                            if(image_count==1)
                            {
                                game.setUrlToImage(urlBase+parser.nextText().trim());
                            }
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
            game.similar = idList;
            return game;
        }
    }
}
