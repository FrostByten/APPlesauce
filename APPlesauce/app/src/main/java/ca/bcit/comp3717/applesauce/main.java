package ca.bcit.comp3717.applesauce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main extends Activity implements SearchView.OnQueryTextListener
{

    ListView lv;
    SearchView sv;
    DrawerLayout dl;
    ListView drawerlv;
    static ArrayList<AppInfo> apps;

    String[] draweritems = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

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
        sv.setQueryHint("Filter");

        lv = (ListView)findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);

        apps = ApplicationLister.getInstalledApps(getPackageManager());
        List<String> names = new ArrayList<String>();
        List<Drawable> icons = new ArrayList<Drawable>();

        Collections.sort(apps, new Comparator<AppInfo>()
        {
            public int compare(AppInfo a1, AppInfo a2)
            {
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        });

        for(int i = 0; i < apps.size(); i++)
        {
            Log.i("Info", apps.get(i).getName() + " v" + apps.get(i).getVersionCode());
            names.add(apps.get(i).getName());
            icons.add(apps.get(i).getIcon());
        }

        ArrayAdapter<String> ladapt = new AppArrayAdapter(this, names, icons);
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
                openAll();
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
            lv.setFilterText(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    class DrawerItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
        }
    }
}
