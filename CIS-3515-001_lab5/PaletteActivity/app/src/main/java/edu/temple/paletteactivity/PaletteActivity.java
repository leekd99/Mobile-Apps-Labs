package edu.temple.paletteactivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class PaletteActivity extends Activity {

    Spinner mySpinner;                              //declare Spinner
    boolean spinnerTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        //Array of colors to be displayed in Spinner
        //final String myColors[] = {"White", "Red", "Yellow", "Green", "Blue"};

        //fetch string array from string resources
        Resources res = this.getResources();
        final String myColors[] = res.getStringArray(R.array.color_palette); //used for display
        final String myMeta[] = res.getStringArray(R.array.color_meta);      //used for code

        final FragmentManager fm = getFragmentManager();

        //connect Spinner object to actual Spinner
        mySpinner = findViewById(R.id.mySpinner);

        CustomAdaptor myAdaptor = new CustomAdaptor(PaletteActivity.this, myColors, myMeta);

        mySpinner.setAdapter(myAdaptor);

        mySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinnerTouched = true;
                return false;
            }//end onTouch
        });//end setOnTouchListener

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //spinner has been touched
                if(spinnerTouched) {

                    CanvasFragment myFragment = CanvasFragment.newInstance(Color.parseColor(myMeta[position]));
                    fm.beginTransaction()
                            .add(R.id.canvas_fragment, myFragment)
                            .addToBackStack(null)
                            .commit();
                    /* start of code for canvasActivity
                    //set intent to launch new Activity
                    Intent startCanvas = new Intent(PaletteActivity.this, CanvasActivity.class);
                    //send selected color to activity
                    startCanvas.putExtra("colorSetting", myMeta[position]);
                    //start Activity
                    startActivity(startCanvas);
                    end of code for canvasActivity*/
                }//end if
            }//endItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }//end onNothingSelected
        });//end setOnItemSelected

    }///end onCreate
}//end PaletteActivity
