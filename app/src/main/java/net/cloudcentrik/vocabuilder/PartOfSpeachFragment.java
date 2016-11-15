package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class PartOfSpeachFragment extends Fragment {

    private WordDbAdapter dbHelper;
    private ArrayList<Word> allWords;

    private Context globalContext = null;

    private String userAnswer;
    private TextView txtPartofspeachQuestion;

    public PartOfSpeachFragment() {
        // Required empty public constructor
        this.userAnswer = "";
    }


    public static PartOfSpeachFragment newInstance(String param1, String param2) {

        return new PartOfSpeachFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalContext = getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part_of_speach, container, false);

        txtPartofspeachQuestion = (TextView) view.findViewById(R.id.txtEditPartofspeechWord);

        //part of speach spinner start
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_partofspeach);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(globalContext,
                R.array.part_of_speech, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                userAnswer = item;
                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner end

        Button endButton = (Button) view.findViewById(R.id.btnPartofspeachEnd);
        endButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.btnPartofspeachCheckAnswer);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //createAQuiz();
                checkAnswer();
                createAQuiz();
            }
        });

        Button nextButton = (Button) view.findViewById(R.id.btnPartofspeachNext);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //
                ((QuizActivity) getActivity()).setCurrentItem(0, true);
            }
        });
        // Inflate the layout for this fragment
        return view;
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

    private void getAllWords() {
        this.allWords = dbHelper.getAllWords();

        Collections.shuffle(allWords);

        for (int i = 0; i < allWords.size(); i++) {
           /* Log.i("WORD " + i, "-" + allWords.get(i).getSwedish()+"-" + allWords.get(i).getEnglish()+"-" +
                    allWords.get(i).getExample()+"-" + allWords.get(i).getEtten()+"-" + allWords.get(i).getPartOfSpeach()+"-" +
                    allWords.get(i).getCreateDate());*/
        }
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
                Word w = allWords.get(0);
                txtPartofspeachQuestion.setText("'" + w.getSwedish());
            }
        }

    }

    public void checkAnswer() {

        String etten = allWords.get(0).getPartOfSpeach();
        Log.i("USER ANSWER :", userAnswer);
        Log.i("Correct ANSWER :", etten);


        if (userAnswer.equals(etten)) {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Answer");
            alertDialog.setMessage("Correct answer..'" + allWords.get(0).getSwedish() + "' is " + allWords.get(0).getPartOfSpeach());
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
            alertDialog.setMessage("Wrong answer..'" + allWords.get(0).getSwedish() + "' is " + allWords.get(0).getPartOfSpeach());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        Collections.shuffle(allWords);
    }


}
