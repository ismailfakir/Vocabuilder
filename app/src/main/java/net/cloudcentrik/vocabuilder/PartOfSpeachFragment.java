package net.cloudcentrik.vocabuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PartOfSpeachFragment extends Fragment {

    public PartOfSpeachFragment() {
        // Required empty public constructor
    }


    public static PartOfSpeachFragment newInstance(String param1, String param2) {

        return new PartOfSpeachFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_part_of_speach, container, false);
    }

}