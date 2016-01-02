package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by ismail on 2016-01-02.
 */
public class StatisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        BootstrapButton closeButton = (BootstrapButton) findViewById(R.id.btnGoBack);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
