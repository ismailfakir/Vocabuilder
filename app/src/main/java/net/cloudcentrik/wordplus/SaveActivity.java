package net.cloudcentrik.wordplus;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class SaveActivity extends AppCompatActivity {

    private WordDbAdapter dbHelper;
    private TextView txtStatus;
    private EditText fileName;
    private TextView txtHeading;

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

        //txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtHeading=(TextView) findViewById(R.id.textHeading);
        WordplusUtils.setTypeface(txtHeading,"Roboto-Light.ttf",this);

        final Button button = (Button) findViewById(R.id.btnSaveWordList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                /*String fPath=CreatePDF.createPdfWordList(dbHelper.getAllWords());
                txtStatus.setText("Word List Saved to : "+fPath);
                Toast.makeText(SaveActivity.this, "Word List Created in " + fPath, Toast.LENGTH_SHORT).show();*/
                /*String file = fileName.getText().toString();
                if (TextUtils.isEmpty(file)) {
                    fileName.setError("Please enter a file name");
                    return;
                }*/

                BackgroundTask bt = new BackgroundTask(SaveActivity.this, dbHelper.getAllWords(), "WordList");
                bt.execute();

            }
        });

        final Button buttonEmail = (Button) findViewById(R.id.btnEmailWordList);
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                BackgroundTask bt = new BackgroundTask(SaveActivity.this, dbHelper.getAllWords(), "WordList");
                bt.execute();

                //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordplus";

                ShareViaEmail();
            }
        });

    }

    @Override
    public void onStop() {
        dbHelper.close();
        super.onStop();
    }

    private void ShareViaEmail() {
        try {

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            String message="Please find your word list attachment with this email from Word Plus";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Word list from wordplus");
            String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordplus/WordList.pdf";
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(fileName)));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setData(Uri.parse("mailto:ismail7043@gmail.com"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

            startActivity(intent);
        } catch(Exception e)  {
            System.out.println("is exception raises during sending mail"+e);
        }
    }
}
