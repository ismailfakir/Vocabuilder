package net.cloudcentrik.wordplus;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ismail on 2016-11-20.
 */

public class DictionaryWord implements Parcelable {
    public static final Parcelable.Creator<DictionaryWord> CREATOR = new Parcelable.Creator<DictionaryWord>() {
        @Override
        public DictionaryWord createFromParcel(Parcel in) {
            return new DictionaryWord(in);
        }

        @Override
        public DictionaryWord[] newArray(int size) {
            return new DictionaryWord[size];
        }
    };
    private String swedish;
    private String english;
    private String partOfSpeech;
    private String swedishExample;
    private String englishExample;
    private String dateCreated;

    public DictionaryWord() {
        this.swedish = "";
        this.english = "";
        this.swedishExample = "";
        this.englishExample = "";
        this.partOfSpeech = "";
        this.dateCreated = "";
    }

    public DictionaryWord(String swedish, String english, String swedishExample,
                          String englishExample, String partOfSpeech, String dateCreated) {
        this.swedish = swedish;
        this.english = english;
        this.swedishExample = swedishExample;
        this.englishExample = englishExample;
        this.partOfSpeech = partOfSpeech;
        this.dateCreated = dateCreated;
    }

    protected DictionaryWord(Parcel in) {
        swedish = in.readString();
        english = in.readString();
        swedishExample = in.readString();
        englishExample = in.readString();
        partOfSpeech = in.readString();
        dateCreated = in.readString();
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
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
        parcel.writeString(swedishExample);
        parcel.writeString(englishExample);
        parcel.writeString(partOfSpeech);
        parcel.writeString(dateCreated);

    }

    @Override
    public String toString() {
        return "DictionaryWord{" +
                "swedish='" + swedish + '\'' +
                ", english='" + english + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", swedishExample='" + swedishExample + '\'' +
                ", englishExample='" + englishExample + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
