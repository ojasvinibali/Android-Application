package com.example.ojasvini.notesdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by Ojasvini on 3/17/2017.
 */


public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FilterDAO filterDAO;

    public DatabaseDataManager(Context context)
    {
        this.mContext = context;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        filterDAO = new FilterDAO(db);
    }

    public void  close(){
        if(db!=null){
            db.close();
        }
    }

    public FilterDAO getFilterDAO()
    {
        return this.filterDAO;
    }

    public long saveFilter(Filter filter){
        Log.i("save filter","save");
        return this.filterDAO.save(filter);
    }

    public boolean updateFilter(Filter filter){
        return this.filterDAO.update(filter);
    }

    public boolean deleteFilter(Filter filter)
    {
        return this.filterDAO.delete(filter);
    }

    public Filter getFilter(long id){
        return this.filterDAO.get(id);
    }

    public List<Filter> getAllFilters(){
        return this.filterDAO.getAll();
    }
}

