package edu.temple.mybrowser;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    Button backButton;
    Button forwardButton;
    WebView myWebView;
    String givenURL;// = "https://temple.edu";
    Context parent;


    public WebFragment() {
        // Required empty public constructor
    }//end WebFragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web, container, false);

        myWebView = v.findViewById(R.id.fragmentWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);

        //loadNewWebView(givenURL);

        if(givenURL != null){

            myWebView.loadUrl(givenURL);

        }//end


        v.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(myWebView.canGoBack()){

                    myWebView.goBack();
                    ((MainActivity)getActivity()).updateUrl(myWebView.getUrl());

                } else {
                    Toast.makeText(getActivity(), "THat's past the origin of the universe!!!!", Toast.LENGTH_SHORT).show();
                }//end

            }//end
        });//end

        v.findViewById(R.id.forwardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myWebView.canGoForward()){

                    myWebView.goForward();
                    ((MainActivity)getActivity()).updateUrl(myWebView.getUrl());

                } else {
                    Toast.makeText(getActivity(), "You're going too far!!!", Toast.LENGTH_SHORT).show();
                }//end
            }//end
        });//end

        myWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                ((MainActivity)getActivity()).updateUrl(myWebView.getUrl());

            }//onPageStarted

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ((MainActivity)getActivity()).updateUrl(myWebView.getUrl());
            }//end onPageFinished
        });

        return v;

    }//end onCreateview

    public void loadNewWebView(String url){

        this.givenURL = url;
        myWebView.loadUrl(givenURL);

    }//end launchWebView

    public interface loadNewUrl{
        void newUrlEntered(String url);
    }//end loadNewUrl

}//end WebFragment
