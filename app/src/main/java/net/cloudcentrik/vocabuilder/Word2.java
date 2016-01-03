package net.cloudcentrik.vocabuilder;

/**
 * Created by ismail on 2015-12-27.
 */
public class Word2 {
    private String swedish;
    private String english;
    private String example;

    public Word2(String swedish, String english, String example) {
        this.swedish = swedish;
        this.english = english;
        this.example = example;
    }

    public Word2() {

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


}
