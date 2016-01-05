package net.cloudcentrik.vocabuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by ismail on 2016-01-03.
 */
public class WordActivity extends AppCompatActivity {

    private BootstrapButton buttonEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        createWordView();

        // components from main.xml
        buttonEdit = (BootstrapButton) findViewById(R.id.btnEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void createWordView() {

        Word word = getIntent().getParcelableExtra("word");
        TextView ts = (TextView) findViewById(R.id.textSV);
        TextView te = (TextView) findViewById(R.id.textEN);
        TextView tx = (TextView) findViewById(R.id.textEX);

        ts.setText(word.getSwedish());
        te.setText(word.getEnglish());
        tx.setText(word.getExample());

    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WordActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialoge_edit, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WordActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.txtEditSwedish);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
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
}
