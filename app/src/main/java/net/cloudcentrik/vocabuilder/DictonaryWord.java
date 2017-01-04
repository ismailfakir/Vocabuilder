package net.cloudcentrik.vocabuilder;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ismail on 2016-11-20.
 */

public class DictonaryWord implements Parcelable {
    private String swedish;
    private String english;
    private String partOfSpeech;
    private String swedishExample;
    private String englishExample;
    private String dateCreated;

    public DictonaryWord() {
        this.swedish = "";
        this.english = "";
        this.partOfSpeech = "";
        this.swedishExample = "";
        this.englishExample = "";
        this.dateCreated = "";
    }

    public DictonaryWord(String swedish, String english, String partOfSpeech, String swedishExample, String englishExample, String dateCreated) {
        this.swedish = swedish;
        this.english = english;
        this.partOfSpeech = partOfSpeech;
        this.swedishExample = swedishExample;
        this.englishExample = englishExample;
        this.dateCreated = dateCreated;
    }

    public String getSwedish() {
        return swedish;
    }

    public void setSwedish(String swedish) {
        this.swedish = swedish;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getSwedishExample() {
        return swedishExample;
    }

    public void setSwedishExample(String swedishExample) {
        this.swedishExample = swedishExample;
    }

    public String getEnglishExample() {
        return englishExample;
    }

    public void setEnglishExample(String englishExample) {
        this.englishExample = englishExample;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(swedish);
        parcel.writeString(english);
        parcel.writeString(partOfSpeech);
        parcel.writeString(swedishExample);
        parcel.writeString(englishExample);
        parcel.writeString(dateCreated);

    }

    public static final Parcelable.Creator<DictonaryWord> CREATOR = new Parcelable.Creator<DictonaryWord>() {
        @Override
        public DictonaryWord createFromParcel(Parcel in) {
            return new DictonaryWord(in);
        }

        @Override
        public DictonaryWord[] newArray(int size) {
            return new DictonaryWord[size];
        }
    };

    protected DictonaryWord(Parcel in) {
        swedish = in.readString();
        english = in.readString();
        partOfSpeech = in.readString();
        swedishExample = in.readString();
        englishExample = in.readString();
        dateCreated = in.readString();
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
}
