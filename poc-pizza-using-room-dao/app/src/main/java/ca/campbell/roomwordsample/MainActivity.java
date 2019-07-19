package ca.campbell.roomwordsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import ca.campbell.roomwordsample.Data.Pizza;

/**
 *  Pizza menu onclick leads to pizza description
 *  Illustrating the use of
 *  RecyclerView + Room for SQLite db
 *
 *  original code base from
 *      https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0
 *  ref to
 *      https://medium.com/@tonia.tkachuk/android-app-example-using-room-database-63f7091e69af
 *
 *  @author pmcampbell
 *  @version today
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final int NEW_PIZZA_ACTIVITY_REQUEST_CODE = 1 ;
    private PizzaViewModel mPizzaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPizzaActivity.class);
                startActivityForResult(intent, NEW_PIZZA_ACTIVITY_REQUEST_CODE);
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PizzaRecycAdapter adapter = new PizzaRecycAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPizzaViewModel = ViewModelProviders.of(this).get(PizzaViewModel.class);
        mPizzaViewModel.getAllPizzas().observe(this, new Observer<List<Pizza>>() {
            @Override
            public void onChanged(@Nullable final List<Pizza> pizzas) {
                // Update the cached copy of the pizzas in the adapter.
                adapter.setPizzas(pizzas);
            }
        });
    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == NEW_PIZZA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                Pizza pizza = new Pizza(data.getStringExtra(NewPizzaActivity.EXTRA_REPLY));
                mPizzaViewModel.insert(pizza);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
