package net.cloudcentrik.vocabuilder;

/**
 * Created by ismail on 13/01/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable{
    private int id;
    private String question;
    private String answer;
    private String result;

    public Answer(){

    }

    public Answer(int id, String question, String answer, String result) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.result = result;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
                ", answer='" + answer + '\'' +
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
        dest.writeString(this.answer);
        dest.writeString(this.result);
    }

    protected Answer(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.answer = in.readString();
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
