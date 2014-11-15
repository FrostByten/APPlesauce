package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppArrayAdapter extends ArrayAdapter<String> implements Filterable
{
    private final Context context;
    private List<String> values;
    private List<Drawable> images;

    private List<String> origValues;
    private List<Drawable> origImages;

    public AppArrayAdapter(Context context, List<String> values, List<Drawable> images)
    {
        super(context, R.layout.app_listing, values);
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

        View rowView = inflater.inflate(R.layout.app_listing, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

        textView.setText(values.get(position));
        imageView.setImageDrawable(images.get(position));


        return rowView;
    }
}
