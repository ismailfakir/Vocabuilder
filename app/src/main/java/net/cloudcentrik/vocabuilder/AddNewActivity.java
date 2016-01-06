package net.cloudcentrik.vocabuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by ismail on 2015-12-28.
 */
public class AddNewActivity extends Activity{

    private WordDbAdapter dbHelper;
    private TextView txtSwedish;
    private TextView txtEnglish;
    private TextView txtExample;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_addnew);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        txtSwedish = (TextView) findViewById(R.id.txtSwedish);
        txtEnglish = (TextView) findViewById(R.id.txtEnglish);
        txtExample = (TextView) findViewById(R.id.txtExample);

        ImageButton closeButton = (ImageButton) findViewById(R.id.btnAddBack);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

        BootstrapButton clearButton = (BootstrapButton) findViewById(R.id.btnAddClear);
        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clearText();
            }
        });

        Button addButton = (Button) findViewById(R.id.btnAddWord);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                insertWord();
            }
        });
    }

    private void insertWord() {

        long r = dbHelper.createWord(txtSwedish.getText().toString(), txtEnglish.getText().toString(), txtExample.getText().toString());
        if (r > 0) {

            Toast.makeText(AddNewActivity.this, "Word added", Toast.LENGTH_SHORT).show();
            clearText();

        } else {
            Toast.makeText(AddNewActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearText() {

        txtSwedish.setText("");
        txtEnglish.setText("");
        txtExample.setText("");

    }

    /*
    private void updateUI(){
        swapCursor(dbHelper.getCursor());
        notifyDataSetChanged();
    }*/
}
