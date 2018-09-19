package edu.temple.signupform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends Activity {

    EditText takeName;
    EditText takeEMail;
    EditText takePW;
    EditText takeConfirmPW;
    Button saveButton;

    private Boolean isEmptyName = true;
    private Boolean isEmptyEmail = true;
    private Boolean isEmptyPW = true;
    private Boolean isEmptyConfirmPW = true;
    private Boolean doesNotMatchPW = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //map textfields by ID
        takeName = findViewById(R.id.takeName);
        takeEMail = findViewById(R.id.takeEMail);
        takePW = findViewById(R.id.takePW);
        takeConfirmPW = findViewById(R.id.takeConfirmPW);
        //map button by ID
        saveButton = findViewById(R.id.saveButton);

        //Button for saving
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check to see that text fields are filled
                if(!takeName.getText().toString().isEmpty()){
                    isEmptyName = false;
                    Log.d("FormActivity", "name checked!***************\n");
                }//end check for name
                if(!takeEMail.getText().toString().isEmpty()){
                    isEmptyEmail = false;
                    Log.d("FormActivity", "email checked!***************\n");
                }//end check for email
                if(!takePW.getText().toString().isEmpty()){
                    isEmptyPW = false;
                    Log.d("FormActivity", "password checked!***************\n");
                }//end check for password
                if(!takeConfirmPW.getText().toString().isEmpty()){
                    isEmptyConfirmPW = false;
                    Log.d("FormActivity", "confirmation checked!***************\n");
                }//end check for confirmation
                if(takePW.getText().toString().matches(takeConfirmPW.getText().toString())){
                    doesNotMatchPW = false;
                    Log.d("FormActivity", "match checked!***************\n");
                }//end match check

                //check to see if all prompts are filled and that password matches
                if(isEmptyName || isEmptyEmail || isEmptyPW || isEmptyConfirmPW){
                    //check to see that all text fields are filled
                    //if not Toast error message
                    Toast.makeText(FormActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    Log.d("FormActivity", "Toast made for form!***************\n");
                } else if(doesNotMatchPW){
                    //check to see that passwords match
                    //if not toast error message
                    Toast.makeText(FormActivity.this,"Please make sure that passwords match", Toast.LENGTH_SHORT).show();
                    Log.d("FormActivity", "Toast made for password!***************\n");
                } else {
                    //everything checks out launch the activity!
                    Intent welcomeUser = new Intent(FormActivity.this, Welcome.class);
                    Log.d("FormActivity", "about to start new activty!***************\n");
                    welcomeUser.putExtra("nameOfUser", takeName.getText().toString());
                    startActivity(welcomeUser);

                }//end check for correctly filled form

            }//end onClick
        });//end setOnClickListener

    }//end onCreate
}//end FormActivity
