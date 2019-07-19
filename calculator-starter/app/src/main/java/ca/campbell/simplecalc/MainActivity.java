package ca.campbell.simplecalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//  TODO: add a field to input a 2nd number, get the input and use it in calculations
//  TODO: the inputType attribute forces a number keyboard, don't use it on the second field so you can see the difference

//  TODO: add buttons & methods for subtract, multiply, divide

//  TODO: add input validation: no divide by zero
//  TODO: input validation: set text to show error when it occurs

//  TODO: add a clear button that will clear the result & input fields

//  TODO: the hint for the result widget is hard coded, put it in the strings file

public class MainActivity extends Activity {
    EditText etNumber1;
    TextView result;
    double num1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get a handle on the text fields
        etNumber1 = (EditText) findViewById(R.id.num1);
        result = (TextView) findViewById(R.id.result);
    }  //onCreate()

    // TODO: replace with code that adds the two input numbers
    public void addNums(View v) {
        num1 = Double.parseDouble(etNumber1.getText().toString());
        result.setText(Double.toString(2*num1));
    }  //addNums()

}