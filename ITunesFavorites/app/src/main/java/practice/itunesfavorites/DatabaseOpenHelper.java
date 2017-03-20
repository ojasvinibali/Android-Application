package practice.itunesfavorites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mahi on 2/27/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "favs.db";
    static final int DB_VERSION = 3;

    public DatabaseOpenHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        FilterTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FilterTable.onUpgrade(db,oldVersion,newVersion);
    }
}
