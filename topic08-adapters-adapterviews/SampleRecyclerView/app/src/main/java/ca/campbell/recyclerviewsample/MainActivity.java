package ca.campbell.recyclerviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Simple example of using a RecyclerView and a Custom Adatper
 * original code from
 * https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 *
 * @author P Campbell (some mods only)
 * @version 2018-10-15
 *
 * TODO:  add an image to the RecyclerView items
 *
 */

public class MainActivity extends AppCompatActivity
        implements CustomRecyclerViewAdapter.ItemClickListener{

    protected int countNew = 0;
    CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration decoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
     }

    @Override
     public void onItemClick(View view, int position) {
        adapter.addItem(adapter.getItem(position)+" "+countNew++);
        //adapter.notifyItemInserted(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "You clicked "
                + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
