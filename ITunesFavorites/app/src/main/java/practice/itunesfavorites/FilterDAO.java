package practice.itunesfavorites;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ojasvini on 3/17/2017.
 */

public class FilterDAO {

    private SQLiteDatabase db;

    public FilterDAO(SQLiteDatabase db)
    {
        this.db=db;
    }

    public long save(Filter filter)
    {

        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME,filter.getName());
        values.put(FilterTable.COLUMN_IMGURL,filter.getUrl());
        values.put(FilterTable.COLUMN_PRICE,filter.getPrice());

        Log.d("Demo","inside NoteDAO"+values.toString());
        return db.insert(FilterTable.TABLENAME,null,values);
    }

    public boolean update (Filter filter)
    {
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME,filter.getName());
        values.put(FilterTable.COLUMN_IMGURL,filter.getUrl());
        values.put(FilterTable.COLUMN_PRICE,filter.getPrice());

        return db.update(FilterTable.TABLENAME,values,FilterTable.COLUMN_ID+"=?",new String[]{filter.get_id()+""})>0;
    }

    public boolean delete(Filter filter)
    {
        return db.delete(FilterTable.TABLENAME,FilterTable.COLUMN_ID+"=?",new String[]{filter.get_id()+""})>0;
    }

    public Filter get(long id)
    {
        Filter filter = null;
        Cursor c = db.query(true,FilterTable.TABLENAME,new String[]
                        {FilterTable.COLUMN_ID,FilterTable.COLUMN_NAME,FilterTable.COLUMN_IMGURL,FilterTable.COLUMN_PRICE}
                ,FilterTable.COLUMN_ID + "=?",new String[]{id+""},null,null,null,null);

        if(c!=null && c.moveToFirst())
        {
            filter = buildFilterFromCursor(c);
            if(!c.isClosed())
            {
                c.close();
            }
        }

        return filter;
    }

    public List<Filter> getAll()
    {
        List<Filter> notes = new ArrayList<Filter>();
        Cursor c = db.query(true,FilterTable.TABLENAME,new String[]{FilterTable.COLUMN_ID,FilterTable.COLUMN_NAME,FilterTable.COLUMN_IMGURL,FilterTable.COLUMN_PRICE},null,null,null,null,null,null);

        if(c!=null && c.moveToFirst())
        {
            do {
                Filter filter = new Filter();
                filter = buildFilterFromCursor(c);
                if(filter!=null)
                {
                    notes.add(filter);
                }
            }while(c.moveToNext());

            if(!c.isClosed())
            {
                c.close();
            }
        }
        return notes;
    }


    private Filter buildFilterFromCursor(Cursor c)
    {
        Filter filter = null;

        if(c!=null)
        {
            filter = new Filter();
            filter.set_id(c.getLong(0));
            filter.setName(c.getString(1));
            filter.setUrl(c.getString(2));
            filter.setPrice(c.getDouble(3));
        }
        return filter;
    }

}



