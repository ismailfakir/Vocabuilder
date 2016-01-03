package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ismail on 2016-01-03.
 */
public class WordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        createWordView();
    }

    private void createWordView() {

        Word word = getIntent().getParcelableExtra("word");
        TextView ts = (TextView) findViewById(R.id.textSV);
        TextView te = (TextView) findViewById(R.id.textEN);
        TextView tx = (TextView) findViewById(R.id.textEX);

        ts.setText(word.getSwedish());
        te.setText(word.getEnglish());
        tx.setText(word.getExample());

    }
}
