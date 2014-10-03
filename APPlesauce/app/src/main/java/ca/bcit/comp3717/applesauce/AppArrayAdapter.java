package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppArrayAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final List<String> values;
    private final List<Integer> images;

    public AppArrayAdapter(Context context, List<String> values, List<Integer> images)
    {
        super(context, R.layout.app_listing, values);
        this.context = context;
        this.values = values;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.app_listing, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values.get(position));
        imageView.setImageResource(images.get(position));

        return rowView;
    }
}
