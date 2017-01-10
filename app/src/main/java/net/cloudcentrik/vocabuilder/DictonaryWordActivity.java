package net.cloudcentrik.vocabuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ismail on 2016-01-03.
 */
public class DictonaryWordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private WordDbAdapter dbHelper;

    private TextView txtSwedish;
    private TextView txtEnglish;
    private TextView txtPartOfSpeech;
    private TextView txtExampleSwedish;
    private TextView txtExampleEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictonary_word);

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

        txtSwedish = (TextView) findViewById(R.id.textDictonarySwedish);
        txtEnglish = (TextView) findViewById(R.id.textDictonaryEnglish);
        txtPartOfSpeech = (TextView) findViewById(R.id.textDictonaryPartOfSpeach);
        txtExampleSwedish = (TextView) findViewById(R.id.textDictonaryExampleSwedish);
        txtExampleEnglish = (TextView) findViewById(R.id.textDictonaryExampleEnglish);


        createWordView();
    }

    private void createWordView() {

        Typeface tf=Typeface.createFromAsset(this.getAssets(),"font/Roboto-Thin.ttf");
        Typeface tf1=Typeface.createFromAsset(this.getAssets(),"font/hotrocks.ttf");
        Typeface tf2=Typeface.createFromAsset(this.getAssets(),"font/Fabrica.otf");
        DictonaryWord word = getIntent().getParcelableExtra("DictonaryWord");
        txtSwedish.setText(word.getSwedish());
        txtSwedish.setTypeface(tf);

        txtEnglish.setText(word.getEnglish());
        txtEnglish.setTypeface(tf);

        txtPartOfSpeech.setText(word.getPartOfSpeech());
        txtPartOfSpeech.setTypeface(tf2);

        txtExampleSwedish.setText(word.getSwedishExample());
        txtExampleSwedish.setTypeface(tf2);
        txtExampleEnglish.setText(word.getEnglishExample());
        txtExampleEnglish.setTypeface(tf2);

    }



    public void deleteWord() {
        final TextView svTxt = (TextView) findViewById(R.id.textSV);
        if (dbHelper.deleteWord(svTxt.getText().toString())) {
            Toast.makeText(DictonaryWordActivity.this, "Word Deleted", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(DictonaryWordActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final Intent myIntent;

        switch (item.getItemId()) {

            case R.id.btnDeleteWord:
                //delete confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DictonaryWordActivity.this);
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
                return (true);

            case R.id.btnEditWord:
                //showInputDialog();
                Word word = getIntent().getParcelableExtra("word");

                //Word ww=new Word("TEST","English","Example","ETT","verb","date");
                Intent i = new Intent(DictonaryWordActivity.this, EditWordActivity.class);

                i.putExtra("editedword", word);

                startActivity(i);
                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onResume(){
        super.onResume();
        //ts.setText("test");
    }

}