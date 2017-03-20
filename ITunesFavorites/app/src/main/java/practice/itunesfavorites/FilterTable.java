package practice.itunesfavorites;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Ojasvini on 3/17/2017.
 */

public class FilterTable {
    static final String TABLENAME = "filter";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_IMGURL = "imgurl";
    static final String COLUMN_PRICE = "price";


    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE  "+TABLENAME+"(");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NAME + " text not null, ");
        sb.append(COLUMN_IMGURL + " text not null, ");
        sb.append(COLUMN_PRICE + " integer not null);");
        try {
            db.execSQL(sb.toString());
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        Log.i("table created","something");
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        FilterTable.onCreate(db);
    }
}
