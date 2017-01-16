package net.cloudcentrik.vocabuilder;

/**
 * Created by ismail on 12/01/17.
 */

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.quiz_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        TextView txtScore=(TextView)findViewById(R.id.txtScore);

        Bundle b=getIntent().getExtras();

        ArrayList<Answer> answersList= b.getParcelableArrayList("answer");

        txtScore.setText("Score : :"+countScore(answersList));

        TextView txtResultQuestion1=(TextView)findViewById(R.id.txtResultQuestion1);
        TextView txtResultQuestion2=(TextView)findViewById(R.id.txtResultQuestion2);
        TextView txtResultQuestion3=(TextView)findViewById(R.id.txtResultQuestion3);

        txtResultQuestion1.setText(answersList.get(0).getQuestion());
        txtResultQuestion2.setText(answersList.get(1).getQuestion());
        txtResultQuestion3.setText(answersList.get(2).getQuestion());


        TextView txtResultAnswer1=(TextView)findViewById(R.id.txtResultAnswer1);
        TextView txtResultAnswer2=(TextView)findViewById(R.id.txtResultAnswer2);
        TextView txtResultAnswer3=(TextView)findViewById(R.id.txtResultAnswer3);

        txtResultAnswer1.setText(answersList.get(0).getAnswer());
        txtResultAnswer2.setText(answersList.get(1).getAnswer());
        txtResultAnswer3.setText(answersList.get(2).getAnswer());


        TextView txtResult1=(TextView)findViewById(R.id.txtResult1);
        TextView txtResult2=(TextView)findViewById(R.id.txtResult1);
        TextView txtResult3=(TextView)findViewById(R.id.txtResult1);

        txtResult1.setText(answersList.get(0).getResult());
        txtResult2.setText(answersList.get(1).getResult());
        txtResult3.setText(answersList.get(2).getResult());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menue, menu);
        return true;
    }

    private int countScore(ArrayList<Answer> answerArrayList){

        Log.d("Size",""+answerArrayList.size());
        int score=0;

        for(int i=0;i<answerArrayList.size();i++){

            Answer a=(Answer) answerArrayList.get(i);

            if(a.getResult().equals("correct")){
                score++;
            }
        }

        return score;
    }

}