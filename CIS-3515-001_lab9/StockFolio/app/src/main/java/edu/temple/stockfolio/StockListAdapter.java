package edu.temple.stockfolio;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StockListAdapter extends ArrayAdapter<Stock> {

    private Context myContext;
    private int stockLayout;


    public StockListAdapter(@NonNull Context context, int resource, ArrayList<Stock> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.stockLayout = resource;
    }//end constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String companyName = getItem(position).getName();
        String currentPrice = getItem(position).getCurrentPrice();
        String openingPrice = getItem(position).getOpenPrice();

        //Stock stock = new Stock(companyName, currentPrice, openingPrice);

        LayoutInflater infalter = LayoutInflater.from(myContext);
        convertView = infalter.inflate(stockLayout, parent, false);

        //get Views
        TextView companyNameView = (TextView) convertView.findViewById(R.id.stockName);
        TextView currentPriceView = (TextView) convertView.findViewById(R.id.stockCurrentPrice);

        //set Views
        companyNameView.setText(companyName);
        currentPriceView.setText(currentPrice);

        Double convertedOpen = Double.valueOf(openingPrice);
        Double convertedCurrent = Double.valueOf(currentPrice);

        if (convertedCurrent > convertedOpen) {
            currentPriceView.setBackgroundColor(Color.GREEN);
        } else {
            currentPriceView.setBackgroundColor(Color.RED);
        }//end set color

        return convertView;

    }//end gerView

    @Nullable
    @Override
    public Stock getItem(int position) {
        return super.getItem(position);
    }//end getItem
}//end StockListAdpater
