package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ismail on 2016-01-03.
 */
public class QuizActivity extends AppCompatActivity {
    private WordDbAdapter dbHelper;
    private ArrayList<Word> allWords;
    private TextView question;

    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.quiz_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        question = (TextView) findViewById(R.id.txtQuestion);

        allWords = new ArrayList<Word>();

        getAllWords();
        createAQuiz();


        /*ImageButton closeButton = (ImageButton) findViewById(R.id.btnQuizBack);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });*/

        Button submitButton = (Button) findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createAQuiz();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menue, menu);
        return true;
    }

    private void getAllWords() {
        this.allWords = dbHelper.getAllWords();
    }

    private void createAQuiz() {
        if (allWords != null) {
            //int n = generateRandonInRange(0, (int) dbHelper.countWords());
            Collections.shuffle(allWords);
            Word w = allWords.get(0);
            question.setText("What is the meaning of '" + w.getSwedish() + "' ?");

            option1 = (RadioButton) findViewById(R.id.radio0);
            option2 = (RadioButton) findViewById(R.id.radio1);
            option3 = (RadioButton) findViewById(R.id.radio2);
            option4 = (RadioButton) findViewById(R.id.radio3);

            int option[] = VocabuilderUtils.RandomizeArray(0, 3);

            option1.setText(allWords.get(option[0]).getEnglish());
            option2.setText(allWords.get(option[1]).getEnglish());
            option3.setText(allWords.get(option[2]).getEnglish());
            option4.setText(allWords.get(option[3]).getEnglish());
        }

        /*Cursor c=dbHelper.fetchAllWords();
        c.moveToFirst();
        String s=c.getString(c.getColumnIndex("swedish"));

        question.setText("What is the meaning of " + s + " ?");*/


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Collections.shuffle(allWords);

    }

    public int generateRandonInRange(int min, int max) {
        //int min = 0;
        //int max = (int)dbHelper.countWords();

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        return randomNumber;
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }
}
