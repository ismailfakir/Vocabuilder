package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ismail on 2016-01-03.
 */
public class QuizActivity extends AppCompatActivity {
    private WordDbAdapter dbHelper;
    private ArrayList<Word> allWords;
    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        question = (TextView) findViewById(R.id.txtQuestion);

        allWords = new ArrayList<Word>();

        getAllWords();
        createAQuiz();


        ImageButton closeButton = (ImageButton) findViewById(R.id.btnQuizBack);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Button submitButton = (Button) findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createAQuiz();
            }
        });
    }

    private void getAllWords() {
        this.allWords = dbHelper.getAllWords();
    }

    private void createAQuiz() {
        if (allWords != null) {
            int n = generateRandonInRange(0, (int) dbHelper.countWords());
            Word w = allWords.get(n);
            question.setText("What is the meaning of " + w.getSwedish() + " ?");
        }

        /*Cursor c=dbHelper.fetchAllWords();
        c.moveToFirst();
        String s=c.getString(c.getColumnIndex("swedish"));

        question.setText("What is the meaning of " + s + " ?");*/


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
