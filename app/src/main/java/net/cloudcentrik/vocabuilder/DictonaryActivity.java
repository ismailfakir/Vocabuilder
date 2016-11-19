package net.cloudcentrik.vocabuilder;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FilterQueryProvider;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.lang.reflect.Method;

public class DictonaryActivity extends AppCompatActivity {

    private WebView myWebView;
    private Cursor words;
    private DictonaryDatabase db;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictonary);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.dictonary_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        ListView listView = (ListView) findViewById(R.id.dictonary_wordlist);

        db = new DictonaryDatabase(this);
        words = db.getEmployees(); // you would not typically call this on the main thread

        /*ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.dictonary_list,
                words,
                new String[] {"English","Swedish","PartOfSpeech","SwedishExample","EnglishExample"},
                new int[] {R.id.textEN,R.id.textSV,R.id.textPartOfSpeach,R.id.textEX,R.id.texEnglishExample});*/

        adapter = new SimpleCursorAdapter(
                this, R.layout.dictonary_list_backup,
                words,
                new String[] {"English"},
                new int[] {R.id.textEN},
                0);

        listView .setAdapter(adapter);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.getWordByEntry(constraint.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menue, menu);

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //return true;

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Here");
        searchView.setSubmitButtonEnabled(true);

        //setupSearchView(searchView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString());
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent myIntent;

        switch (item.getItemId()) {

            /*case R.id.add:
                // Start AddNewActivity.class
                myIntent = new Intent(MainActivity.this,
                        AddNewActivity.class);
                startActivity(myIntent);
                return (true);*/
            case R.id.action_test:
                // Start QuizActivity.class

                myIntent = new Intent(DictonaryActivity.this,
                        QuizActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.action_stat:
                // Start AboutActivity.class
                myIntent = new Intent(DictonaryActivity.this,
                        StatisticsActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.action_about:
                // Start AboutActivity.class
                myIntent = new Intent(DictonaryActivity.this,
                        AboutActivity.class);
                startActivity(myIntent);
                return (true);

            case R.id.action_dictonary:
                // Start ExampleActivity
                myIntent = new Intent(DictonaryActivity.this,
                        DictonaryActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.action_save:
                myIntent = new Intent(DictonaryActivity.this,
                        SaveActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.action_add:
                myIntent = new Intent(DictonaryActivity.this,
                        AddNewActivity.class);
                startActivity(myIntent);
                return (true);

            case R.id.action_search:

                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }

    //Show icon in menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack())
            myWebView.goBack();
        else
            super.onBackPressed();
    }

}
