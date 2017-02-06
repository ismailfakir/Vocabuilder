package net.cloudcentrik.wordplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;

import java.util.ArrayList;

/**
 * Created by ismail on 2016-02-20.
 */
public class SaveFileBackgroundTask extends AsyncTask<Void, Void, String> {
    Activity activity;
    String savedFileName;
    String fileName;
    private ProgressDialog dialog;
    private ArrayList<DictonaryWord> words;

    public SaveFileBackgroundTask(Activity activity, ArrayList<DictonaryWord> words, String fileName) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        this.words = words;
        this.savedFileName = "";
        this.fileName = fileName;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Processing for Email, please wait.");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            this.savedFileName = CreatePDF.createPdfWordList(words, this.fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.savedFileName;
    }

}