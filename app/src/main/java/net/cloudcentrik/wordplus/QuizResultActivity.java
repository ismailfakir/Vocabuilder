package net.cloudcentrik.wordplus;

/**
 * Created by ismail on 12/01/17.
 */

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizResultActivity extends AppCompatActivity {
    //TextView txtResult1,txtResult2,txtResult3;
    ImageView imgResult1,imgResult2,imgResult3;
    TextView txtResultUserAnswer1,txtResultUserAnswer2,txtResultUserAnswer3;
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

        //Answer a=(Answer) answersList.get(1);
        //Log.d("Answer",""+a.toString());



        TextView txtResultQuestion1=(TextView)findViewById(R.id.txtResultQuestion1);
        TextView txtResultQuestion2=(TextView)findViewById(R.id.txtResultQuestion2);
        TextView txtResultQuestion3=(TextView)findViewById(R.id.txtResultQuestion3);

        txtResultQuestion1.setText(answersList.get(0).getQuestion());
        txtResultQuestion2.setText(answersList.get(1).getQuestion());
        txtResultQuestion3.setText(answersList.get(2).getQuestion());


        TextView txtResultCorrectAnswer1=(TextView)findViewById(R.id.txtResultCorrectAnswer1);
        TextView txtResultCorrectAnswer2=(TextView)findViewById(R.id.txtResultCorrectAnswer2);
        TextView txtResultCorrectAnswer3=(TextView)findViewById(R.id.txtResultCorrectAnswer3);

        txtResultCorrectAnswer1.setText(answersList.get(0).getCorrectAnswer());
        txtResultCorrectAnswer2.setText(answersList.get(1).getCorrectAnswer());
        txtResultCorrectAnswer3.setText(answersList.get(2).getCorrectAnswer());

         txtResultUserAnswer1=(TextView)findViewById(R.id.txtResultUserAnswer1);
         txtResultUserAnswer2=(TextView)findViewById(R.id.txtResultUserAnswer2);
         txtResultUserAnswer3=(TextView)findViewById(R.id.txtResultUserAnswer3);

        txtResultUserAnswer1.setText(answersList.get(0).getUserAnswer());
        txtResultUserAnswer2.setText(answersList.get(1).getUserAnswer());
        txtResultUserAnswer3.setText(answersList.get(2).getUserAnswer());


        /*txtResult1=(TextView)findViewById(R.id.txtResult1);
        txtResult2=(TextView)findViewById(R.id.txtResult2);
        txtResult3=(TextView)findViewById(R.id.txtResult3);

        txtResult1.setText(answersList.get(0).getResult());
        txtResult2.setText(answersList.get(1).getResult());
        txtResult3.setText(answersList.get(2).getResult());*/

        imgResult1=(ImageView)findViewById(R.id.imgResult1);
        imgResult2=(ImageView)findViewById(R.id.imgResult2);
        imgResult3=(ImageView)findViewById(R.id.imgResult3);

        txtScore.setText("Your Score :"+countScore(answersList));

    }

    private int countScore(ArrayList<Answer> answerArrayList){

        Log.d("Size",""+answerArrayList.size());
        int score=0;

        Drawable drawableRight = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_right_green_24dp);
        Drawable drawableWrong = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_wrong_red_24dp);
        for(int i=0;i<answerArrayList.size();i++){

            Answer a=(Answer) answerArrayList.get(i);

            if(a.getResult().equals("correct")){
                score++;
                switch (i){
                    case 0:
                        txtResultUserAnswer1.setTextColor(Color.parseColor("#2e7d32"));
                        imgResult1.setImageDrawable(drawableRight);

                        break;
                    case 1:
                        txtResultUserAnswer2.setTextColor(Color.parseColor("#2e7d32"));
                        imgResult2.setImageDrawable(drawableRight);
                        break;
                    case 2:
                        txtResultUserAnswer3.setTextColor(Color.parseColor("#2e7d32"));
                        imgResult3.setImageDrawable(drawableRight);
                        break;
                }
            }
        }

        return score;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}