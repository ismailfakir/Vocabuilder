package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

/**
 * Created by ismail on 2016-02-11.
 */
public class EttEnFragment extends Fragment {

    ToggleButton tgbutton;

    public EttEnFragment() {
        // Required empty public constructor
    }

    public static EttEnFragment newInstance() {
        return new EttEnFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*tgbutton = (ToggleButton)  getActivity().findViewById(R.id.toggleButton1);
        tgbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tgbutton.isChecked()) {

                    tgbutton.setText("ON");
                } else {

                    tgbutton.setText("OFF");
                }
            }
        });*/

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_etten, container, false);


    }
}
