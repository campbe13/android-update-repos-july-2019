package ca.campbell.dbAsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * AddStudent.class
 * 
 * This Activity is a UI used simply to add records to the db
 *
 * It uses a DBHelper class for the sqlite database
 */
public class AddStudentActivity extends Activity implements OnClickListener {
    private Context context;
    private DBHelper dbh;
    TextView status;
    public static final String TAG = "DBADD";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context = getApplicationContext();
        Button btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        status = (TextView) findViewById(R.id.tv_status);
    }

    public void onClick(View v) {
        Log.d(TAG, "onClick");
        dbh = DBHelper.getDBHelper(this);
        // get the data from the UI
        String firstName = ((EditText) findViewById(R.id.et_name)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.et_lastName)).getText().toString();
        String className = ((EditText) findViewById(R.id.et_class)).getText().toString();
        String grade = ((EditText) findViewById(R.id.et_grade)).getText().toString();

        // add the data to the db
        InsertDataTask indata = new InsertDataTask();
        indata.execute(firstName, lastName, className, grade);
    } //onClick()

    private class InsertDataTask extends AsyncTask<String, Void, Long> {
        private final ProgressDialog dialog = new ProgressDialog(AddStudentActivity.this);

        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            this.dialog.setMessage("Inserting data...");
            this.dialog.show();
        }

        protected Long doInBackground(String... args) {
            Log.d(TAG, "doInBackground");
            return dbh.insertNewStudent(args[0], args[1], args[2], Integer.parseInt(args[3]));
            // insertNewStudent(firstName, lastName, className, grade);;
        }

        protected void onPostExecute(Long retcode) {
            Log.d(TAG, "onPostExecute");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (retcode != -1) {
                Toast.makeText(context, R.string.insert_ok, Toast.LENGTH_LONG).show();
                status.setText(R.string.insert_ok);
            } else
                status.setText(R.string.insert_fail);
        }
    } // InsertDataTask class (AsyncTask)
}  // Activity class