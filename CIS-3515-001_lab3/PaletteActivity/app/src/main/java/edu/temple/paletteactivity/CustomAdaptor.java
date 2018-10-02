package edu.temple.paletteactivity;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdaptor extends BaseAdapter {

    Context context;
    String myData[];

    public CustomAdaptor(Context context, String myData[]){
        this.context = context;
        this.myData = myData;
    }//end CustomAdaptor

    @Override
    public int getCount() {
        return myData.length;
    }//end getCount

    @Override
    public Object getItem(int position) {
        return myData[position];
    }//end getItem

    @Override
    public long getItemId(int position) {
        return 0;
    }//end getItemId

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = new TextView(context);
        String textValue = myData[position];

        textView.setText(textValue);
        textView.setBackgroundColor(Color.parseColor(textValue));
        textView.setTextSize(20);

        return textView;
    }//end getView
}//end CustomAdaptor
