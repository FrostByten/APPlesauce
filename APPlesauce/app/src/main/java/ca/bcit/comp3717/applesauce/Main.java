package ca.bcit.comp3717.applesauce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class Main extends Activity implements SearchView.OnQueryTextListener, AppDetailResponse
{

    ListView lv;
    SearchView sv;
    DrawerLayout dl;
    ListView drawerlv;
    List<String> names = new ArrayList<String>();
    List<Drawable> icons = new ArrayList<Drawable>();

    private ArrayAdapter<String> ladapt;
    private final IgnoredAppDataSource datasource = new IgnoredAppDataSource(this);

    static ArrayList<AppInfo> apps;

    // Added for I'm Feeling Lucky!
    //AsyncAppDetail getRecApps = new AsyncAppDetail();
    private final static String GOOGLEPLAY = "http://play.google.com/store/apps/details?id=";

    String[] draweritems = {"Filter Apps", "I'm Feeling Lucky!", "Achievements", "Item 4", "Item 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerlv = (ListView) findViewById(R.id.left_drawer);
        drawerlv.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, draweritems));
        drawerlv.setOnItemClickListener(new DrawerItemClickListener());

        sv = (SearchView)findViewById(R.id.searchView);
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("Search");

        lv = (ListView)findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);

        apps = ApplicationLister.getInstalledApps(getPackageManager());


        Collections.sort(apps, new Comparator<AppInfo>()
        {
            public int compare(AppInfo a1, AppInfo a2)
            {
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        });

        // Do not display ignored apps
        try
        {
            datasource.open();
        }
        catch(SQLException e)
        {
            Log.d("IgnoreApps: onCreate ", e.getLocalizedMessage());
        }

        // Populate names and icons
        for(int i = 0; i < apps.size(); i++)
        {
            Log.i("Info", apps.get(i).getName() + " v" + apps.get(i).getVersionCode());
            if(!datasource.checkIfIgnored(apps.get(i).getName()))
            {
                names.add(apps.get(i).getName());
                icons.add(apps.get(i).getIcon());
            }
        }
        datasource.close();

        ladapt = new AppArrayAdapter(this, names, icons);
        lv.setAdapter(ladapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                String app = apps.get(position).getName();

                Intent i = new Intent(Main.this, Recommendations.class);
                i.putExtra("PACKAGE_NAME", apps.get(position).getPName());

                Toast.makeText(getApplicationContext(), "Selected : " + app + " " + apps.get(position).getPName(), Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        names = new ArrayList<String>();
        icons = new ArrayList<Drawable>();

        try
        {
            datasource.open();
        }
        catch(SQLException e)
        {
            Log.d("Main: onResume open failed", e.getLocalizedMessage());
        }

        for(int i = 0; i < apps.size(); i++)
        {
            if(!datasource.checkIfIgnored(apps.get(i).getName()))
            {
                names.add(apps.get(i).getName() + "                                         ");
                icons.add(apps.get(i).getIcon());
            }
        }
        datasource.close();

        ladapt = new AppArrayAdapter(this, names, icons);
        lv.setAdapter(ladapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_all:
                Toast.makeText(getApplicationContext(), "Lucky Recommendation", Toast.LENGTH_SHORT).show();
                Random r = new Random();
                int randomApp = r.nextInt(apps.size());
                LuckyApp(randomApp);
                return true;
            case R.id.action_settings:
                if(dl.isDrawerOpen(drawerlv))
                    dl.closeDrawer(drawerlv);
                else
                    dl.openDrawer(drawerlv);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Cannot use this at the moment as Recommendations needs a package name
    void openAll()
    {
        Intent i = new Intent(Main.this, Recommendations.class);
        startActivity(i);
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        if (TextUtils.isEmpty(newText))
        {
            lv.clearTextFilter();
        } else {
            lv.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void LuckyApp(int randomAppRecommendation)
    {
        String app = apps.get(randomAppRecommendation).getName();
        AsyncAppDetail getRecApps = new AsyncAppDetail();
        getRecApps.delegate = this;

        getRecApps.execute(apps.get(randomAppRecommendation).getPName());
    }

    // Get results from recommendation asyncappdetail
    public void processFinish(final ArrayList<AppInfo> recApps)
    {
        Random r = new Random();
        int randomApp = r.nextInt(recApps.size());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(GOOGLEPLAY + recApps.get(randomApp).getPName()));
        startActivity(intent);
    }

    class DrawerItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch(position)
            {
                case 0: // Filter Apps
                {
                    Intent i = new Intent(Main.this, IgnoreApps.class);
                    startActivity(i);
                    break;
                }
                case 1: // I'm feeling lucky
                {
                    Random r = new Random();
                    int randomApp = r.nextInt(apps.size());
                    LuckyApp(randomApp);
                }
                case 2: //Achievements
                {
                    Intent i = new Intent(Main.this, Achievements.class);
                    Toast.makeText(getApplicationContext(), "Achievements selected", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            }
        }
    }
}
