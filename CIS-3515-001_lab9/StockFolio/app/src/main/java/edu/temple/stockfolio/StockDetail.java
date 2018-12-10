package edu.temple.stockfolio;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockDetail extends Fragment {

    TextView companyName;
    TextView openingPrice;
    TextView currentPrice;
    WebView stockChart;
    Context parent;
    private String chartUrl;
    String currentStock;

    public StockDetail() {
        // Required empty public constructor
    }//end constructor

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = context;
    }//end

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stock_detail, container, false);

        //get url from parent
        chartUrl = parent.getResources().getString(R.string.stock_chart_url);

        //Get views and set enable javascript for webview
        companyName = (TextView) v.findViewById(R.id.companyName);
        openingPrice = (TextView) v.findViewById(R.id.openingPrice);
        currentPrice = (TextView) v.findViewById(R.id.currentPrice);
        stockChart = (WebView) v.findViewById(R.id.stockChart);
        stockChart.getSettings().setJavaScriptEnabled(true);

        return v;

    }//end onCreateView

    //update if prompted to
    public void updateDetails(String newName, String newOpenPrice, String newCurrentPrice){

        this.currentStock = newName;
        companyName.setText(newName);
        openingPrice.setText(newOpenPrice);
        currentPrice.setText(newCurrentPrice);
        stockChart.loadUrl(this.chartUrl + newName);

    }//end update details

}//end StockDetail
