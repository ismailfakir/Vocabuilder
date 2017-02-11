package net.cloudcentrik.wordplus;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ismail on 2015-12-28.
 */
public class AddNewActivity extends AppCompatActivity {

    private WordDbAdapter dbHelper;
    private TextView txtSwedish;
    private TextView txtEnglish;
    private TextView txtSwedishExample;
    private TextView txtEnglishExample;
    private TextInputLayout inputLayoutSwedish;
    private TextInputLayout inputLayoutEnglish;
    private TextInputLayout inputLayoutSwedishExample;
    private TextInputLayout inputLayoutEnglishExample;


    private String etten, partOfSpeach;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view from new_activity.xml
        setContentView(R.layout.activity_add_word);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_new_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //
        etten = "en";
        partOfSpeach = "Noun";


        //part of speach spinner start
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.part_of_speech, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                partOfSpeach = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(3);
        //spinner end

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        txtSwedish = (TextView) findViewById(R.id.txtSwedish);
        txtEnglish = (TextView) findViewById(R.id.txtEnglish);
        txtSwedishExample = (TextView) findViewById(R.id.txtSwedishExample);
        txtEnglishExample = (TextView) findViewById(R.id.txtEnglishExample);

        inputLayoutSwedish = (TextInputLayout) findViewById(R.id.input_layout_swedish);
        inputLayoutEnglish = (TextInputLayout) findViewById(R.id.input_layout_english);
        inputLayoutSwedishExample = (TextInputLayout) findViewById(R.id.input_layout_swedish_example);
        inputLayoutEnglishExample = (TextInputLayout) findViewById(R.id.input_layout_english_example);

        Button addButton = (Button) findViewById(R.id.btnAddWord);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Log.d("DATABASE","Testing");
                insertWord();
            }
        });
    }

    private void insertWord() {
        if (!validateData()) {
            return;
        }

        boolean b=dbHelper.wordExist(txtSwedish.getText().toString().toLowerCase());
        if(b){
            showWordExistDialog();
            //Toast.makeText(AddNewActivity.this, "Word already exist", Toast.LENGTH_LONG).show();
            return;
        }

        long r = dbHelper.createWord(txtSwedish.getText().toString().toLowerCase(), txtEnglish.getText().toString(),
                txtSwedishExample.getText().toString(), txtEnglishExample.getText().toString(), partOfSpeach);
        if (r > 0) {

            Toast.makeText(AddNewActivity.this, "Word added", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(AddNewActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearText() {

        txtSwedish.setText("");
        txtEnglish.setText("");
        txtSwedishExample.setText("");
        txtEnglishExample.setText("");

    }

    //input validation

    private boolean validateData() {
        boolean result = true;

        String swedish = txtSwedish.getText().toString();
        if (swedish == null || swedish.length()<2) {
            // We set the error message
            inputLayoutSwedish.setError("value required");
            result = false;
        }
        else
        // We remove error messages
            inputLayoutSwedish.setErrorEnabled(false);

        String english = txtEnglish.getText().toString();
        if (english == null || english.length()<2) {
            // We set the error message
            inputLayoutEnglish.setError("value required");
            result = false;
        }
        else
            // We remove error messages
            inputLayoutEnglish.setErrorEnabled(false);

        String swedishExample = txtSwedishExample.getText().toString();
        if (swedishExample == null || swedishExample.length()<2) {
            // We set the error message
            inputLayoutSwedishExample.setError("value required");
            result = false;
        }
        else
            // We remove error messages
            inputLayoutSwedishExample.setErrorEnabled(false);

        String englishExample = txtEnglish.getText().toString();
        if (englishExample == null || englishExample.length()<2) {
            // We set the error message
            inputLayoutEnglishExample.setError("value required");
            result = false;
        }
        else
            // We remove error messages
            inputLayoutEnglishExample.setErrorEnabled(false);

        return result;
    }

    private void showWordExistDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Word Exist");
        builder.setMessage("Word already exist in the word list. Try to add new word");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearText();
                dialog.cancel();
            }
        });
        builder.show();
    }
}
