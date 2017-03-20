package com.example.ojasvini.radiotedhour;

import android.util.Log;

import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ojasvini on 3/18/2017.
 */

public class PodcastXMLPullParser {
    static public class ListPullParser extends DefaultHandler {
        public static ArrayList<Podcast> parseList(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Podcast list = null;
            ArrayList<Podcast> TList = new ArrayList<>();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {

                    case XmlPullParser.START_TAG: {

                        if (parser.getName().equals("item")) {
                            list = new Podcast();
                        } else if (parser.getName().equalsIgnoreCase("title") && list != null) {
                            list.setTitle(parser.nextText());
                        }else if (parser.getName().equalsIgnoreCase("description") && list != null) {
                            list.setDescription(parser.nextText());
                        } else if (parser.getName().equalsIgnoreCase("pubDate") && list != null) {
                            list.setPubdate(parser.nextText());
                        } else if (parser.getName().equalsIgnoreCase("itunes:image") && list != null) {
                            list.setImgURL(parser.getAttributeValue(null, "href"));
                        } else if (parser.getName().equalsIgnoreCase("enclosure") && list != null) {
                            list.setMediaURL(parser.getAttributeValue(null, "url"));
                        }else if (parser.getName().equalsIgnoreCase("itunes:duration") && list != null) {
                            list.setDuration(Integer.parseInt(parser.nextText()));
                        }

                        break;
                    }
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            TList.add(list);
                            list = null;
                        }
                        break;

                }

                event = parser.next();

            }


            return TList;
        }
    }
}
