package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismail on 2016-01-03.
 */
public class QuizActivity extends AppCompatActivity {
    private WordDbAdapter dbHelper;
    private ArrayList<Word> allWords;
    private TextView question;

    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.quiz_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        //showToast("One");
                        break;
                    case 1:
                        //showToast("Two");
                        break;
                    case 2:
                        //showToast("Three");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*dbHelper = new WordDbAdapter(this);
        dbHelper.open();

        question = (TextView) findViewById(R.id.txtQuestion);

        allWords = new ArrayList<Word>();

        getAllWords();
        createAQuiz();




        Button submitButton = (Button) findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createAQuiz();
            }
        });*/
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menue, menu);
        return true;
    }

    private void getAllWords() {
        this.allWords = dbHelper.getAllWords();
    }

    private void createAQuiz() {
        if (allWords != null) {
            //int n = generateRandonInRange(0, (int) dbHelper.countWords());
            Collections.shuffle(allWords);
            Word w = allWords.get(0);
            question.setText("What is the meaning of '" + w.getSwedish() + "' ?");

            option1 = (RadioButton) findViewById(R.id.radio0);
            option2 = (RadioButton) findViewById(R.id.radio1);
            option3 = (RadioButton) findViewById(R.id.radio2);
            option4 = (RadioButton) findViewById(R.id.radio3);

            int option[] = VocabuilderUtils.RandomizeArray(0, 3);

            option1.setText(allWords.get(option[0]).getEnglish());
            option2.setText(allWords.get(option[1]).getEnglish());
            option3.setText(allWords.get(option[2]).getEnglish());
            option4.setText(allWords.get(option[3]).getEnglish());
        }

        *//*Cursor c=dbHelper.fetchAllWords();
        c.moveToFirst();
        String s=c.getString(c.getColumnIndex("swedish"));

        question.setText("What is the meaning of " + s + " ?");*//*


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Collections.shuffle(allWords);

    }

    public int generateRandonInRange(int min, int max) {
        //int min = 0;
        //int max = (int)dbHelper.countWords();

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        return randomNumber;
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }*/

    //tab
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MeaningFragment(), "Meaning Swedish");
        adapter.addFragment(new EnglishSwedishFragment(), "Meaning English");
        adapter.addFragment(new PartOfSpeachFragment(), "PartOfSpeech");
        viewPager.setAdapter(adapter);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
