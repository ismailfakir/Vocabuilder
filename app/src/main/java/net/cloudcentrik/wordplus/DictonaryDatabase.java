package net.cloudcentrik.wordplus;

/**
 * Created by ismail on 2016-11-15.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DictonaryDatabase extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "dictonary.db";
    private static final int DATABASE_VERSION = 1;

    public DictonaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAllDictionaryWords() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"0 _id","Swedish","English","SwedishExample","EnglishExample","PartOfSpeech"};
        String sqlTables = "WORDS";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }

    public Cursor getWordByEntry(String str) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        Cursor c=null;

        String [] sqlSelect = {"0 _id","Swedish","English","SwedishExample","EnglishExample","PartOfSpeech"};
        String sqlTables = "WORDS";

        if(str==null ||  str.length () == 0){

            qb.setTables(sqlTables);
            c = qb.query(db, sqlSelect, null, null,
                    null, null, null);

        }else {

            qb.appendWhere("English like '"+str+"%'");

            qb.setTables(sqlTables);
            c = qb.query(db, sqlSelect, null, null,
                    null, null, null);

        }

        if (c != null) {
            c.moveToFirst();
        }

        return c;

    }

}
