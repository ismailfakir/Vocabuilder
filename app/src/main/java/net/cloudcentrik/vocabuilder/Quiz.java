package net.cloudcentrik.vocabuilder;



import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ismail on 10/01/17.
 */

public class Quiz {
    private int noOfQuestion;
    private Question [] questions;
    private WordDbAdapter dbHelper;
    private ArrayList<DictonaryWord> allWords;

    public Quiz(int noOfQuestion,Context ctx) {
        this.noOfQuestion = noOfQuestion;
        questions= new Question[this.noOfQuestion];

        dbHelper=new WordDbAdapter(ctx);
        dbHelper.open();
        this.allWords = dbHelper.getAllWords();
    }

    public Question [] createQuiz(){

        //suffle wordlist
        Collections.shuffle(allWords);
        for(int i=0;i<noOfQuestion;i++){
            questions[i]=this.createQuestion(i);
        }
        return questions;
    }

    private Question createQuestion(int id){
        Question q=new Question();
        q.setId(id+1);
        q.setQuestion(allWords.get(id).getSwedish());
        q.setAnswer(allWords.get(id).getEnglish());

        int option[] = VocabuilderUtils.RandomizeArray(0, 2);

        q.setOptionA(allWords.get(id+option[0]).getEnglish());
        q.setOptionB(allWords.get(id+option[1]).getEnglish());
        q.setOptionC(allWords.get(id+option[2]).getEnglish());
        return q;
    }
}
