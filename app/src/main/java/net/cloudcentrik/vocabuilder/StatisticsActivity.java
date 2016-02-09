package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

/**
 * Created by ismail on 2016-01-02.
 */
public class StatisticsActivity extends AppCompatActivity {
    private WordDbAdapter dbHelper;
    private TextView txtNoOfWord;
    private TextView txtNoun;
    private TextView txtProNoun;
    private TextView txtAdjective;
    private TextView txtVerb;
    private TextView txtAdVerb;
    private TextView txtPreposition;
    private TextView txtInterjection;
    private TextView txtConjunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.statistic_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        this.setStastistics();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics_menu, menu);
        return true;
    }

    public void setStastistics() {

        txtNoOfWord = (TextView) findViewById(R.id.noOfWord);
        txtNoOfWord.setText("" + dbHelper.countWords());

        txtNoun = (TextView) findViewById(R.id.txtNoun);
        txtNoun.setText("" + dbHelper.countPartofSpeach("Noun"));

        txtProNoun = (TextView) findViewById(R.id.txtProNoun);
        txtProNoun.setText("" + dbHelper.countPartofSpeach("Pronoun"));

        txtAdjective = (TextView) findViewById(R.id.txtAdjective);
        txtAdjective.setText("" + dbHelper.countPartofSpeach("Adjective"));

        txtVerb = (TextView) findViewById(R.id.txtVerb);
        txtVerb.setText("" + dbHelper.countPartofSpeach("Verb"));

        txtAdVerb = (TextView) findViewById(R.id.txtAdverb);
        txtAdVerb.setText("" + dbHelper.countPartofSpeach("Adverb"));

        txtPreposition = (TextView) findViewById(R.id.txtPrePosition);
        txtPreposition.setText("" + dbHelper.countPartofSpeach("Preposition"));

        txtInterjection = (TextView) findViewById(R.id.txtInterjection);
        txtInterjection.setText("" + dbHelper.countPartofSpeach("Interjection"));

        txtConjunction = (TextView) findViewById(R.id.txtConjunction);
        txtConjunction.setText("" + dbHelper.countPartofSpeach("Conjunction"));

    }
}
