package ca.bcit.comp3717.applesauce;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Achievements extends Activity {

    ListView lv;
    private final AchievementDataSource datasource = new AchievementDataSource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.chievelist);

        try
        {
            datasource.open();
        }
        catch(SQLException e)
        {
            Log.d("Achievements: onCreate ", e.getLocalizedMessage());
        }
        List<String> chieves = datasource.getAllIgnoredApps();
        List<String> descs = datasource.getAllIgnoredAppsDesc();
        datasource.close();

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> item;

        for(int i=0;i<chieves.size();i++)
        {
            item = new HashMap<String,String>();
            item.put( "line1", chieves.get(i));
            item.put( "line2", descs.get(i));
            list.add( item );
        }
        SimpleAdapter sa = new SimpleAdapter(this, list, android.R.layout.two_line_list_item,
                            new String[] { "line1","line2" }, new int[] {android.R.id.text1, android.R.id.text2});


        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.two_line_list_item, android.R.id.text1, chieves, android.R.id.text2, descs );

        if(!sa.equals(null))
            lv.setAdapter(sa);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ignore_apps, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        //Intent myIntent = new Intent(getApplicationContext(), Main.class);
        //startActivityForResult(myIntent, 0);
        return true;

    }

    public void resetChieves(View v)
    {
        try { datasource.open(); }
        catch(SQLException e) { Log.d("Achievements: onCreate ", e.getLocalizedMessage()); }
        datasource.resetAchievements();
        datasource.close();
        Main.chievecheck = 0;
        Main.luckycheck = 0;
        Main.gocount = 0;
        Main.ignorecount = 0;
        Main.chievecount = 0;
        AchievementDataSource.makeChieve(Main.c, Main.c.getString(R.string.cleanname), Main.c.getString(R.string.cleandesc));
        finish();
    }
}
