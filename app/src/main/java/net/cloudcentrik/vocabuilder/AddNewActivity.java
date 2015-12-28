package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.app.Activity;
/**
 * Created by ismail on 2015-12-28.
 */
public class AddNewActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.addnew_activity);
    }
}
