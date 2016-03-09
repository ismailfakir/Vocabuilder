package net.cloudcentrik.vocabuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ismail on 2016-01-03.
 */
public class WordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button buttonEdit;
    private Button deleteButton;
    private ImageButton backButton;
    private WordDbAdapter dbHelper;

    private TextView ts;
    private TextView te;
    private TextView tx;
    private TextView tEtten;
    private TextView tpartOfSpeach;
    private TextView tDate;

    private EditText editTextSV;
    private EditText editTextEN;
    private EditText editTextEX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.word_toolbar);
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
        tEtten = (TextView) findViewById(R.id.textEtten);
        tpartOfSpeach = (TextView) findViewById(R.id.textPartOfSpeach);
        tDate = (TextView) findViewById(R.id.texDateCreated);


        //setDialogueText();

        createWordView();

        // edit button
        /*buttonEdit = (Button) findViewById(R.id.btnEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        //delete button
        deleteButton = (Button) findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //delete confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(WordActivity.this);
                builder.setMessage("Are you sure you want to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteWord();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });*/


    }

    private void createWordView() {

        Word word = getIntent().getParcelableExtra("word");
        ts.setText(word.getSwedish());
        te.setText(word.getEnglish());
        tx.setText(word.getExample());
        tEtten.setText(word.getEtten());
        tpartOfSpeach.setText(word.getPartOfSpeach());
        tDate.setText(word.getCreateDate());

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

                        Word w = new Word(editTextSV.getText().toString(), editTextEN.getText().toString(), editTextEX.getText().toString(), "etten", "noun", "date");

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
        alert.setTitle("Edit Word");
        alert.show();
    }

    public void deleteWord() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.word_menu, menu);
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
