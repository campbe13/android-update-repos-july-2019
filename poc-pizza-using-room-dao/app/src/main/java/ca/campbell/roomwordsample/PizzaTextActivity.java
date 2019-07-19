package ca.campbell.roomwordsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PizzaTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_text);
        Bundle extras = getIntent().getExtras();
        ((TextView)findViewById(R.id.pizzatexttv))
                .setText(extras.getInt("pizzakey",99)+
                " " + extras.getString("pizzaname", "none"));
    }
}
