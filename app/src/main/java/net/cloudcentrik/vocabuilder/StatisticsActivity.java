package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by ismail on 2016-01-02.
 */
public class StatisticsActivity extends AppCompatActivity {
    private WordDbAdapter dbHelper;
    private TextView txtNoOfWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        txtNoOfWord = (TextView) findViewById(R.id.noOfWord);
        txtNoOfWord.setText("" + dbHelper.countWords());

        ImageButton closeButton = (ImageButton) findViewById(R.id.btnStatBack);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }
}
