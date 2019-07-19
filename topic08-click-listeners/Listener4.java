// 0 setting OnClickListener(s) in xml
// xml
<Button
    android:id="@+id/button1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/btsubmit" />
// code
public class Main extends Activity implements onClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.button1).setOnClickListener(this);
        }
    public void onClick(View view) {
        // react to event code goes here
    }
}
