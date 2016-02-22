package net.cloudcentrik.vocabuilder;

/**
 * Created by ismail on 2016-02-19.
 */

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class FileOperations {

    private static final String TAG = "MEDIA";

    public FileOperations() {

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static String writeUsingXMLSerializer(Word word) throws Exception {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);
        // start DOCUMENT
        xmlSerializer.startDocument("UTF-8", true);
        // open tag: <record>
        xmlSerializer.startTag("", "word");
        // open tag: <study>
        xmlSerializer.startTag("", "Swedish");
        /*xmlSerializer.attribute("", Study.ID, String.valueOf(study.mId));

        // open tag: <topic>
        xmlSerializer.startTag("", Study.TOPIC);
        xmlSerializer.text(study.mTopic);
        // close tag: </topic>
        xmlSerializer.endTag("", Study.TOPIC);

        // open tag: <content>
        xmlSerializer.startTag("", Study.CONTENT);
        xmlSerializer.text(study.mContent);
        // close tag: </content>
        xmlSerializer.endTag("", Study.CONTENT);

        // open tag: <author>
        xmlSerializer.startTag("", Study.AUTHOR);
        xmlSerializer.text(study.mAuthor);
        // close tag: </author>
        xmlSerializer.endTag("", Study.AUTHOR);

        // open tag: <date>
        xmlSerializer.startTag("", Study.DATE);
        xmlSerializer.text(study.mDate);
        // close tag: </date>
        xmlSerializer.endTag("", Study.DATE);

        // close tag: </study>
        xmlSerializer.endTag("", Study.STUDY);*/
        // close tag: </record>
        xmlSerializer.endTag("", "word");

        // end DOCUMENT
        xmlSerializer.endDocument();

        return writer.toString();
    }

    public Boolean write(String fname, String fcontent) {
        try {

            String fpath = "/sdcard/" + fname + ".txt";

            File file = new File(fpath);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();

            Log.d("Suceess", "Sucess");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String read(String fname) {

        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/" + fname + ".txt";

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line + "n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;

    }

    private void writeToSDFile(String fileName) {
        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-
        // storage.html#filesExternal
        File root = android.os.Environment.getExternalStorageDirectory();
        //tv.append("\nExternal file system root: " + root);
        // See
        // http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "myData.txt");
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("Hi , How are you");
            pw.println("Hello");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you"
                    + " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //tv.append("\n\nFile written to " + file);
    }

    /**
     * Method to read in a text file placed in the res/raw directory of the
     * application. The method reads in all lines of the file sequentially.
     */
    private void readRaw(String fname) {
        //tv.append("\nData read from res/raw/textfile.txt:");
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192); // 2nd arg is buffer
        // size
        // More efficient (less readable) implementation of above is the
        // composite expression
        /*
         * BufferedReader br = new BufferedReader(new InputStreamReader(
         * this.getResources().openRawResource(R.raw.textfile)), 8192);
         */
        try {
            String test;
            while (true) {
                test = br.readLine();
                // readLine() returns null if no more lines in the file
                if (test == null) break;
                //tv.append("\n" + "    " + test);
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //tv.append("\n\nThat is all");
    }
}