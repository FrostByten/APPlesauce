package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IgnoreAppArrayAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private List<String> values;
    private List<Drawable> images;
    private List<String> origValues;
    private List<Drawable> origImages;
    private final IgnoredAppDataSource datasource = new IgnoredAppDataSource(getContext());

    public IgnoreAppArrayAdapter(Context context, List<String> values, List<Drawable> images)
    {
        super(context, R.layout.app_ignore_listing, values);
        this.context = context;
        this.values = values;
        this.images = images;
    }

    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Pair<String, Drawable>> results = new ArrayList<Pair<String, Drawable>>();

                if (origValues == null && origImages == null)
                {
                    origValues = values;
                    origImages = images;
                }
                if (constraint != null) {
                    if (origValues != null && origValues.size() > 0) {

                        for (int i = 0; i < origValues.size(); i++)
                        {
                            if(origValues.get(i).toLowerCase().contains(constraint.toString().toLowerCase()))
                            {
                                System.out.println("found " + origValues.get(i).toLowerCase());
                                results.add(Pair.create(origValues.get(i),origImages.get(i)));
                            }
                        }
                    }

                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {

                ArrayList<Pair<String, Drawable>> temp = (ArrayList<Pair<String, Drawable>>) results.values;

                values = new ArrayList<String>();
                images = new ArrayList<Drawable>();

                for(int i = 0; i < temp.size(); i++)
                {
                    values.add((temp.get(i)).first);
                    images.add((temp.get(i)).second);
                }

                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.size();
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
                ignoreCB.setChecked(false);
            }
            else
            {
                ignoreCB.setChecked(true);
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
                        datasource.deleteIgnoredApp(textView.getText().toString());

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
                        datasource.ignoreApp(textView.getText().toString());
                    }
                    catch (SQLException e)
                    {
                        Log.d("IgnoredAppArrayAdapter: onCheckedChanged open failed", e.getLocalizedMessage());
                    }
                    datasource.close();
                }
            }
        });

        return rowView;
    }
}
