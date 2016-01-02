package net.cloudcentrik.vocabuilder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ismail on 2015-12-27.
 */
public class MainActivity extends Activity {

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

        dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllWords();
        //Add some data
        dbHelper.insertSomeWords();

        //Generate ListView from SQLite Database
        displayListView();

        //Generate Spinner menue
        displaySpinner();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void displaySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.menu_spinner);
        String[] items = new String[]{"Menu", "Add new", "Random Test", "Statistics", "About"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

               /* Toast.makeText(getApplicationContext(),
                        (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();*/

                final Intent myIntent;
                switch (position) {
                    case 1:

                        // Start AddNewActivity.class
                        myIntent = new Intent(MainActivity.this,
                                AddNewActivity.class);
                        startActivity(myIntent);
                        break;
                    case 3:

                        // Start AboutActivity.class
                        myIntent = new Intent(MainActivity.this,
                                StatisticsActivity.class);
                        startActivity(myIntent);
                        break;
                    case 4:

                        // Start AboutActivity.class
                        myIntent = new Intent(MainActivity.this,
                                AboutActivity.class);
                        startActivity(myIntent);
                        break;
                    case 2:

                        Toast.makeText(getApplicationContext(),
                                (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                parent.setSelection(0);
            }
        });

    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllWords();

        // The desired columns to be bound
        String[] columns = new String[]{
                WordDbAdapter.KEY_SWEDISH,
                WordDbAdapter.KEY_ENGLISH,
                WordDbAdapter.KEY_EXAMPLE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.swedish,
                R.id.english,
                R.id.example,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list,
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
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow("swedish"));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

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
}
