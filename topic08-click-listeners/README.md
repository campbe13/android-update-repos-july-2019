# Coding samples to apply listeners

## Examples implementing the click listeners

1. using the xml attribute [Listener0](Listener0.java)
2. interface as type  [Listener1.java](Listener1.java)
3. member class implements listener  [Listener2.java](Listener2.java)
4. anonymous inner class [Listener3.java](Listener3.java)
5. Activity implements listener [Listener4.java](Listener4.java)

## [Android Cookbook](Android Cookbook_ Recipe Five Ways to Wire Up an Event Listener.pdf)

## Interface Type
note in the pdf at minimum the interface type

In Java an Interface can be used as a type, a variable is declared as an OnClickListener and assigned using new
OnClickListener(){...}, behind the scenes Java is creating an object (an Anonymous Class) that implements
OnClickListener. This has similar benefits to the first method.
```java
public class Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //use the handleClick variable to attach the event listener
        findViewById(R.id.button1).setOnClickListener(handleClick);
        }
    // *MUST BE PUBLIC NOT PRIVATE    
    private* OnClickListener handleClick = new OnClickListener(){
            public void onClick(View arg0) {
            Button btn = (Button)arg0;
            TextView tv = (TextView) findViewById(R.id.textview1);
            tv.setText("You pressed " + btn.getText());
            }
        };
}
```
