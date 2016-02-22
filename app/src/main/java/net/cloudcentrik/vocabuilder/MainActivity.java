package net.cloudcentrik.vocabuilder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by ismail on 2015-12-27.
 */
public class MainActivity extends AppCompatActivity {

    TextView count;
    private WordDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher1);
        //myToolbar.setTitleTextAppearance(this, R.style.MyTitleTextApperance);
        setSupportActionBar(myToolbar);


        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        //Clean all data
        //dbHelper.deleteAllWords();
        //Add some data
        //dbHelper.insertSomeWords();

        //Generate ListView from SQLite Database
        displayListView();

        //CreatePDF.createPdfWordList(dbHelper.getAllWords());

        //Generate Spinner menue
        //displaySpinner();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menue, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final Intent myIntent;

        switch (item.getItemId()) {

            case R.id.add:
                // Start AddNewActivity.class
                myIntent = new Intent(MainActivity.this,
                        AddNewActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.test:
                // Start QuizActivity.class

                myIntent = new Intent(MainActivity.this,
                        QuizActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.stat:
                // Start AboutActivity.class
                myIntent = new Intent(MainActivity.this,
                        StatisticsActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.about:
                // Start AboutActivity.class
                myIntent = new Intent(MainActivity.this,
                        AboutActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.action_example:
                // Start ExampleActivity
                myIntent = new Intent(MainActivity.this,
                        ExampleActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.action_save:
                // Start ExampleActivity
               /* String fPath=CreatePDF.createPdfWordList(this.dbHelper.getAllWords());
                Toast.makeText(this,"Word List Created in "+fPath, Toast.LENGTH_SHORT).show();*/
                // Start ExampleActivity
                myIntent = new Intent(MainActivity.this,
                        SaveActivity.class);
                startActivity(myIntent);
                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllWords();

        // The desired columns to be bound
        String[] columns = new String[]{
                WordDbAdapter.KEY_SWEDISH,
                WordDbAdapter.KEY_ENGLISH,
                //WordDbAdapter.KEY_EXAMPLE,
                //WordDbAdapter.KEY_ETTEN,
                //WordDbAdapter.KEY_PARTOFSPEACH,
                //WordDbAdapter.KEY_DATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.textSV,
                R.id.textEN,
                //R.id.textEX,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.word_list,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String swedish =
                        cursor.getString(cursor.getColumnIndexOrThrow("swedish"));
                String english =
                        cursor.getString(cursor.getColumnIndexOrThrow("english"));
                String example =
                        cursor.getString(cursor.getColumnIndexOrThrow("example"));
                String ettEn =
                        cursor.getString(cursor.getColumnIndexOrThrow("etten"));
                String partOfSpeach =
                        cursor.getString(cursor.getColumnIndexOrThrow("part_of_speach"));
                String dateCreated =
                        cursor.getString(cursor.getColumnIndexOrThrow("created_at"));


                Word w = new Word(swedish, english, example, ettEn, partOfSpeach, dateCreated);

                Intent i = new Intent(MainActivity.this, WordActivity.class);

                i.putExtra("word", w);

                startActivity(i);


            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchWordsByName(constraint.toString());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.cloudcentrik.vocabuilder/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.cloudcentrik.vocabuilder/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();

        final Cursor c = dbHelper.fetchAllWords();
        dataAdapter.swapCursor(c);
        dataAdapter.notifyDataSetChanged();

    }
}
