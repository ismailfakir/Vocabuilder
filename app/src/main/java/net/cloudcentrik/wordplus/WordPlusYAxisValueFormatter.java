package net.cloudcentrik.wordplus;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by ismail on 2016-02-16.
 */
public class WordPlusYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public WordPlusYAxisValueFormatter() {
        // use one decimal
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // write your logic here
        // access the YAxis object to get more information
        return "" + ((int) value);
    }
}