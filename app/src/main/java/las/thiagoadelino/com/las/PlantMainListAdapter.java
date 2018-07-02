package las.thiagoadelino.com.las;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import las.thiagoadelino.com.domain.Plant;

public class PlantMainListAdapter extends ArrayAdapter<Plant> {
    public PlantMainListAdapter(@NonNull Context context, int resource, @NonNull List<Plant> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Plant plant = getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        }


        convertView.setTag(plant);
        TextView mainText = (TextView) convertView.findViewById(android.R.id.text1);
        TextView detailText = (TextView) convertView.findViewById(android.R.id.text2);

        mainText.setText(plant.getScientificName());
        mainText.setTypeface(Typeface.DEFAULT_BOLD);
        detailText.setText(plant.getCommonName());

        return convertView;
    }
}
