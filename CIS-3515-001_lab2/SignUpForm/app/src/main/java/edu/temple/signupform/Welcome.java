package edu.temple.signupform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends Activity {

    TextView greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        greeting = findViewById(R.id.myGreeting);

        Intent myIntent = getIntent();

        String name = myIntent.getStringExtra("nameOfUser");

        greeting.setTextSize(24);

        greeting.setText("Welcome \n\n" + "\t\t\t\t" + name + "\n\nto the SignUpForm App!");


    }//end onCreate
}//end Welcome
