package net.cloudcentrik.wordplus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by ismail on 2016-01-02.
 */
public class StatisticsActivity extends AppCompatActivity {
    float values[] = {700, 400, 100, 500, 600, 300, 900};
    private WordDbAdapter dbHelper;
    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_statistics);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.statistic_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle("Statistics");

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();


        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.chart);

        barChart.setDrawGridBackground(false);
        barChart.setGridBackgroundColor(Color.WHITE);


        barChart.setDescription("WORDS");  // set the description

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Noun"), 0));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Pronoun"), 1));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Adjective"), 2));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Verb"), 3));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Adverb"), 4));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Preposition"), 5));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Conjunction"), 6));
        entries.add(new BarEntry((float) dbHelper.countPartofSpeach("Interjection"), 7));
        entries.add(new BarEntry((float) dbHelper.getAllWords().size(), 8));


        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);

        dataset.setBarSpacePercent(35f);

        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Noun");
        labels.add("Pronoun");
        labels.add("Adjective");
        labels.add("Verb");
        labels.add("Adverb");
        labels.add("Preposition");
        labels.add("Conjunction");
        labels.add("Interjection");
        labels.add("Total");


        BarData data = new BarData(labels, dataset);

        data.setValueFormatter(new WordPlusValueFormatter());


        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTypeface(tf);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        //top
        YAxis yl = barChart.getAxisLeft();
        //yl.setTypeface(tf);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
        yl.setValueFormatter(new WordPlusYAxisValueFormatter());
        yl.setDrawZeroLine(false);
        yl.setDrawTopYLabelEntry(false);
        yl.setDrawLabels(false);

        //bottom
        YAxis yr = barChart.getAxisRight();
        //yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);
        yr.setValueFormatter(new WordPlusYAxisValueFormatter());

        barChart.setDescription("");    // Hide the description
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setDrawLabels(true);

        barChart.getLegend().setEnabled(false);

        barChart.setData(data); // set the data and list of lables into chart

        barChart.animateY(2500);


        /*Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);*/





    }


    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }


    private void showPieChart(PieChart mChart) {

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Noun"), 0));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Pronoun"), 1));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Adjective"), 2));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Verb"), 3));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Adverb"), 4));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Preposition"), 5));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Conjunction"), 6));
        entries.add(new Entry((float) dbHelper.countPartofSpeach("Interjection"), 7));


        PieDataSet dataset = new PieDataSet(entries, "");

        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Noun");
        labels.add("Pronoun");
        labels.add("Adjective");
        labels.add("Verb");
        labels.add("Adverb");
        labels.add("Preposition");
        labels.add("Conjunction");
        labels.add("Interjection");


        PieData data = new PieData(labels, dataset); // initialize Piedata
        mChart.setDescription("");  // set the description

        dataset.setColors(ColorTemplate.JOYFUL_COLORS); // set the color

        mChart.setNoDataText("Data Not Avail able");
        mChart.setDrawSliceText(false);

        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.animateY(5000);
        mChart.setData(data);

        mChart.setCenterText("WORDS");

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setDirection(Legend.LegendDirection.RIGHT_TO_LEFT);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        mChart.invalidate();

    }
}


