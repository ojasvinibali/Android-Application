package practice.itunesfavorites;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppAsyncTask.IApp {
    ArrayList<Apps> appMain;
    ArrayList<Apps> favorite;
    ListView lv;
    AppAdapter adapter;
    RecyclerAdapter recadapter;
    ProgressDialog progressDialog;
    String url ="https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    Switch orderSwitch;
    TextView order;
    DatabaseDataManager dm;
    RecyclerView mRecyclerView;
    Filter f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("in main","starting");
        favorite = new ArrayList<>();
        orderSwitch = (Switch) findViewById(R.id.switch1);
        order = (TextView)findViewById(R.id.order);
        dm = new DatabaseDataManager(this);

        orderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    order.setText("Descending");
                    sortDecreasing(appMain);
                }
                else {
                    order.setText("Ascending");
                    sortIncreasing(appMain);
                }
            }
        });
        if(isConnectedOnline()){
            new AppAsyncTask(MainActivity.this).execute(url);
        }
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                f = new Filter();
                f.set_id(0);
                f.setName(appMain.get(position).getName().toString());
                f.setPrice(Double.parseDouble(appMain.get(position).getPrice().toString().substring(1,appMain.get(position).getPrice().toString().length())));
                f.setUrl(appMain.get(position).getImgUrl().toString());
                dm.saveFilter(f);
                appMain.remove(position);
                displayList(appMain);
                Toast.makeText(getApplicationContext(), appMain.get(position).getName().toString()+" added to filtered apps", Toast.LENGTH_LONG).show();
                refreshRecycler(dm.getAllFilters());
                return false;
            }
        });
    }

    private void refreshRecycler(List<Filter> filterList) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(layoutManager);
        recadapter = new RecyclerAdapter(MainActivity.this, filterList);
        mRecyclerView.setAdapter(recadapter);
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
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading... Please Wait");
        progressDialog.show();
    }

    @Override
    public void setUpData(ArrayList<Apps> apps) {
        appMain = apps;
        sortIncreasing(appMain);
    }

    private void displayList(final ArrayList<Apps> appMain) {
        adapter = new AppAdapter(this, R.layout.row, appMain);
        lv.setAdapter(adapter);
    }

    @Override
    public void stopProgress() {
        progressDialog.dismiss();
    }

    public void sortIncreasing(ArrayList<Apps> appMain) {
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

    public void sortDecreasing(ArrayList<Apps> appMain) {
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
    private boolean contains(ArrayList<Apps> list, String name) {
        for (Apps item : list) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
