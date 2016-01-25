package net.cloudcentrik.vocabuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by ismail on 2016-01-03.
 */
public class WordActivity extends AppCompatActivity {

    private BootstrapButton buttonEdit;
    private BootstrapButton deleteButton;
    private ImageButton backButton;
    private WordDbAdapter dbHelper;

    private TextView ts;
    private TextView te;
    private TextView tx;

    private EditText editTextSV;
    private EditText editTextEN;
    private EditText editTextEX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_new_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        ts = (TextView) findViewById(R.id.textSV);
        te = (TextView) findViewById(R.id.textEN);
        tx = (TextView) findViewById(R.id.textEX);

        //setDialogueText();

        createWordView();

        // edit button
        buttonEdit = (BootstrapButton) findViewById(R.id.btnEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        //delete button
        deleteButton = (BootstrapButton) findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                deleteWord(v);
                finish();

                //Toast.makeText(WordActivity.this, "Delete button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        //back button
        backButton = (ImageButton) findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }

    private void createWordView() {

        Word word = getIntent().getParcelableExtra("word");
        ts.setText(word.getSwedish());
        te.setText(word.getEnglish());
        tx.setText(word.getExample());

    }

    public void setDialogueText(View v) {

        editTextSV = (EditText) v.findViewById(R.id.txtEditSwedish);
        editTextSV.setText(ts.getText());

        editTextEN = (EditText) v.findViewById(R.id.txtEditEnglish);
        editTextEN.setText(te.getText());

        editTextEX = (EditText) v.findViewById(R.id.txtEditExample);
        editTextEX.setText(tx.getText());

    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WordActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialoge_edit, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WordActivity.this);
        alertDialogBuilder.setView(promptView);

        setDialogueText(promptView);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());

                        Word w = new Word(editTextSV.getText().toString(), editTextEN.getText().toString(), editTextEX.getText().toString());

                        editWord(w);

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void deleteWord(View v) {
        final TextView svTxt = (TextView) findViewById(R.id.textSV);
        if (dbHelper.deleteWord(svTxt.getText().toString())) {
            Toast.makeText(WordActivity.this, "Word Deleted", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(WordActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }


    }

    public void editWord(Word w) {
        if (dbHelper.updateWord(w)) {
            Toast.makeText(WordActivity.this, "Word Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(WordActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }
}
