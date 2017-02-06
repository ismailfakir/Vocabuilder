package net.cloudcentrik.wordplus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import java.util.ArrayList;

/**
 * Created by ismail on 03/02/17.
 */

public class SaveWordList extends AsyncTask<String,Void, String> {

    private Activity activity;
    private ArrayList<DictonaryWord> words;
    private String message=null;
    private ProgressDialog dialog;
    private String fileName=null;
    public SaveWordList(Activity activity, ArrayList<DictonaryWord> words, String message){
        this.activity = activity;
        this.message=message;
        this.dialog = new ProgressDialog(activity);
        this.words = words;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage(this.message);
        dialog.show();

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            fileName = CreatePDF.createPdfWordList(words,"WordList-"+WordplusUtils.getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    @Override
    protected void onPostExecute(String result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
