package edu.temple.paletteactivity;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MasterFragment extends Fragment {

    Spinner mySpinner;
    CustomAdaptor myAdaptor;
    Context parent;
    boolean spinnerTouched;
    selectColor mCallBack;

    public MasterFragment() {
        // Required empty public constructor
    }//end constructor

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = context;

        try {
            mCallBack = (selectColor)context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implement selectColor");
        }

    }//end onAttach

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get String array from resources file and set them to arrays
        Resources res = parent.getResources();
        String myColors[] = res.getStringArray(R.array.color_palette);
        final String myMeta[] = res.getStringArray(R.array.color_meta);

        View v = inflater.inflate(R.layout.fragment_master, container, false);

        myAdaptor = new CustomAdaptor(parent, myColors, myMeta);

        mySpinner = v.findViewById(R.id.mySpinner);
        mySpinner.setAdapter(myAdaptor);

        mySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinnerTouched = true;
                return false;
            }//end onTOuch
        });//end setOnTouchListener

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerTouched) {
                    spinnerTouched = false;
                    mCallBack.onColorSelected(myMeta[position]);
                }//end check for activity
            }//end onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //DO NOTHING FOR NOW
            }//end on NothingSelected
        });

        // Inflate the layout for this fragment
        return v;
    }//end onCreateView

    public interface selectColor{
        public void onColorSelected(String selectedColor);
    }//end selectColor

}//end MasterFragment
