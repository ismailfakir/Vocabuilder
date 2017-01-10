package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ismail on 2016-02-11.
 */
public class MeaningFragment extends Fragment {

    private WordDbAdapter dbHelper;
    private ArrayList<DictonaryWord> allWords;
    private TextView txtMeaningQuestion;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    private Context globalContext = null;

    private String userAnswer;

    public MeaningFragment() {
        // Required empty public constructor

        this.userAnswer = "";
    }

    public static MeaningFragment newInstance() {
        return new MeaningFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meaning, container, false);
        //return inflater.inflate(R.layout.fragment_meaning, container, false);

        txtMeaningQuestion = (TextView) view.findViewById(R.id.txtMeaningQuestion);

        option1 = (RadioButton) view.findViewById(R.id.radioMeaning1);
        option2 = (RadioButton) view.findViewById(R.id.radioMeaning2);
        option3 = (RadioButton) view.findViewById(R.id.radioMeaning3);
        option4 = (RadioButton) view.findViewById(R.id.radioMeaning4);

        Button endButton = (Button) view.findViewById(R.id.btnMeaningEnd);
        endButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });

        Button nextButton = (Button) view.findViewById(R.id.btnMeaningNext);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //
                ((QuizActivity) getActivity()).setCurrentItem(1, true);
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.btnMeaningCheckAnswer);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //createAQuiz();
                checkAnswer();
                createAQuiz();
            }
        });

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.radioGroup1);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMeaning1:
                        // do operations specific to this selection
                        //checkAnswer(1);
                        userAnswer = option1.getText().toString();
                        break;

                    case R.id.radioMeaning2:
                        // do operations specific to this selection
                        //checkAnswer(2);
                        userAnswer = option2.getText().toString();
                        break;

                    case R.id.radioMeaning3:
                        // do operations specific to this selection
                        //checkAnswer(3);
                        userAnswer = option3.getText().toString();
                        break;
                    case R.id.radioMeaning4:
                        // do operations specific to this selection
                        //checkAnswer(4);
                        userAnswer = option4.getText().toString();
                        break;

                }


            }
        });


        return view;
    }

    private void getAllWords() {
        this.allWords = dbHelper.getAllWords();
        Collections.shuffle(allWords);
    }

    public int generateRandonInRange(int min, int max) {
        //int min = 0;
        //int max = (int)dbHelper.countWords();

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        return randomNumber;
    }

    private void createAQuiz() {
        if (allWords != null) {
            if (allWords.size() < 4) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Not Enough Word");
                alertDialog.setMessage("You need to add alleast four word in word list to Test");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.dismiss();
                                getActivity().finish();
                            }
                        });
                alertDialog.show();

            } else {
                DictonaryWord w = allWords.get(0);
                txtMeaningQuestion.setText("What is the meaning of '" + w.getSwedish() + "' ?");


                int option[] = VocabuilderUtils.RandomizeArray(0, 3);

                option1.setText(allWords.get(option[0]).getEnglish());
                option2.setText(allWords.get(option[1]).getEnglish());
                option3.setText(allWords.get(option[2]).getEnglish());
                option4.setText(allWords.get(option[3]).getEnglish());
            }
        } else {
            txtMeaningQuestion.setText("No Question to asked");
            option1.setText("NO DATA");
            option2.setText("NO DATA");
            option3.setText("NO DATA");
            option4.setText("NO DATA");
        }

        /*Cursor c=dbHelper.fetchAllWords();
        c.moveToFirst();
        String s=c.getString(c.getColumnIndex("swedish"));

        txtMeaningQuestion.setText("What is the meaning of " + s + " ?");*/

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new WordDbAdapter(globalContext);

        dbHelper.open();


        allWords = new ArrayList<DictonaryWord>();

        getAllWords();

        createAQuiz();

    }


    public void checkAnswer() {

        if (userAnswer.equals(allWords.get(0).getEnglish())) {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Answer");
            alertDialog.setMessage("Correct answer....Meaning of '" + allWords.get(0).getSwedish() + "' is " + userAnswer);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setIcon(R.mipmap.ic_right);
            alertDialog.show();

        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Answer");
            alertDialog.setIcon(R.mipmap.ic_wrong);
            alertDialog.setMessage("Wrong answer....Meaning of '" + allWords.get(0).getSwedish() + "' is " + allWords.get(0).getEnglish());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        option1.setChecked(false);
        option2.setChecked(false);
        option3.setChecked(false);
        option4.setChecked(false);
        Collections.shuffle(allWords);
    }
}
