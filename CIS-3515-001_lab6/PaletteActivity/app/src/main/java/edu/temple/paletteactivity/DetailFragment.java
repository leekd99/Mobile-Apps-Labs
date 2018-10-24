package edu.temple.paletteactivity;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public static String COLOR_KEY = "color";
    int color;
    View view;

    public DetailFragment() {
        // Required empty public constructor
    }//end constructor

    public static DetailFragment newInstance(int color) {

        DetailFragment colorDisplay = new DetailFragment();
        Bundle displayColor = new Bundle();
        displayColor.putInt(colorDisplay.COLOR_KEY, color);
        colorDisplay.setArguments(displayColor);
        return colorDisplay;
    }//end newInstance

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.color = bundle.getInt(COLOR_KEY);
    }//end onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        view.setBackgroundColor(color);
        return view;
    }//end onCreateView

}//end DetailFragment
