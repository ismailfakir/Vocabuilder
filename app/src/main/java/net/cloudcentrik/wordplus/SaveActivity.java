package net.cloudcentrik.wordplus;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SaveActivity extends AppCompatActivity {

    private WordDbAdapter dbHelper;
    private TextView txtEmailAddress;
    private EditText txtFileName;
    private TextView txtHeading1,txtHeading2;
    private String emailAddress="";
    public String fileName="";
    private TextInputLayout inputLayoutEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.save_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        txtEmailAddress=(TextView)findViewById(R.id.txtEmailAddress);
        inputLayoutEmail=(TextInputLayout) findViewById(R.id.input_layout_email_address);

        txtEmailAddress.addTextChangedListener(new EmailTextWatcher(txtEmailAddress));


        dbHelper = new WordDbAdapter(this);
        dbHelper.open();


        final Button button = (Button) findViewById(R.id.btnSaveWordList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                BackgroundTask bt = new BackgroundTask(SaveActivity.this, dbHelper.getAllWords(), "WordList"+WordplusUtils.getDateTime());
                bt.execute();

            }
        });

        final Button buttonEmail = (Button) findViewById(R.id.btnEmailWordList);
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(validateEmail()){

                    SaveWordList saveWordListTask = new SaveWordList(SaveActivity.this, dbHelper.getAllWords(), "Processing for email");
                    try{
                        saveWordListTask.execute();
                        String createdFileName=saveWordListTask.get();
                        shareViaEmail(txtEmailAddress.getText().toString(),createdFileName);
                    }catch (Exception e){
                        Log.d("Send email Error",e.getMessage());
                    }

                    finish();

                }

            }
        });

    }

    @Override
    public void onStop() {
        dbHelper.close();
        super.onStop();
    }

    private void shareViaEmail(String emailAddress,String fileName) {
        try {

            //showEmailDialog();

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            String message="Please find your word list attachment with this email from Word Plus";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Word list from Word Plus "+WordplusUtils.getDateTime());
            //String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordplus/WordList.pdf";
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(fileName)));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setData(Uri.parse(emailAddress));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

            startActivity(intent);
        } catch(Exception e)  {
            System.out.println("is exception raises during sending mail"+e);
        }

    }

    private void showEmailDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send To");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emailAddress = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emailAddress="";
                dialog.cancel();
            }
        });

        builder.show();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail() {
        String email = txtEmailAddress.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Invalid email address");
            requestFocus(txtEmailAddress);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private class EmailTextWatcher implements TextWatcher {

        private View view;

        private EmailTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            validateEmail();
        }
    }
}
