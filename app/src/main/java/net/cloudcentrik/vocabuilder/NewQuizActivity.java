package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by ismail on 11/01/17.
 */

public class NewQuizActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private TextView txtProgress;
    private RadioGroup radioGroup;

    private ArrayList<Question> questionList;
    private int questionNo = 0;
    private int score = 0;
    private int noOfQuestion = 3;
    private Question currentQuestion = null;

    private ArrayList<Answer> answerList;
    private String userAnswer;

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

        Quiz quiz = new Quiz(noOfQuestion, this);
        questionList = quiz.createQuiz();
        answerList = new ArrayList<Answer>();
        userAnswer="";

        txtQuestion = (TextView) findViewById(R.id.textQuestion);
        txtProgress = (TextView) findViewById(R.id.textProgress);

        radioGroup=(RadioGroup) findViewById(R.id.radioGroup1);
        option1 = (RadioButton) findViewById(R.id.radio0);
        option2 = (RadioButton) findViewById(R.id.radio1);
        option3 = (RadioButton) findViewById(R.id.radio2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        userAnswer = option1.getText().toString();
                        break;

                    case R.id.radio1:
                        userAnswer = option2.getText().toString();
                        break;

                    case R.id.radio2:
                        userAnswer = option3.getText().toString();
                        break;
                }
            }
        });

        this.setQuizView();

        Button nextButton = (Button) findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //check any radio button is checked
                int selected = radioGroup.getCheckedRadioButtonId();
                if(selected<0){
                    Toast.makeText(getApplicationContext(),"select your answer",Toast.LENGTH_LONG).show();

                    return;
                }

                Answer answer = new Answer();
                answer.setId(currentQuestion.getId());
                answer.setQuestion(currentQuestion.getQuestion());
                answer.setAnswer(userAnswer);

                Log.d("User Answer",""+userAnswer);
                Log.d("User Answer",""+currentQuestion.getQuestion());
                if (currentQuestion.getAnswer().equals(userAnswer)) {
                    answer.setResult("correct");
                } else {
                    answer.setResult("wrong");
                }
                answerList.add(answer);

                if (questionNo <2) {
                    questionNo++;

                    radioGroup.clearCheck();
                    setQuizView();
                } else {
                    questionNo = 0;
                    Intent intent = new Intent(NewQuizActivity.this, QuizResultActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("answer", answerList);
                    //b.putInt("score", answerList); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    //intent.putParcelableArrayListExtra("answer",answerList);
                    startActivity(intent);
                }

            }

        });

        Button submitButton = (Button) findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //
            }
        });

    }

    private void setQuizView() {

        currentQuestion = questionList.get(questionNo);
        //Log.d("Current question",""+currentQuestion.toString());

        this.txtProgress.setText(currentQuestion.getId() + " of " + questionList.size());
        this.txtQuestion.setText(currentQuestion.getQuestion());
        this.option1.setText(currentQuestion.getOptionA());
        this.option2.setText(currentQuestion.getOptionB());
        this.option3.setText(currentQuestion.getOptionC());

    }
}
