package net.cloudcentrik.wordplus;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by ismail on 2016-02-09.
 */
public class WordPlusUtils {

    public static int[] RandomizeArray(int a, int b) {
        // Random number generator
        Random rgen = new Random();
        int size = b - a + 1;
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = a + i;
        }

        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    public static int[] RandomizeArray(int[] array) {
        // Random number generator
        Random rgen = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    public char[] remove(char[] symbols, char c) {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] == c) {
                char[] copy = new char[symbols.length - 1];
                System.arraycopy(symbols, 0, copy, 0, i);
                System.arraycopy(symbols, i + 1, copy, i, symbols.length - i - 1);
                return copy;
            }
        }
        return symbols;
    }

    public static void setTypeface(TextView textView, String fontName, Context context){

        Typeface tf=Typeface.createFromAsset(context.getAssets(),"font/"+fontName);
        textView.setTypeface(tf);

    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
    }

    public static String capitalizeString(String string) {

        if (string == null || string.trim().isEmpty()) {
            return string;
        }
        char c[] = string.trim().toLowerCase().toCharArray();
        c[0] = Character.toUpperCase(c[0]);

        return new String(c);

    }
}
