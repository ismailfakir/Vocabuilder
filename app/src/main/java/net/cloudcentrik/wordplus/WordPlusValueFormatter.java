package net.cloudcentrik.wordplus;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by ismail on 2016-02-16.
 */
public class WordPlusValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public WordPlusValueFormatter() {
        // use one decimal
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return "" + ((int) value);
    }

}