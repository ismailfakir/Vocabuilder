package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditWordActivity extends AppCompatActivity {
    private TextView ts;
    private TextView te;
    private TextView tx;

    private String partOfSpeach;
    private Spinner spinner;

    private WordDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.edit_word_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        ts = (TextView) findViewById(R.id.txtEditSwedish);
        ts.setEnabled(false);
        te = (TextView) findViewById(R.id.txtEditEnglish);
        tx = (TextView) findViewById(R.id.txtEditExample);

        partOfSpeach = "Noun";
        //part of speach spinner start
        spinner = (Spinner) findViewById(R.id.spinnerEditWord);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.part_of_speech, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                partOfSpeach = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(3);
        //spinner end

        createWordView();

        Button editButton = (Button) findViewById(R.id.btnEditWord);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Word w = new Word(ts.getText().toString(), te.getText().toString(), tx.getText().toString(), "EN", partOfSpeach, "new Date");

                //Word w=new Word("TEST","English","Example","ETT","verb","date");

                editWord(w);
            }
        });

    }

    private void createWordView() {

        Word word = getIntent().getParcelableExtra("editedword");
        ts.setText(word.getSwedish());
        te.setText(word.getEnglish());
        tx.setText(word.getExample());
        //tpartOfSpeach.setText(word.getPartOfSpeach());
        //tDate.setText(word.getCreateDate());
        partOfSpeach = word.getPartOfSpeach();
        switch (partOfSpeach) {
            case "Noun":
                spinner.setSelection(0);
                break;
            case "ProNoun":
                spinner.setSelection(1);
                break;
            case "Adjective":
                spinner.setSelection(2);
                break;
            case "Verb":
                spinner.setSelection(3);
                break;
            case "Adverb":
                spinner.setSelection(4);
                break;
            case "Preposition":
                spinner.setSelection(5);
                break;
            case "Conjunction":
                spinner.setSelection(6);
                break;
            case "Interjection":
                spinner.setSelection(7);
                break;
            default:
                spinner.setSelection(7);
                break;
        }

    }

    public void editWord(Word w) {

        //Toast.makeText(EditWordActivity.this, "This is a test", Toast.LENGTH_SHORT).show();

        if (dbHelper.updateWord(w)) {
            Toast.makeText(EditWordActivity.this, "Word Updated", Toast.LENGTH_SHORT).show();
           /* Word word=new Word(ts.getText().toString(),te.getText().toString(),tx.getText().toString(),"EN",partOfSpeach,tDate.getText().toString());
            Intent i = new Intent(EditWordActivity.this, WordActivity.class);

            i.putExtra("word", word);

            startActivity(i);
            finish();*/
            finish();


        } else {
            Toast.makeText(EditWordActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
