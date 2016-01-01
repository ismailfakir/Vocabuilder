package net.cloudcentrik.vocabuilder;

import android.app.Activity;
import android.os.Bundle;
/**
 * Created by ismail on 2015-12-28.
 */
public class AddNewActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_addnew);
    }
}
