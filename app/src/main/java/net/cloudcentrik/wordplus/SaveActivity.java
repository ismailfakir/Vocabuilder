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
import android.view.LayoutInflater;
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
    private TextInputLayout inputLayoutEmail;
    private String emailAddress="";
    private View dialogView;

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

        LayoutInflater inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialoge_email, null);

        txtEmailAddress=(TextView)dialogView.findViewById(R.id.txtDialogueEmailAddress);
        inputLayoutEmail=(TextInputLayout) dialogView.findViewById(R.id.dialogue_email_address);

        //txtEmailAddress.addTextChangedListener(new EmailTextWatcher(txtEmailAddress));


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

                /*//showEmailDialog();
                showEmailDialogueTest();

                if(validateEmail()){

                    SaveFileBackgroundTask saveFileBackgroundTask = new SaveFileBackgroundTask(SaveActivity.this, dbHelper.getAllWords(), "WordList"+WordplusUtils.getDateTime());
                    saveFileBackgroundTask.execute();

                    //SaveWordList saveWordListTask = new SaveWordList(SaveActivity.this, dbHelper.getAllWords(), "Processing for email");
                    try{
                        String createdFileName=saveFileBackgroundTask.get();
                        shareViaEmail(txtEmailAddress.getText().toString(),createdFileName);
                    }catch (Exception e){
                        Log.d("Send email Error",e.getMessage());
                    }

                    finish();

                }else{
                    showInvalidEmailDialog();
                }*/

                SaveFileBackgroundTask saveFileBackgroundTask = new SaveFileBackgroundTask(SaveActivity.this, dbHelper.getAllWords(), "WordList"+WordplusUtils.getDateTime());
                saveFileBackgroundTask.execute();
                try{
                    String createdFileName=saveFileBackgroundTask.get();
                    shareViaEmail(emailAddress,createdFileName);
                }catch (Exception e){
                    Log.d("Send email Error",e.getMessage());
                }
                finish();

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
            //intent.setData(Uri.parse(emailAddress));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

            startActivity(intent);
        } catch(Exception e)  {
            System.out.println("is exception raises during sending mail"+e);
        }

    }

    private void showEmailDialog(){
        Log.d("EMAIL DIALOGUE","INSIADE EMAIL DIALOGUE");
        AlertDialog.Builder builder = new AlertDialog.Builder(SaveActivity.this);
        builder.setTitle("Enter Your Email");
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emailAddress = txtEmailAddress.getText().toString();
                //dialog.dismiss();
                dialog.cancel();
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
        //showEmailDialog();
        //String email = txtEmailAddress.getText().toString().trim();
        String email = this.emailAddress;

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

    private void showInvalidEmailDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("invalid email");
        builder.setMessage("Please enter valid email address");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void showEmailDialogueTest(){
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.dialoge_email, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Tilte");
        alert.setMessage("Message");
        alert.setView(inflator);

        final EditText et1 = (EditText) inflator.findViewById(R.id.txtDialogueEmailAddress);

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                //String s1=et1.getText().toString();
                emailAddress=et1.getText().toString();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();
    }
}
