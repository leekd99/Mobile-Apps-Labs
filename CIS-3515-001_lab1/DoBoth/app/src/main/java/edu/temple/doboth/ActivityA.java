package edu.temple.doboth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ActivityA extends Activity {

    Button startActivityB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        startActivityB = findViewById(R.id.startActivityB);

        startActivityB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start_Activity_B_Intent = new Intent(ActivityA.this, ActivityB.class);
                startActivity(start_Activity_B_Intent);

            }//end onCLick
        });//end setOnClickListener


    }//end onCreate
}//end ActivityA
