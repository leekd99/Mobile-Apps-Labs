package edu.temple.stockfolio;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockList extends Fragment {

    ListView stockList;
    StockListAdapter myAdapter;
    Context parent;
    ArrayList<Stock> myStocks = new ArrayList<>();
    onStockSelectedListener selectionListener;

    public StockList() {
        // Required empty public constructor
    }//end constructor

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = context;

        //check to make sure parent has implemented interface
        //if not, throw a tantrum
        try {
            selectionListener = (onStockSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "you forgot to implement onStockSelectedListener, you fool!");
        }//end try catch

    }//end onAttach

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stock_list, container, false);
        //Find list view for fragment
        stockList = (ListView) v.findViewById(R.id.stockListView);

        //create adapter and set adatper for listView
        myAdapter = new StockListAdapter(parent, R.layout.stock_list_view, myStocks);
        stockList.setAdapter(myAdapter);

        stockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectionListener.onStockSelected(myStocks.get(position).getName());
            }//end onItemCLick
        });//end setOnItemSelected

        return v;

    }//end onCreateView

    public void addStock(Stock stock){

        //cadd stock to array list and notify the data set has changed to update view
        myStocks.add(stock);
        myAdapter.notifyDataSetChanged();

    }//end addStock

    public String getStock(int position){

        return myStocks.get(position).toString();

    }//end getStock

    //Container activity must implement this in order to receive messages from fragment
    public interface onStockSelectedListener {
        public void onStockSelected(String tickerName);
    }//end onStockSelectedListener

    public void upDateTicker(Stock stock){

        int target = -1;

        //iterate through stocks till match is found
        for(Stock compStock : myStocks){
            //if matching stock is found
            if(stock.getName().compareToIgnoreCase(compStock.getName()) == 0){
                target = myStocks.indexOf(compStock);
                break;
            }//end
        }//end

        //if no target was found
        if (target != -1) {
            myStocks.set(target, stock);
            myAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(parent, parent.getResources().getString(R.string.update_stock_not_found) + stock.getName(), Toast.LENGTH_SHORT).show();
        }//end

    }//end

}//end StockList
