package net.cloudcentrik.vocabuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ismail on 2015-12-27.
 */
public class WordDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_SWEDISH = "swedish";
    public static final String KEY_ENGLISH = "english";
    public static final String KEY_EXAMPLE = "example";
    public static final String KEY_ETTEN = "etten";
    public static final String KEY_PARTOFSPEACH = "part_of_speach";
    public static final String KEY_DATE = "created_at";

    private static final String TAG = "CountriesDbAdapter";
    private static final String DATABASE_NAME = "vocabuilder";
    private static final String SQLITE_TABLE = "words";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_SWEDISH + "," +
                    KEY_ENGLISH + "," +
                    KEY_EXAMPLE + "," +
                    KEY_ETTEN + "," +
                    KEY_PARTOFSPEACH + "," +
                    KEY_DATE + "," +
                    " UNIQUE (" + KEY_SWEDISH +"));";
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public WordDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public WordDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

   /* public long createWord(String swedish, String english,
                              String example) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SWEDISH, swedish);
        initialValues.put(KEY_ENGLISH, english);
        initialValues.put(KEY_EXAMPLE, example);
        initialValues.put(KEY_ETTEN, "en");
        initialValues.put(KEY_PARTOFSPEACH, "noun");
        initialValues.put(KEY_DATE, getDateTime());

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }*/

    public long createWord(String swedish, String english,
                           String example, String ettEn, String partOfSpeach) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SWEDISH, swedish);
        initialValues.put(KEY_ENGLISH, english);
        initialValues.put(KEY_EXAMPLE, example);
        initialValues.put(KEY_ETTEN, ettEn);
        initialValues.put(KEY_PARTOFSPEACH, partOfSpeach);
        initialValues.put(KEY_DATE, getDateTime());

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    //---deletes a particular word
    public boolean deleteWord(String word) {
        return mDb.delete(SQLITE_TABLE, KEY_SWEDISH + " ='" + word + "'", null) > 0;
        //return mDb.delete(SQLITE_TABLE, KEY_SWEDISH +" ='Jag'", null) > 0;
    }

    // Updating single word
    public boolean updateWord(Word w) {

        ContentValues values = new ContentValues();
        values.put(KEY_ENGLISH, w.getEnglish());
        values.put(KEY_EXAMPLE, w.getExample());
        values.put(KEY_ETTEN, w.getEtten());
        values.put(KEY_PARTOFSPEACH, w.getPartOfSpeach());
        values.put(KEY_DATE, getDateTime());

        // updating row
        return mDb.update(SQLITE_TABLE, values, KEY_SWEDISH + " = ?",
                new String[]{String.valueOf(w.getSwedish())}) > 0;
    }

    public long countWords() {
        return DatabaseUtils.queryNumEntries(mDb, SQLITE_TABLE);
    }

    public long countPartofSpeach(String pos) {
        SQLiteStatement s = mDb.compileStatement("select count(*) from words where part_of_speach='" + pos + "'; ");
        long count = s.simpleQueryForLong();
        return count;
    }

    public boolean deleteAllWords() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchWordsByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,KEY_SWEDISH,
                            KEY_ENGLISH, KEY_EXAMPLE, KEY_ETTEN, KEY_PARTOFSPEACH, KEY_DATE},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,KEY_SWEDISH,
                            KEY_ENGLISH, KEY_EXAMPLE, KEY_ETTEN, KEY_PARTOFSPEACH, KEY_DATE},
                    KEY_SWEDISH + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllWords() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,KEY_SWEDISH,
                        KEY_ENGLISH, KEY_EXAMPLE, KEY_ETTEN, KEY_PARTOFSPEACH, KEY_DATE},
                null, null, null, null, KEY_SWEDISH + " ASC");

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeWords() {

        createWord("Jag", "I", "Jag hetter Ismail", "en", "pronoun");
        createWord("Du", "you", "Vad hetter du?", "en", "pronoun");
        createWord("Köp", "Bye", "Jag vill köpa jul klap", "en", "pronoun");

    }

    public ArrayList<Word> getAllWords() {

        ArrayList<Word> list = new ArrayList<Word>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + SQLITE_TABLE + " ORDER BY " + KEY_SWEDISH + " COLLATE NOCASE ASC";

        try {

            Cursor cursor = mDb.rawQuery(selectQuery, null);

            try {

                // looping through all rows and adding to list
                if (cursor.getCount() != 0) {

                    cursor.moveToFirst();
                    do {
                        //Word obj = new Word(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                        Word obj = new Word();
                        //get all column
                        obj.setSwedish(cursor.getString(cursor.getColumnIndex("swedish")));
                        obj.setEnglish(cursor.getString(cursor.getColumnIndex("english")));
                        obj.setExample(cursor.getString(cursor.getColumnIndex("example")));
                        obj.setEtten(cursor.getString(cursor.getColumnIndex("etten")));
                        obj.setPartOfSpeach(cursor.getString(cursor.getColumnIndex("part_of_speach")));
                        obj.setCreateDate(cursor.getString(cursor.getColumnIndex("created_at")));

                        //you could add additional columns here..

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            //try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

}
