package ca.bcit.comp3717.applesauce;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class AsyncAppDetail extends AsyncTask<String, Void, ArrayList<AppInfo>>
{
    public AppDetailResponse delegate = null;
    private final static String PLAYSTOREAPI = "http://api.playstoreapi.com/v1.1/apps/";
    private final static String PLAYSTOREAPI_KEY = "?key=f52eb5c9f83198478dfca870331dbfd1";

    private ArrayList<AppInfo> getAppInfo(ArrayList<String> recApp_packageName) {

        HttpClient httpClient = new DefaultHttpClient();
        ArrayList<AppInfo> recAppList = new ArrayList<AppInfo>();

        // This code will use sample JSONObject from the sample class to save queries from
        // PlaystoreAPI key
        Iterator<JSONObject> it = Sample.getSampleRecApps().iterator();
        while(it.hasNext())
        {
            JSONObject JSONtestApp = it.next();

            try
            {

                recAppList.add(new AppInfo(JSONtestApp.getString("appName") + "                                               ",
                                           JSONtestApp.getString("packageID"),
                                           null,
                                           0,
                                           createDrawableFromUrl(JSONtestApp.getString("logo"))));
            }
            catch(Exception e)
            {
                Log.d("AsyncAppDetail", "Sample apps");
            }
        }

        return recAppList;
        // End of sample query


        /*
        // Uncomment this when ready for actual process
        // End of sample

        //Iterator<String> it = recApp_packageName.iterator();

        //while(it.hasNext())
        //debugging: only display 2 recommended apps
        for(int i = 0; i < 2; i++)
        {
            String URL = PLAYSTOREAPI +
                    it.next() +
                    recApp_packageName.get(i) +
                    PLAYSTOREAPI_KEY;

            HttpGet httpGet = new HttpGet(URL);
            StringBuilder stringBuilder = new StringBuilder();

            try
            {
                HttpResponse response = httpClient.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    inputStream.close();

                    // Create recommended app AppInfo
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    // end of debug

                    recAppList.add(new AppInfo(jsonObject.getString("appName"),
                            jsonObject.getString("packageID"),
                            null,
                            0,
                            createDrawableFromUrl(jsonObject.getString("logo"))));
                } else {
                    Log.d("JSON", "Failed to download file");
                }
            } catch (Exception e) {
                Log.d("readJSONFeed", e.getLocalizedMessage());
            }
        }

        return recAppList;

        // End of actual process
        */
    }

    public Drawable createDrawableFromUrl(String imageURL)
    {
        Drawable drawable = null;

        try
        {
            InputStream inputStream = new URL(imageURL).openStream();
            drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        }
        catch (MalformedURLException ex)
        {
            Log.d("createDrawableFromUrl", "MalformedURLException");
        }
        catch (IOException ex)
        {
            Log.d("createDrawableFromUrl", "IOException");
        }

        return drawable;
    }

    public ArrayList<String> getRecAppsFromJSONFeed(String packageName)
    {

        ArrayList<String> recApps = new ArrayList<String>();

        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();

        String URL = PLAYSTOREAPI +
                packageName +
                PLAYSTOREAPI_KEY;

        HttpGet httpGet = new HttpGet(URL);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
                inputStream.close();

                JSONObject  jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray  = jsonObject.getJSONArray("recommendedApps");

                if(jsonArray != null)
                {
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        recApps.add(jsonArray.get(i).toString());
                    }

                    return recApps;
                }
            }
            else
            {
                Log.d("JSON", "Failed to download file");
            }
        }
        catch (Exception e)
        {
            Log.d("getRecAppsFromJSONFeed", e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    protected ArrayList<AppInfo> doInBackground(String... packageName)
    {
        // Uncomment this for actual process
        //return getAppInfo(getRecAppsFromJSONFeed(packageName[0]));
        // End of actual process

        // Using Sample class for test query
        return getAppInfo(null);
        // End of test query

    }

    @Override
    protected void onPostExecute(ArrayList<AppInfo> result)
    {
        try
        {
            delegate.processFinish(result);
        }
        catch (Exception e)
        {
            Log.d("GetRecommendationsJSONFeedTask", e.getLocalizedMessage());
        }
    }
}

