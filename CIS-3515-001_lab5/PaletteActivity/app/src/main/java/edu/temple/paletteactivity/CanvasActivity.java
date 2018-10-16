package edu.temple.paletteactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

public class CanvasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        setTitle("CanvasActivity");

        //get intetn from Activity that launched this
        Intent intent = getIntent();

        //get the color sent form other activity
        String colorSet = intent.getStringExtra("colorSetting");

        //set background color with color received from other activity
        ((ConstraintLayout)findViewById(R.id.Canvas)).setBackgroundColor(Color.parseColor(colorSet));

    }//end onCreate
}//end CanvasActivity
