package net.cloudcentrik.vocabuilder;

/**
 * Created by ismail on 13/01/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable{
    private int id;
    private String question;
    private String correctAnswer;
    private String userAnswer;
    private String result;


    public Answer(){

    }

    public Answer(int id, String question, String correctAnswer, String userAnswer, String result) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.result = result;
    }

    public static Creator<Answer> getCREATOR() {

        return CREATOR;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.question);
        dest.writeString(this.correctAnswer);
        dest.writeString(this.userAnswer);
        dest.writeString(this.result);
    }

    protected Answer(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.correctAnswer = in.readString();
        this.userAnswer=in.readString();
        this.result = in.readString();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
