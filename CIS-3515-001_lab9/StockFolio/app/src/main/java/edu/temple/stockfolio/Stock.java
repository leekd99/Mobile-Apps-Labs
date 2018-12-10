package edu.temple.stockfolio;

public class Stock {

    private String name;
    private String openPrice;
    private String currentPrice;

    public Stock(String name, String openPrice, String currentPrice){

        this.name = name;
        this.openPrice = openPrice;
        this.currentPrice = currentPrice;

    }//end constructor

    public String getName() {
        return name;
    }//end getName

    public void setName(String name) {
        this.name = name;
    }//end setName

    public String getOpenPrice() {
        return openPrice;
    }//end getOpenPrice

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }//end setOpenPrice

    public String getCurrentPrice() {
        return currentPrice;
    }//end getCurrentPrice

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }//end setCurrentPrice

    public String toString(){
        return this.name + " " + this.openPrice + " " + this.currentPrice;
    }//end toString

}//end Stock
