package net.cloudcentrik.vocabuilder;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismail on 2015-12-28.
 */
public class AddNewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private WordDbAdapter dbHelper;
    private TextView txtSwedish;
    private TextView txtEnglish;
    private TextView txtExample;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_addnew);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_new_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        //spinner
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Noun");
        categories.add("Pronoun");
        categories.add("Adjective");
        categories.add("Verb");
        categories.add("Adverb");
        categories.add("preposition");
        categories.add("conjunction");
        categories.add("interjection");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //spinner end

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);


        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        txtSwedish = (TextView) findViewById(R.id.txtSwedish);
        txtEnglish = (TextView) findViewById(R.id.txtEnglish);
        txtExample = (TextView) findViewById(R.id.txtExample);

        Button clearButton = (Button) findViewById(R.id.btnAddClear);
        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clearText();
            }
        });

        Button addButton = (Button) findViewById(R.id.btnAddWord);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                insertWord();
            }
        });
    }

    private void insertWord() {

        long r = dbHelper.createWord(txtSwedish.getText().toString(), txtEnglish.getText().toString(), txtExample.getText().toString());
        if (r > 0) {

            Toast.makeText(AddNewActivity.this, "Word added", Toast.LENGTH_SHORT).show();
            clearText();

        } else {
            Toast.makeText(AddNewActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearText() {

        txtSwedish.setText("");
        txtEnglish.setText("");
        txtExample.setText("");

    }

    /*
    private void updateUI(){
        swapCursor(dbHelper.getCursor());
        notifyDataSetChanged();
    }*/

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adnew_menu, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        arg0.setSelection(0);
    }


}
