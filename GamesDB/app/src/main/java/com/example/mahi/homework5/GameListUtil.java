package com.example.mahi.homework5;

import com.example.mahi.homework5.Game;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class GameListUtil {
    static public class GamePullParser{

        public static ArrayList<Game> parseGame(InputStream in) throws XmlPullParserException, IOException {

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");

            Game game = null;
            ArrayList<Game> gameList = new ArrayList<Game>();

            int event = parser.getEventType();

            while(event != XmlPullParser.END_DOCUMENT)
            {
                switch(event)
                {
                    case XmlPullParser.START_TAG:

                        if(parser.getName().equals("Game"))
                        {
                            game = new Game();
                        }
                        else if(parser.getName().equals("id"))
                        {
                            game.setId(Integer.parseInt(parser.nextText().trim()));
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
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("Game"))
                        {
                            gameList.add(game);
                            game=null;
                        }
                    default:
                        break;
                }
                event = parser.next();
            }
            return gameList;
        }
    }
}
