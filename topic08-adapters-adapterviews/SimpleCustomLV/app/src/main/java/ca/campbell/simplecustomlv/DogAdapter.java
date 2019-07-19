package ca.campbell.simplecustomlv;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Custom adapter for an AdapterView
 *
 * @author PMCampbell
 * @version today
 *
 */
public class DogAdapter extends BaseAdapter {
    private Context context;
    private final String TAG = "DOGADAPTR";
    String [] listDogs;
    int [] listIdDogs;
    LayoutInflater inflater;

    public DogAdapter(Context c, String[] list, int[] imageId) {
        context = c;
        listDogs = list;
        listIdDogs = imageId;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return listDogs.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) { return position; }

    public class ViewHolder {
        TextView tv; ImageView iv;
    }
    // create a new layout for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        View row = convertView;
        if (convertView == null) {
            row = inflater.inflate(R.layout.custom_item, null);

            vh.tv = (TextView) row.findViewById(R.id.itemTV);
            vh.iv = (ImageView) row.findViewById(R.id.itemIV);
            vh.tv.setText(listDogs[position]);
            vh.iv.setImageResource(listIdDogs[position]);
            // use the View.tag to ccmmunicate the viewholder we used, when it
            // is no longer new (see else)
            row.setTag(vh);
            // can set the listener here if I want to
            Log.d(TAG, "Adapter getView() new view: position "+position+" dog "+listDogs[position]);
        } else {
            vh = (ViewHolder) convertView.getTag();
            // spotted by Brian Doherty 2017-03-06
            // we are re using the view but changing the content
            vh.tv.setText(listDogs[position]);
            vh.iv.setImageResource(listIdDogs[position]);
            Log.d(TAG, "Adapter getView() reuse view: position "+position+" dog "+listDogs[position]);

        }

        // note it is better to set a clicklistener on the AdapterView
        row.setOnClickListener(  new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked " + listDogs[position], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DogActivity.class);
                i.putExtra("dog_name", listDogs[position]);
                i.putExtra("dog_image", listIdDogs[position]);
                context.startActivity(i);
            }
        });
        return row;
    }

}  // DogAdapter
