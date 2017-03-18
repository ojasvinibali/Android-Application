package com.example.libraryguest2.homework6;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.libraryguest2.homework6.MainActivity.Name;
import static com.example.libraryguest2.homework6.MainActivity.Price;

public class FavoriteActivity extends AppCompatActivity {
    ListView listView;
    AppAdapter adapter;
    Toolbar toolbar;
    ArrayList<Apps> app;
    AlertDialog.Builder builder;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        app = (ArrayList<Apps>) getIntent().getSerializableExtra("Favorite activities");
        Log.i("ArrayList",app.toString());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.container);
        adapter = new AppAdapter(this, R.layout.row_item_layout, app);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("demo",app.get(position).toString());
                builder = new AlertDialog.Builder(FavoriteActivity.this);
                Log.i("In", String.valueOf(position));
                builder.setTitle("Remove From  Favourites").setMessage("Are you sure you want to remove this App from Favorites?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                             app.remove(position);
                                Log.i("arraylist in favorite",app.toString());
                                Log.i("size", String.valueOf(app.size()));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
    }
}
