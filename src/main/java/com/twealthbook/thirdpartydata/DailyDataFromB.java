package com.twealthbook.thirdpartydata;

import java.io.Serializable;
import java.util.List;

public class DailyDataFromB implements Serializable {

//    private Overview overview;
    private List<Ticker> tickers;
//    private List<Watchlist> watchlist_list;


    public List<Ticker> getTickers() {
        return tickers;
    }

    public void setTickers(List<Ticker> tickers) {
        this.tickers = tickers;
    }

}
