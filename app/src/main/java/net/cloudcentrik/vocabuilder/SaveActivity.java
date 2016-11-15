package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveActivity extends AppCompatActivity {

    private WordDbAdapter dbHelper;
    private TextView txtStatus;
    private EditText fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.save_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        fileName = (EditText) findViewById(R.id.editTextFileName);

        final Button button = (Button) findViewById(R.id.btnSaveWordList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                /*String fPath=CreatePDF.createPdfWordList(dbHelper.getAllWords());
                txtStatus.setText("Word List Saved to : "+fPath);
                Toast.makeText(SaveActivity.this, "Word List Created in " + fPath, Toast.LENGTH_SHORT).show();*/
                String file = fileName.getText().toString();
                if (TextUtils.isEmpty(file)) {
                    fileName.setError("Please enter a file name");
                    return;
                }

                BackgroundTask bt = new BackgroundTask(SaveActivity.this, dbHelper.getAllWords(), file);
                bt.execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        dbHelper.close();
        super.onStop();
    }
}
