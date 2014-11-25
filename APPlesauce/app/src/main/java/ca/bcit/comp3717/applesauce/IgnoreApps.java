package ca.bcit.comp3717.applesauce;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class IgnoreApps extends Activity implements SearchView.OnQueryTextListener{

    private ListView lv;
    private SearchView sv;
    private List<String> names = new ArrayList<String>();
    private List<Drawable> icons = new ArrayList<Drawable>();
    private ArrayAdapter<String> ladapt;

    private final IgnoredAppDataSource datasource = new IgnoredAppDataSource(this);

    static ArrayList<AppInfo> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignore_apps);

        sv = (SearchView)findViewById(R.id.searchViewIA);
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint(getString(R.string.query));

        lv = (ListView)findViewById(R.id.listViewIA);
        lv.setTextFilterEnabled(true);

        apps = ApplicationLister.getInstalledApps(getPackageManager());

        Collections.sort(apps, new Comparator<AppInfo>() {
            public int compare(AppInfo a1, AppInfo a2) {
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        });

        // Populate names and icons
        for(int i = 0; i < apps.size(); i++)
        {
            Log.i("Info", apps.get(i).getName() + " v" + apps.get(i).getVersionCode());
            names.add(apps.get(i).getName());
            icons.add(apps.get(i).getIcon());
        }

        ladapt = new IgnoreAppArrayAdapter(this, names, icons);
        lv.setAdapter(ladapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ignore_apps, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
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
}
