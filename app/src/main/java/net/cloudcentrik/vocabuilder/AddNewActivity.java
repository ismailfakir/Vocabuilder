package net.cloudcentrik.vocabuilder;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ismail on 2015-12-28.
 */
public class AddNewActivity extends AppCompatActivity {

    private WordDbAdapter dbHelper;
    private TextView txtSwedish;
    private TextView txtEnglish;
    private TextView txtSwedishExample;
    private TextView txtEnglishExample;
    private TextInputLayout inputLayoutSwedish;


    private String etten, partOfSpeach;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view from new_activity.xml
        setContentView(R.layout.activity_addnew);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_new_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        //
        etten = "en";
        partOfSpeach = "Noun";


        //part of speach spinner start
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        txtSwedish = (TextView) findViewById(R.id.txtSwedish);
        txtEnglish = (TextView) findViewById(R.id.txtEnglish);
        txtSwedishExample = (TextView) findViewById(R.id.txtSwedishExample);
        txtEnglishExample = (TextView) findViewById(R.id.txtEnglishExample);

        inputLayoutSwedish = (TextInputLayout) findViewById(R.id.input_layout_swedish);

        txtSwedish.addTextChangedListener(new MyTextWatcher(txtSwedish));

        Button addButton = (Button) findViewById(R.id.btnAddWord);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Log.d("DATABASE","Testing");
                insertWord();
            }
        });
    }

    private void insertWord() {
        if (!validateSwedish()) {
            return;
        }

        long r = dbHelper.createWord(txtSwedish.getText().toString(), txtEnglish.getText().toString(),
                txtSwedishExample.getText().toString(), txtEnglishExample.getText().toString(), partOfSpeach);
        if (r > 0) {

            Toast.makeText(AddNewActivity.this, "Word added", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(AddNewActivity.this, "Some thing go wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearText() {

        txtSwedish.setText("");
        txtEnglish.setText("");
        txtSwedishExample.setText("");
        txtEnglishExample.setText("");

    }

    /*
    private void updateUI(){
        swapCursor(dbHelper.getCursor());
        notifyDataSetChanged();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adnew_menu, menu);
        return true;
    }

    //input validation

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    private boolean validateSwedish() {
        if (txtSwedish.getText().toString().trim().isEmpty()) {
            inputLayoutSwedish.setError(getString(R.string.error_message));
            requestFocus(txtSwedish);
            return false;
        } else {
            inputLayoutSwedish.setErrorEnabled(false);
        }

        return true;
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            /*switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }*/
            //hideKeyboard();
            validateSwedish();
        }
    }

}
