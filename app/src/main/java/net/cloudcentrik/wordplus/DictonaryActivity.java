package net.cloudcentrik.wordplus;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
        words = db.getAllDictionaryWords();

        adapter = new SimpleCursorAdapter(
                this, R.layout.dictonary_list_backup,
                words,
                new String[] {"English","Swedish"},
                new int[] {R.id.textEN,R.id.textSV},
                0);

        listView .setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String swedish =
                        cursor.getString(cursor.getColumnIndexOrThrow("Swedish"));
                String english =
                        cursor.getString(cursor.getColumnIndexOrThrow("English"));
                String exampleSwedish =
                        cursor.getString(cursor.getColumnIndexOrThrow("SwedishExample"));
                String exampleEnglish =
                        cursor.getString(cursor.getColumnIndexOrThrow("EnglishExample"));
                String partOfSpeech =
                        cursor.getString(cursor.getColumnIndexOrThrow("PartOfSpeech"));

                DictionaryWord w = new DictionaryWord(swedish, english,exampleSwedish, exampleEnglish,partOfSpeech, DictionaryWord.getCurrentDate());

                Intent i = new Intent(DictonaryActivity.this, DictionaryWordActivity.class);

                i.putExtra("DictionaryWord", w);

                startActivity(i);

            }
        });


        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.getWordByEntry(constraint.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dictonary_menu, menu);

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
