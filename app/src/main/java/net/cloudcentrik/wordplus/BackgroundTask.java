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
public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    Activity activity;
    String savedFileName;
    String fileName;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private ArrayList<DictonaryWord> words;

    public BackgroundTask(Activity activity, ArrayList<DictonaryWord> words, String fileName) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        this.words = words;
        this.savedFileName = "File saved as ";
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

            //String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordplus/WordList.pdf";
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
            builder.setTitle("File Saved");
            builder.setMessage("File Saved as "+this.savedFileName);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                    activity.finish();
                }
            });
            //builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            //fName.concat(CreatePDF.createPdfWordList(words));
            this.savedFileName = CreatePDF.createPdfWordList(words, this.fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}