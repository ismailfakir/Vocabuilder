package net.cloudcentrik.vocabuilder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ismail on 2016-01-03.
 */

public class Word implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
    private String swedish;
    private String english;
    private String example;
    private String etten;
    private String partOfSpeach;
    private String createDate;

    public Word(String swedish, String english, String example, String etten, String partOfSpeach, String createDate) {
        this.swedish = swedish;
        this.english = english;
        this.example = example;
        this.etten = etten;
        this.partOfSpeach = partOfSpeach;
        this.createDate = createDate;
    }

    public Word() {

    }

    protected Word(Parcel in) {
        swedish = in.readString();
        english = in.readString();
        example = in.readString();
        etten = in.readString();
        partOfSpeach = in.readString();
        createDate = in.readString();
    }

    /*public Word(String swedish, String english, String example) {
        this.swedish = swedish;
        this.english = english;
        this.example = example;
    }
*/
    /*public Word(String swedish, String english, String example, String etten, String partOfSpeach) {
        this.swedish = swedish;
        this.english = english;
        this.example = example;
        this.etten = etten;
        this.partOfSpeach = partOfSpeach;
    }*/

    public String getCreateDate() {

        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEtten() {

        return etten;
    }

    public void setEtten(String etten) {
        this.etten = etten;
    }

    public String getPartOfSpeach() {
        return partOfSpeach;
    }

    public void setPartOfSpeach(String partOfSpeach) {
        this.partOfSpeach = partOfSpeach;
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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(swedish);
        dest.writeString(english);
        dest.writeString(example);
        dest.writeString(etten);
        dest.writeString(partOfSpeach);
        dest.writeString(createDate);
    }
}

