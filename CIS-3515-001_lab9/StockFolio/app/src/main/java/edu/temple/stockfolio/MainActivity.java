package edu.temple.stockfolio;

import android.content.Context;
import android.content.DialogInterface;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements StockList.onStockSelectedListener{

    private static final String LEDGER_FILE_NAME = "ledger.txt";
    private static final String UPDATE_TRACKER = "last_update.txt";

    private FloatingActionButton addStockFab;
    private StockList stockListFragment;
    private StockDetail stockDetailFragment;
    private FragmentManager fm;
    boolean singlePane;
    private TextView msg;

    private StockUpdater updateThread;
    private RequestQueue myRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singlePane = findViewById(R.id.stockDetail) == null;
        addStockFab = findViewById(R.id.addStockFab);
        msg = findViewById(R.id.addstock);
        stockListFragment = new StockList();
        stockDetailFragment = new StockDetail();
        fm = getSupportFragmentManager();
        myRequestQueue = Volley.newRequestQueue(this);

        DetailUpdateChecker observer = new DetailUpdateChecker(getFilesDir() + "/" + UPDATE_TRACKER);
        observer.startWatching();

        //Implement add stock fab and set onClick
        addStockFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add Stock and stop thread and start again
                displayAddDialog();
            }//end onClick
        });//end

        if (stockListFragment.myStocks.size() !=0){
            msg.setVisibility(View.GONE);
        }//end

        fm.beginTransaction()
                .add(R.id.stockList, stockListFragment)
                .commit();

        if (!singlePane) {

            fm.beginTransaction()
                    .replace(R.id.stockDetail, stockDetailFragment)
                    .commit();

        }//end check for singlePane*/

    }//end onCreate

    @Override
    protected void onStart() {
        super.onStart();

        DetailUpdateChecker observer = new DetailUpdateChecker(getFilesDir() + "/" + UPDATE_TRACKER);
        observer.startWatching();
        /*int size = stockListFragment.myStocks.size();
        String thing = Integer.toString(size);
        Toast.makeText(this,thing,Toast.LENGTH_SHORT).show();*/
        loadSavedStocks();

        updateThread = new StockUpdater();
        updateThread.run();

    }//end onStart

    @Override
    protected void onResume() {
        super.onResume();
        DetailUpdateChecker observer = new DetailUpdateChecker(getFilesDir() + "/" + UPDATE_TRACKER);
        observer.startWatching();
    }//end onResume

    @Override
    protected void onRestart() {
        super.onRestart();
        DetailUpdateChecker observer = new DetailUpdateChecker(getFilesDir() + "/" + UPDATE_TRACKER);
        observer.startWatching();
    }//end onRestart

    @Override
    protected void onStop() {
        super.onStop();

        updateThread.currentThread().interrupt();


    }//end onStop

    @Override
    protected void onDestroy() {
        super.onDestroy();

        updateThread.currentThread().interrupt();

    }//end onDestroy

    /*Method for when fab is clicked*/
    public void displayAddDialog(){

        AlertDialog.Builder stockDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final EditText stockTicker = new EditText(MainActivity.this);

        stockDialogBuilder.setMessage(R.string.dialog_message);
        stockDialogBuilder.setTitle(R.string.dialog_title);
        stockDialogBuilder.setView(stockTicker);

        stockDialogBuilder.setPositiveButton(R.string.dialog_add_stock, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newStock = stockTicker.getText().toString();

                searchStock(newStock);

                if (stockListFragment.myStocks.size() !=0){
                    msg.setVisibility(View.GONE);
                }//end

            }//end onClick
        });//end add button

        stockDialogBuilder.setNegativeButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                test();
                dialog.cancel();
            }//end onClick
        });//end close button

        stockDialogBuilder.show();

    }//end displayAddDialog

    //search for stock using Volley
    private void searchStock(final String ticker){

        final Context thisActivity = this;
        //create url to search stock
        String stock_API_url = getResources().getString(R.string.stock_ticker_search_url) + ticker;
        //"http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol="


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stock_API_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //check to see if ticker is valid
                try {

                    if( (response.has("Status")) && (response.getString("Status").equals("SUCCESS")) ){

                        Stock tempStock = new Stock(response.getString("Symbol"), response.getString("Open"), response.getString("LastPrice"));

                        stockListFragment.addStock(tempStock);

                        //check to see if ticker should be saved to ledger
                        if(saveCheck(ticker)){
                            saveStockTicker(ticker);
                        }//end check for save

                        //save or update stock info
                        saveStockInfo(tempStock);

                        //save last stock saved or updated
                        trackerUpdate(tempStock.getName());

                    } else {
                        Toast.makeText(thisActivity,R.string.volley_error_invalid_ticker,Toast.LENGTH_SHORT).show();
                    }//end check

                } catch (JSONException e) {
                    e.printStackTrace();

                }//end try catch

            }//end onResponse
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //Toast.makeText(thisActivity, R.string.volley_on_error_response, Toast.LENGTH_SHORT).show();

                //Toast.makeText(thisActivity, stock_API_url, Toast.LENGTH_SHORT).show();

            }//end onErrorResponse
        });//end Response.ErrorListener

        myRequestQueue.add(request);

    }//end searchStock

    /*This method writes ticker to master list of tickers*/
    private void saveStockTicker(String ticker) {

        FileOutputStream fileOutputStream = null;
        String name = ticker + "\n";

        try {
            fileOutputStream = openFileOutput(LEDGER_FILE_NAME,MODE_PRIVATE|MODE_APPEND);
            fileOutputStream.write(name.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }//end try catch
            }//end file close
        }//end try catch

    }//end saveStockInfo

    /*This method writes stock info to files*/
    private void saveStockInfo(Stock stock){

        String name = stock.getName() + ".txt";
        String save = stock.getName() + "\n" + stock.getOpenPrice() + "\n" + stock.getCurrentPrice() + "\n";

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(name,MODE_PRIVATE);
            fileOutputStream.write(save.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }//end try catch
            }//end file close
        }//end try catch

    }//end saveStockInfo

    private void trackerUpdate(String lastChange){

        String name = UPDATE_TRACKER;

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(name,MODE_PRIVATE);
            fileOutputStream.write(lastChange.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }//end try catch
            }//end file close
        }//end try catch

    }//end trackerUpdate

    private void loadSavedStocks(){

        FileInputStream fileInputStream = null;
        File tmpFile = new File(getFilesDir()+ "/" + LEDGER_FILE_NAME);

        //check to see if there is anything to load
        if (tmpFile.exists()) {

            try {
                fileInputStream = openFileInput(LEDGER_FILE_NAME);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader fileReader =  new BufferedReader(inputStreamReader);
                String tempVar;

                //while new lineExists read in lines and request info for such stocks again
                while( (tempVar = fileReader.readLine()) != null ){
                    searchStock(tempVar);
                }//end while

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//end try catch
                }//end check for inputStream
            }//end try catch
        } else {
            return;
        }//end check

    }//end loadSavedStocks

    private boolean saveCheck(String string){

        //saveCheck(ticker)

        boolean doSave = true;
        FileInputStream fileInputStream = null;
        File tmpFile = new File(getFilesDir()+ "/" +LEDGER_FILE_NAME);

        //check to see if there is anything to load
        if (tmpFile.exists()) {

            try {
                fileInputStream = openFileInput(LEDGER_FILE_NAME);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader fileReader =  new BufferedReader(inputStreamReader);
                String tempVar;

                //while new lineExists read in lines and request info for such stocks again
                while( (tempVar = fileReader.readLine()) != null ){

                    //if ticker is present in file do not save
                    if( (tempVar.compareToIgnoreCase(string)) == 0 ){
                        doSave = false;
                    }//end check

                }//end while

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//end try catch
                }//end check for inputStream
            }//end try catch
        } else {
            return doSave;
        }//end check

        return doSave;

    }//end saveCheck

    public void onStockSelected(String name){

        String[] info = gatheredData(name);
        Stock tempStocky = new Stock(info[0],info[1],info[2]);

        if (singlePane){

            fm.beginTransaction()
                    .replace(R.id.stockList, stockDetailFragment)
                    .addToBackStack(null)
                    .commit();

            //stockDetailFragment.updateDetails(info[0],info[1],info[2]);

        } else {

            //stockDetailFragment.updateDetails(info[0],info[1],info[2]);

        }//end adding fragment
        fm.executePendingTransactions();
        stockDetailFragment.updateDetails(info[0],info[1],info[2]);

        //Toast.makeText(this, "you picked " + name, Toast.LENGTH_LONG).show();

    }//end onStockSelected

    private class DetailUpdateChecker extends FileObserver {

        public DetailUpdateChecker(String path) {
            super(path);
            Log.d("here", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + path);
        }//end constructor

        @Override
        public void onEvent(int event, String path) {
            Log.d("weep", "000000000000000000000000000000000000000000000000000000000000000000");
            if (event == CLOSE_WRITE){
                Log.d("JKI", "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");

                Stock temp = new Stock("M", "50", "150");

                if (!singlePane) {

                    stockDetailFragment.updateDetails(temp.getName(), temp.getOpenPrice(), temp.getCurrentPrice());

                }

                Log.d("JKI", "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
            }//end if event occurs
        }//end

    }//end

    private boolean updateMatch(String name){

        FileInputStream fileInputStream = null;
        File tmpFile = new File(getFilesDir()+ "/" + UPDATE_TRACKER);

        //check to see if there is anything to load
        if (tmpFile.exists()) {

            Log.d("here", "1111111111111111111111111111111111111111111111111111111111111111111111111");

            try {
                fileInputStream = openFileInput(UPDATE_TRACKER);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader fileReader =  new BufferedReader(inputStreamReader);
                String tempVar;

                //while new lineExists read in lines and request info for such stocks again
                while( (tempVar = fileReader.readLine()) != null ){
                    if( name.compareToIgnoreCase(tempVar) == 0){
                        Log.d("here", "2222222222222222222222222222222222222222222222222222222222222222222");
                        return true;
                    }//end check for update match
                }//end while

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//end try catch
                }//end check for inputStream
            }//end try catch
        } else {
            return false;
        }//end check

        return false;

    }//end loadSavedStocks

    private String[] gatheredData(String name){

        FileInputStream fileInputStream = null;
        String fileName = name + ".txt";
        File tmpFile = new File(getFilesDir()+ "/" + fileName);
        String[] info = new String[3];
        int i = 0;

        //check to see file exists
        if (tmpFile.exists()){

            try {
                fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader fileReader =  new BufferedReader(inputStreamReader);
                String tempVar;

                //while new lineExists read in lines gather info for stock display
                while( (tempVar = fileReader.readLine()) != null ){
                    info[i] = tempVar;
                    i++;
                }//end while

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }//end try catch

        } else {
            Toast.makeText(this, R.string.stock_file_error, Toast.LENGTH_LONG).show();
        }//end check

        return info;

    }//end gatheredData

    private void test(){
        trackerUpdate("123");
    }

    public void threadUpdate(){



    }//end

    public class StockUpdater extends Thread {

        final private int ID = 0000;

        public StockUpdater() {

        }//end private constructor

        public int getID(){
            return this.ID;
        }//end

        @Override
        public void run() {

            while(stockListFragment.myStocks.size() != 0 && !updateThread.interrupted()){

                for(Stock stock : stockListFragment.myStocks){

                    saveStockInfo(stock);

                    if(updateThread.isInterrupted()){
                        return;
                    }//end if

                    try {
                        updateThread.sleep(120000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }//end for

            }//end while

        }//end run

    }//end StockUpdater

}//end MainActivity

