package ca.bcit.comp3717.applesauce;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recommendations extends Activity
        implements AppDetailResponse
{
    //ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
    AsyncAppDetail getRecApps = new AsyncAppDetail();
    private final static String GOOGLEPLAY = "http://play.google.com/store/apps/details?id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_recommendations);

        setProgressBarIndeterminateVisibility(true);
        getRecApps.delegate = this;

        Bundle extras = getIntent().getExtras();
        //debug
        Toast.makeText(getApplicationContext(), extras.getString("PACKAGE_NAME"), Toast.LENGTH_LONG).show();

        // For emulator use only. extras.getString("PACKAGE_NAME") for real android device with
        // google play apps
        //getRecApps.execute(extras.getString("PACKAGE_NAME"));
        getRecApps.execute("com.hotheadgames.google.free.rawsniper");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recommendations, menu);
        return true;
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

    public void processFinish(final ArrayList<AppInfo> recApps)
    {
        setProgressBarIndeterminateVisibility(false);
        AppInfoAdapter adapter = new AppInfoAdapter(getBaseContext(), recApps);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listViewRec);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                AppInfo app = recApps.get(position);

                // debug
                Toast.makeText(getApplicationContext(), "Selected : " + app.getName(), Toast.LENGTH_SHORT).show();

                Main.gocount++;
                if(Main.gocount == 1)
                AchievementDataSource.makeChieve(Recommendations.this, "Give it a go", "Check out 1 app");
                else if(Main.gocount == 5)
                    AchievementDataSource.makeChieve(Recommendations.this, "Give it a good go", "Check out 5 apps");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(GOOGLEPLAY + app.getPName()));
                startActivity(intent);
            }
        });
    }
}