package net.cloudcentrik.wordplus;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ismail on 2016-02-09.
 */
public class WordplusUtils {

    public static int[] RandomizeArray(int a, int b) {
        Random rgen = new Random();  // Random number generator
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

        for (int s : array)
            System.out.println(s);

        return array;
    }

    public static int[] RandomizeArray(int[] array) {
        Random rgen = new Random();  // Random number generator

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

    /*
    // Create an array
Integer[] array = new Integer[]{1, 2, 3, 4};

//int[] array = new int[]{1, 2, 3, 4}; //does not work

// Shuffle the elements in the array
Collections.shuffle(Arrays.asList(array));
     */


}
