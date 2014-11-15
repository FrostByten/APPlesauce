package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

public class IgnoreAppArrayAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final List<String> values;
    private final List<Drawable> images;
    private final IgnoredAppDataSource datasource = new IgnoredAppDataSource(getContext());

    public IgnoreAppArrayAdapter(Context context, List<String> values, List<Drawable> images)
    {
        super(context, R.layout.app_ignore_listing, values);
        this.context = context;
        this.values = values;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.app_ignore_listing, parent, false);
        final TextView textView = (TextView) rowView.findViewById(R.id.labelIL);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.logoIL);
        final CheckBox ignoreCB = (CheckBox)rowView.findViewById(R.id.ignoreCB);

        String appname = values.get(position);
        Drawable icon  = images.get(position);

        textView.setText(appname);
        imageView.setImageDrawable(icon);

        try
        {
            datasource.open();
            if(datasource.checkIfIgnored(appname))
            {
                ignoreCB.setChecked(true);
            }
            else
            {
                ignoreCB.setChecked(false);
            }
        }
        catch(SQLException e)
        {
            Log.d("IgnoredAppArrayAdapter: open failed", e.getLocalizedMessage());
        }

        ignoreCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    try
                    {
                        datasource.open();
                        datasource.ignoreApp(textView.getText().toString());
                    }
                    catch (SQLException e)
                    {
                        Log.d("IgnoredAppArrayAdapter: onCheckedChanged open failed", e.getLocalizedMessage());
                    }
                    datasource.close();

                } else {

                    try
                    {
                        datasource.open();
                        datasource.deleteIgnoredApp(textView.getText().toString());
                    }
                    catch (SQLException e)
                    {
                        Log.d("IgnoredAppArrayAdapter: onCheckedChanged open failed", e.getLocalizedMessage());
                    }
                    datasource.close();
                }

                // For Debugging
                //try
                //{
                //    datasource.open();
                //    List<Ignored> ignored = datasource.getAllIgnoredApps();

                //    for (Ignored i : ignored) {
                //        Toast.makeText(getContext(), "Ignored" + i.getAppName(), Toast.LENGTH_LONG).show();
                //    }
                //    datasource.close();
                //}
                //catch(NullPointerException e)
                //{
                //    Toast.makeText(getContext(), "No Ignored Apps", Toast.LENGTH_LONG).show();
                //}
                //catch(SQLException e)
                //{
                //    Toast.makeText(getContext(), "SQLException", Toast.LENGTH_LONG).show();
                //}


            }
        });

        return rowView;
    }
}
