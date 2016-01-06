package net.cloudcentrik.vocabuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by ismail on 2016-01-03.
 */
public class WordActivity extends AppCompatActivity {

    private BootstrapButton buttonEdit;
    private WordDbAdapter dbHelper;

    private TextView ts;
    private TextView te;
    private TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        ts = (TextView) findViewById(R.id.textSV);
        te = (TextView) findViewById(R.id.textEN);
        tx = (TextView) findViewById(R.id.textEX);

        createWordView();

        // components from main.xml
        buttonEdit = (BootstrapButton) findViewById(R.id.btnEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        BootstrapButton deleteButton = (BootstrapButton) findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                deleteWord(v);
                finish();

                //Toast.makeText(WordActivity.this, "Delete button pressed", Toast.LENGTH_SHORT).show();
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

        final EditText editTextSV = (EditText) v.findViewById(R.id.txtEditSwedish);
        final TextView txSv = (TextView) findViewById(R.id.textSV);
        editTextSV.setText(txSv.getText());

        final EditText editTextEN = (EditText) v.findViewById(R.id.txtEditEnglish);
        final TextView txEn = (TextView) findViewById(R.id.textEN);
        editTextEN.setText(txEn.getText());

        final EditText editTextEX = (EditText) v.findViewById(R.id.txtEditExample);
        final TextView txEx = (TextView) findViewById(R.id.textEX);
        editTextEX.setText(txEx.getText());

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
                        final TextView svTxt = (TextView) findViewById(R.id.txtEditSwedish);
                        final TextView enTxt = (TextView) findViewById(R.id.txtEditEnglish);
                        final TextView exTxt = (TextView) findViewById(R.id.txtEditExample);
                        Word w = new Word(svTxt.getText().toString(), enTxt.getText().toString(), exTxt.getText().toString());
                        editWord(w);
                        Toast.makeText(WordActivity.this, "Word Updated", Toast.LENGTH_SHORT).show();
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
