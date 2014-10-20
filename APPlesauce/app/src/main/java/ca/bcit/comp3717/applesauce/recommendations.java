package ca.bcit.comp3717.applesauce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class recommendations extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lv = (ListView)findViewById(R.id.listViewRec);
        lv.setTextFilterEnabled(true);

        final List<String> l = new ArrayList<String>();
        final List<Drawable> images = new ArrayList<Drawable>();
        for(int i = 0; i < 56; i++)
        {
            l.add("Recommended App " + i);
            if(i%3==0)
                images.add(getApplicationContext().getResources().getDrawable(R.drawable.ic_action_settings));
            else
                images.add(getApplicationContext().getResources().getDrawable(R.drawable.ic_launcher));
        }

        ArrayAdapter<String> ladapt = new AppArrayAdapter(this, l, images);
        lv.setAdapter(ladapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                String app=l.get(position);
                Toast.makeText(getApplicationContext(), "Selected : " + app, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recommendations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
