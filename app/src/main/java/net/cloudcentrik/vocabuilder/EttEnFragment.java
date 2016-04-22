package net.cloudcentrik.vocabuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ismail on 2016-02-11.
 */
public class EttEnFragment extends Fragment {


    private SwitchCompat switchCompat;

    private WordDbAdapter dbHelper;
    private ArrayList<Word> allWords;

    private Context globalContext = null;

    private String userAnswer;
    private TextView txtEttEnQuestion;


    public EttEnFragment() {
        // Required empty public constructor
        this.userAnswer = "";
    }

    public static EttEnFragment newInstance() {
        return new EttEnFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalContext = getActivity().getApplicationContext();

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


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_etten, container, false);

        txtEttEnQuestion = (TextView) view.findViewById(R.id.txtEnEnWord);

        switchCompat = (SwitchCompat) view.findViewById(R.id.switch_compat);
        switchCompat.setSwitchPadding(40);
        //attach a listener to check for changes in state
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switchCompat.setText("EN");
                    //userAnswer = "EN";
                } else {
                    switchCompat.setText("ETT");
                    //userAnswer = "ETT";
                }

            }
        });

        Button endButton = (Button) view.findViewById(R.id.btnEttEnEnd);
        endButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.btnEttEnCheckAnswer);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //createAQuiz();
                checkAnswer();
                createAQuiz();
            }
        });

        Button nextButton = (Button) view.findViewById(R.id.btnEttenNext);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //
                ((QuizActivity) getActivity()).setCurrentItem(2, true);
            }
        });


        // Inflate the layout for this fragment
        return view;

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
                txtEttEnQuestion.setText("'" + w.getSwedish());
            }
        }

    }

    public void checkAnswer() {

        String etten = allWords.get(0).getEtten();
        Log.i("USER ANSWER :", userAnswer);
        Log.i("Correct ANSWER :", etten);


        if (userAnswer.equals(etten)) {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Answer");
            alertDialog.setMessage("Correct answer..'" + allWords.get(0).getSwedish() + "' is " + allWords.get(0).getEtten() + " word");
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
            alertDialog.setMessage("Wrong answer..'" + allWords.get(0).getSwedish() + "' is " + allWords.get(0).getEtten() + " word");
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
