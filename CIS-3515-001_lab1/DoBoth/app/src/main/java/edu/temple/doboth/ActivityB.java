package edu.temple.doboth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityB extends Activity {

    Button startActivityA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        startActivityA = findViewById(R.id.startActivityA);

        startActivityA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start_Activity_A_Intent = new Intent(ActivityB.this, ActivityA.class);
                startActivity(start_Activity_A_Intent);

            }//end onCLick
        });//end setOnClickListener

    }//end onCreate
}//end ActivityB
