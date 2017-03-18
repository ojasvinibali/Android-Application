package com.example.mahi.homework07;

import android.util.Log;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PodcastUtil {
    static public class PodcastPullParser
    {
        static ArrayList<Podcast> parsePodcasts(InputStream inputStream) throws XmlPullParserException, IOException
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(inputStream, "UTF-8");
            ArrayList<Podcast> podcasts = new ArrayList<>();
            Podcast podcast = null;
            String nameSpace=parser.getNamespace("itunes");

            boolean ItemFlag=false;
            boolean Imageflag=false;

            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT)
            {
                switch(event)
                {
                    case XmlPullParser.START_TAG:

                        if(parser.getName().equalsIgnoreCase("channel"))
                        {
                            Imageflag=true;

                            if (parser.getName().equalsIgnoreCase("image") && Imageflag){

                                if (parser.getName().equalsIgnoreCase("url"))
                                {
                                    String s=parser.nextText();
                                    MainActivity.image=parser.nextText();
                                }
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("item")){
                            ItemFlag = true;
                            podcast = new Podcast();
                        }

                        else if (parser.getName().equalsIgnoreCase("title")){
                            if (ItemFlag){
                                String s=parser.nextText();
                                podcast.setTitle(s);
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("description")){
                            if (ItemFlag){
                                String s=parser.nextText();
                                podcast.setDescription(s);
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("pubDate")){
                            if (ItemFlag){
                                String s=parser.nextText();
                                podcast.setPubDate(s);
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("image")){
                            if(ItemFlag){
                                String s = parser.getAttributeValue(nameSpace,"href");
                                podcast.setImgURL(s);
                            }
                        }

                        else if (parser.getName().equalsIgnoreCase("duration")){
                            if(ItemFlag){
                                String s=parser.nextText();
                                podcast.setDuration(s);
                            }
                        }

                        else if(parser.getName().equalsIgnoreCase("enclosure")){
                            if(ItemFlag){
                                String s = parser.getAttributeValue(null,"url");
                                podcast.setMp3url(s);
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equalsIgnoreCase("item"))
                        {
                            ItemFlag = false;
                            podcasts.add(podcast);
                            podcast =null;
                        }
                        break;
                    default:
                        break;

                }

                event = parser.next();
            }
            return podcasts;
        }
    }
}