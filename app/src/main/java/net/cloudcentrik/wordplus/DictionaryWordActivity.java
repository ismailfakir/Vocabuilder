package net.cloudcentrik.wordplus;

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
public class DictionaryWordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        DictionaryWord word = getIntent().getParcelableExtra("DictionaryWord");
        txtSwedish.setText(word.getSwedish());
        txtSwedish.setTypeface(tf);

        txtEnglish.setText(word.getEnglish());
        txtEnglish.setTypeface(tf);


        if(word.getPartOfSpeech().equals("no value")){
            txtPartOfSpeech.setText("Noun");
        }else if(word.getPartOfSpeech().equals("interjektion")){
            txtPartOfSpeech.setText("Interjection");
        }else{
            txtPartOfSpeech.setText(word.getPartOfSpeech());
        }

        txtPartOfSpeech.setTypeface(tf2);

        if(word.getSwedishExample().equals("no value")){
            txtExampleSwedish.setText("");
        }else{
            txtExampleSwedish.setText(word.getSwedishExample());
        }
        txtExampleSwedish.setTypeface(tf2);

        if(word.getEnglishExample().equals("no value")){
            txtExampleEnglish.setText("");
        }else{
            txtExampleEnglish.setText(word.getEnglishExample());
        }
        txtExampleEnglish.setTypeface(tf2);

    }



    public void addWord() {

        boolean b=dbHelper.wordExist(txtSwedish.getText().toString().toLowerCase());
        if(b){
            Toast.makeText(DictionaryWordActivity.this, "Word already exist", Toast.LENGTH_LONG).show();
            return;
        }

        String [] swedish=txtSwedish.getText().toString().toLowerCase().split(",");

        String partOfSpeech= WordPlusUtils.capitalizeString(txtPartOfSpeech.getText().toString());

        long r = dbHelper.createWord(swedish[0], txtEnglish.getText().toString(),
                txtExampleSwedish.getText().toString(), txtExampleEnglish.getText().toString(), partOfSpeech);
        if (r > 0) {

            Toast.makeText(DictionaryWordActivity.this, "Word added to list", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(DictionaryWordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final Intent myIntent;

        switch (item.getItemId()) {

            case R.id.btnAdWord:
                //delete confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DictionaryWordActivity.this);
                builder.setMessage("Are you sure you want to Add?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addWord();
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

        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        if (dbHelper == null){
            dbHelper = new WordDbAdapter(this);
            dbHelper.open();
        }
    }

}
