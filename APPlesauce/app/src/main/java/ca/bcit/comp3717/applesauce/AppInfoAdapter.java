package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppInfoAdapter extends ArrayAdapter<AppInfo> {

    // View Lookup cache
    private static class ViewHolder
    {
        ImageView appIcon;
        TextView appTitle;
    }

    public AppInfoAdapter(Context context, ArrayList<AppInfo> apps)
    {
        super(context, R.layout.app_listing, apps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get data item for this position
        AppInfo appInfo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.app_listing, parent, false);

            viewHolder.appIcon = (ImageView)convertView.findViewById(R.id.logo);
            viewHolder.appTitle = (TextView)convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.appIcon.setImageDrawable(appInfo.getIcon());
        viewHolder.appTitle.setText(appInfo.getName());

        return convertView;
    }
}

