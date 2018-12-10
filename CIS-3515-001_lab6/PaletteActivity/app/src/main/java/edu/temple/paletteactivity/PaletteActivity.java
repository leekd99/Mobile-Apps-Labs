package edu.temple.paletteactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class PaletteActivity extends AppCompatActivity implements MasterFragment.selectColor {

    FragmentManager fm;
    DetailFragment df;
    MasterFragment colorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        fm = getSupportFragmentManager();

        df = new DetailFragment();

        colorSpinner = new MasterFragment();

        fm.beginTransaction()
                .add(R.id.canvas_fragment, colorSpinner)
                .addToBackStack(null)
                .commit();

    }//end onCreate

    @Override
    public void onColorSelected(String selectedColor) {

        //Create new detail fragment
        DetailFragment newFragment = DetailFragment.newInstance(Color.parseColor(selectedColor));

        if(findViewById(R.id.canvas_fragment_mkII) != null){
            fm.beginTransaction()
                    .replace(R.id.canvas_fragment_mkII, newFragment)
                    .addToBackStack(null)
                    .commit();
        }else{
            fm.beginTransaction()
                    .replace(R.id.canvas_fragment, newFragment)
                    .addToBackStack(null)
                    .commit();
        }//end if else

    }//end onColorSelected
}//end PaletteActivity
