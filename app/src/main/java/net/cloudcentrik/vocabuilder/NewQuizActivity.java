package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by ismail on 11/01/17.
 */

public class NewQuizActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private TextView txtProgress;

    private Question [] questionList;
    private int questionNo=0;
    private int score=0;
    private int noOfQuestion=3;
    private Question currentQuestion=null;

    private Context globalContext = null;
    private WordDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalContext = this.getApplicationContext();
        setContentView(R.layout.activity_new_quiz);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.quiz_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        txtQuestion = (TextView)findViewById(R.id.textQuestion);
        txtProgress=(TextView)findViewById(R.id.textProgress);

        option1 = (RadioButton) findViewById(R.id.radio0);
        option2 = (RadioButton) findViewById(R.id.radio1);
        option3 = (RadioButton) findViewById(R.id.radio2);

        Button nextButton = (Button)findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //
                RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
                RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
                if(currentQuestion.getAnswer().equals(answer.getText()))
                {
                    score++;
                }
                if(questionNo<2){
                    questionNo++;
                    currentQuestion=questionList[questionNo];
                    setQuizView();
                }else{
                    /*Intent intent = new Intent(NewQuizActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();*/

                    finish();
                }

            }

        });

        Button submitButton = (Button)findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //

            }
        });

        Quiz quiz=new Quiz(noOfQuestion,this);
        questionList=quiz.createQuiz();
        currentQuestion=questionList[questionNo];

        /*dbHelper=new WordDbAdapter(this);
        Log.i("ERROR","NONE");
        dbHelper.open();
        dbHelper.getAllWords();*/

        setQuizView();

    }

    private void setQuizView(){

        this.txtProgress.setText(currentQuestion.getId()+" of "+questionList.length);
        this.txtQuestion.setText(currentQuestion.getQuestion());
        this.option1.setText(currentQuestion.getOptionA());
        this.option2.setText(currentQuestion.getOptionB());
        this.option3.setText(currentQuestion.getOptionC());

    }
}
