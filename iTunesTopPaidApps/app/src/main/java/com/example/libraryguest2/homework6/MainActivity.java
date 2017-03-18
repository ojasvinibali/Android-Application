package com.example.libraryguest2.homework6;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AppAsyncTask.IApp {
    ProgressBar progreeBar;
    ArrayList<Apps> appMain;
    ArrayList<Apps> favorite;
    ListView listView;
    AppAdapter adapter;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder1;
    Toolbar toolbar;
    RadioButton img1;
    ImageView img2;
    boolean isFavorite =false;
    Apps favApp;
    String url ="https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Price = "priceKey";
    public static final String Image = "imgKey";
    SharedPreferences sharedpreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                new AppAsyncTask(MainActivity.this).execute(url);
                return true;
           case R.id.favorites:
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
               intent.putExtra("Favorite activities",favorite);
               startActivity(intent);
                return true;
            case R.id.sortIncreasingly:
                sortIncreasing();
               return true;
            case R.id.sortDecreasingly:
                sortDecreasing();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progreeBar = (ProgressBar)findViewById(R.id.progressBar);
        img1 =(RadioButton) findViewById(R.id.radioButton);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        favorite = new ArrayList<>();

        if(isConnectedOnline()){
            progreeBar.setVisibility(View.VISIBLE);
            new AppAsyncTask(MainActivity.this).execute(url);
        }
       listView = (ListView)findViewById(R.id.container);
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
        return true;
        else
            return false;
    }

    @Override
    public void showProgress() {
       progreeBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpData(ArrayList<Apps> apps) {
        appMain = apps;
        displayList(appMain);
     }

    public void sortIncreasing() {
        Collections.sort(appMain, new Comparator<Apps>() {
            @Override
            public int compare(Apps o1, Apps o2) {
                int len1 = o1.getPrice().length();
                int len2 = o2.getPrice().length();
                if ((Double.parseDouble(o1.getPrice().substring(1, len1))) > Double.parseDouble(o2.getPrice().substring(1, len2))) {
                    return 1;
                }
                return -1;
            }
        });
        displayList(appMain);
    }

    public void sortDecreasing() {
        Collections.sort(appMain, new Comparator<Apps>() {
            @Override
            public int compare(Apps o1, Apps o2) {
                int len1 = o1.getPrice().length();
                int len2 = o2.getPrice().length();
                if ((Double.parseDouble(o1.getPrice().substring(1, len1))) < Double.parseDouble(o2.getPrice().substring(1, len2))) {
                    return 1;
                }
                return -1;
            }
        });
        displayList(appMain);
    }

    private void displayList(final ArrayList<Apps> appMain) {
        adapter = new AppAdapter(this, R.layout.row_item_layout, appMain);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                builder = new AlertDialog.Builder(MainActivity.this);
                if(favorite.size()>0){
  //                  for(int i=0; i<favorite.size(); i++){
                    int index = 0;
                        if(contains(favorite, appMain.get(position).getName())){
                            for (Apps item : favorite) {
                                if (item.getName().equals(appMain.get(position).getName())) {
                                    index = favorite.indexOf(item);
                                }
                            }
                            Log.i("AT REMOVE", String.valueOf(index));
                            removeFromFavorites(index);
                            appMain.get(position).setIsFavorite(false);
                        }
                        else {
                            Log.i("HERE AT", "Add To Favorites 1");
                            addToFavorites(position);
                        }
                  //  }
                }
                else {
                    Log.i("HERE AT", "Add To Favorites 2");
                    addToFavorites(position);
                }
            }

            private boolean contains(ArrayList<Apps> list, String name) {
                for (Apps item : list) {
                    if (item.getName().equals(name)) {
                        return true;
                    }
                }
                return false;
            }

            private void addToFavorites(final int pos) {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add To Favourites").setMessage("Are you sure you want to add this App to Favorites?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name ,appMain.get(pos).getName());
                                editor.putString(Price, appMain.get(pos).getPrice());
                                editor.putString(Image,appMain.get(pos).getImgUrl());
                                editor.commit();
                                favApp = new Apps();
                                favApp.setName(appMain.get(pos).getName());
                                favApp.setPrice(appMain.get(pos).getPrice());
                                favApp.setImgUrl(appMain.get(pos).getImgUrl());
                                isFavorite = true;
                                appMain.get(pos).setIsFavorite(isFavorite);
                                favApp.setIsFavorite(isFavorite);
                                favorite.add(favApp);
                                Log.i("Favorites after addition",favorite.toString());
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

            private void removeFromFavorites(final int position) {
                builder1 = new AlertDialog.Builder(MainActivity.this);
                Log.i("App to be removed", String.valueOf(position));
                builder1.setTitle("Remove From Favourites").setMessage("Are you sure you want to remove this App from Favorites?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Favorites before removal", String.valueOf(favorite.indexOf(position)));
                                favorite.remove(position);
                                Log.i("Favorites after removal",favorite.toString());
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false);
                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        });
    }





    @Override
    public void stopProgress() {
   progreeBar.setVisibility(View.INVISIBLE);
    }
}
