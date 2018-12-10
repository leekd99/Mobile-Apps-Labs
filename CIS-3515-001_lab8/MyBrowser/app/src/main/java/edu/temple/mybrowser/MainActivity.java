package edu.temple.mybrowser;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WebFragment.loadNewUrl {

    Button goButton;
    TextView urlTextView;
    ViewPager myPager;
    ArrayList<WebFragment> tabs = new ArrayList<>();
    int maxFrag = 5;
    int numFrag = 0;
    String[] loadURL = new String[maxFrag];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPager = findViewById(R.id.webBrowse);
        urlTextView = findViewById(R.id.urlEditText);
        myPager.setAdapter(fspa);

        Intent intent = getIntent();
        Uri url;

        if( (url = intent.getData()) != null ){

            if(tabs.isEmpty()){

                WebFragment wf = new WebFragment();

                tabs.add(wf);
                numFrag++;
                fspa.notifyDataSetChanged();
                myPager.arrowScroll(View.FOCUS_RIGHT);
                myPager.arrowScroll(View.FOCUS_RIGHT);
                urlTextView.setText(intent.getData().toString());

                //Your implementation for loading url to web fragment
                tabs.get(myPager.getCurrentItem()).givenURL = intent.getData().toString();

                Log.d("#################", intent.getData().toString());

            } else {

                tabs.get(myPager.getCurrentItem()).loadNewWebView(intent.getData().toString());
                //tabs.get(myPager.getCurrentItem()).givenURL = intent.getData().toString();

            }//end else if

            //newUrlEntered(intent.getData().toString());

        }//end intent check

        findViewById(R.id.launchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tabs.size() > 0) {
                    Log.d("###################", "WORKED!!!!!!!!!!!!!!!");

                    String newUrl = urlTextView.getText().toString();
                    loadURL[myPager.getCurrentItem()] = newUrl;

                    newUrlEntered(newUrl);
                }//end
            }
        });//end button

    }//end onCreate

    public void updateUrl(String url){

        urlTextView.setText(url);

    }//end

    public void newUrlEntered(String url){

        tabs.get(myPager.getCurrentItem()).loadNewWebView(url);

    }//end

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_control, menu);
        return super.onCreateOptionsMenu(menu);
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.newWebView){

            if(numFrag < maxFrag) {
                tabs.add(new WebFragment());
                numFrag++;
                fspa.notifyDataSetChanged();
                urlTextView.setText("");
                myPager.arrowScroll(View.FOCUS_RIGHT);
                myPager.arrowScroll(View.FOCUS_RIGHT);
            }//edn check for max frag

        } else if(id == R.id.leftFragment){

            myPager.arrowScroll(View.FOCUS_LEFT);
            tabs.get(myPager.getCurrentItem()).loadNewWebView(loadURL[myPager.getCurrentItem()]);
            updateUrl(tabs.get(myPager.getCurrentItem()).myWebView.getUrl());

        } else if(id == R.id.rightFragment){

            myPager.arrowScroll(View.FOCUS_RIGHT);
            tabs.get(myPager.getCurrentItem()).loadNewWebView(loadURL[myPager.getCurrentItem()]);
            updateUrl(tabs.get(myPager.getCurrentItem()).myWebView.getUrl());

        }//end else if

        return super.onContextItemSelected(item);
    }//end onOptions selected

    FragmentStatePagerAdapter fspa = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {

            return tabs.get(position);

        }//end getItem

        @Override
        public int getCount() {

            return tabs.size();

        }//end getCount

    };//end fspa

}//end MainActivity
