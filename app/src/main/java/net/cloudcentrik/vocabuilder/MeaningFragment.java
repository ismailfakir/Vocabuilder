package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ismail on 2016-02-11.
 */
public class MeaningFragment extends Fragment {

    private WordDbAdapter dbHelper;
    private ArrayList<Word> allWords;
    private TextView txtMeaningQuestion;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    private Context globalContext = null;

    public MeaningFragment() {
        // Required empty public constructor
    }

    public static MeaningFragment newInstance() {
        return new MeaningFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalContext = getActivity().getApplicationContext();


        /*Button submitButton = (Button) findViewById(R.id.btnMeaningCheckAnswer);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createAQuiz();
            }
        });*/

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
            //int n = generateRandonInRange(0, (int) dbHelper.countWords());
            //Collections.shuffle(allWords);
            Word w = allWords.get(0);
            txtMeaningQuestion.setText("What is the meaning of '" + w.getSwedish() + "' ?");


            int option[] = VocabuilderUtils.RandomizeArray(0, 3);

            option1.setText(allWords.get(option[0]).getEnglish());
            option2.setText(allWords.get(option[1]).getEnglish());
            option3.setText(allWords.get(option[2]).getEnglish());
            option4.setText(allWords.get(option[3]).getEnglish());
        } else {
            txtMeaningQuestion.setText("No Question to asked");
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


        allWords = new ArrayList<Word>();

        getAllWords();

        createAQuiz();

    }
}
