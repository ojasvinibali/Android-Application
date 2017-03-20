package com.example.ojasvini.notesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   DatabaseDataManager dm;
    Filter f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new DatabaseDataManager(this);
        f = new Filter();
        dm.saveFilter(f);
        f.set_id(0);
        f.setName("app 1");
        f.setPrice(3.99);
        f.setUrl("sdgsk.com");
        dm.saveFilter(f);
        f.set_id(0);
        f.setName("app 2");
        f.setPrice(6.99);
        f.setUrl("google.com");
        dm.saveFilter(f);
        f.set_id(0);
        f.setName("app 3");
        f.setPrice(7.99);
        f.setUrl("gfdhdoogle.com");
        dm.saveFilter(f);
        List<Filter> filters = dm.getAllFilters();

        Log.d("demo",filters.toString());


    }
}
