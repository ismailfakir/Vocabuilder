package net.cloudcentrik.wordplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by ismail on 2016-02-20.
 */
public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    Activity activity;
    String fName;
    String fileName;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private ArrayList<DictonaryWord> words;

    public BackgroundTask(Activity activity, ArrayList<DictonaryWord> words, String fileName) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        this.words = words;
        fName = "File saved as ";
        this.fileName = fileName;


    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Saving to file, please wait.");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
            //TextView t = (TextView) activity.findViewById(R.id.txtStatus);
            //t.setText("File Saved in " + fName);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            //fName.concat(CreatePDF.createPdfWordList(words));
            fName = CreatePDF.createPdfWordList(words, this.fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}