package ca.bcit.comp3717.applesauce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.List;


public class main extends Activity implements SearchView.OnQueryTextListener
{

    ListView lv;
    SearchView sv;
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (SearchView)findViewById(R.id.searchView);
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("Filter");

        lv = (ListView)findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);

        final List<String> l = new ArrayList<String>();
        final List<Integer> images = new ArrayList<Integer>();
        for(int i = 0; i < 25; i++)
        {
            l.add("App " + i);
            if(i%2==0)
                images.add(R.drawable.ic_action_settings);
            else
                images.add(R.drawable.ic_launcher);
        }

        ArrayAdapter<String> ladapt = new AppArrayAdapter(this, l, images);
        lv.setAdapter(ladapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                String app=l.get(position);
                Toast.makeText(getApplicationContext(), "Selected : "+ app,   Toast.LENGTH_SHORT).show();
                Intent i = new Intent(main.this, recommendations.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_all:
                openAll();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void openSettings()
    {
        Toast.makeText(this, "Settings pressed", Toast.LENGTH_SHORT).show();
    }

    void openAll()
    {
        Intent i = new Intent(main.this, recommendations.class);
        startActivity(i);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
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
}
