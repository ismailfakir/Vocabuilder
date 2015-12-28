package net.cloudcentrik.vocabuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ismail on 2015-12-27.
 */
public class WordDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_SWEDISH = "swedish";
    public static final String KEY_ENGLISH = "english";
    public static final String KEY_EXAMPLE = "example";

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

    public long createWord(String swedish, String english,
                              String example) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SWEDISH, swedish);
        initialValues.put(KEY_ENGLISH, english);
        initialValues.put(KEY_EXAMPLE, example);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
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
                            KEY_ENGLISH, KEY_EXAMPLE},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,KEY_SWEDISH,
                            KEY_ENGLISH, KEY_EXAMPLE},
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
                        KEY_ENGLISH, KEY_EXAMPLE},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeWords() {

        createWord("Jag", "I", "Jag hetter Ismail");
        createWord("Du","you","Vad hetter du?");
        createWord("Köp","Bye","Jag vill köpa jul klap");

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
