package com.twealthbook.thirdpartydata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Overview implements Serializable {
    private String watchlist_id;
    private String watchlist_name;
    private String total_value;
    private String today_gain_loss;
    private String today_pct_change;
    private String total_gain_loss;
    private String total_pct_change;
    private String as_of_date;
    private String as_of_time;
    private String currency;

    public Overview(){}

    public String getWatchlist_id() {
        return watchlist_id;
    }

    public void setWatchlist_id(String watchlist_id) {
        this.watchlist_id = watchlist_id;
    }

    public String getWatchlist_name() {
        return watchlist_name;
    }

    public void setWatchlist_name(String watchlist_name) {
        this.watchlist_name = watchlist_name;
    }

    public String getTotal_value() {
        return total_value;
    }

    public void setTotal_value(String total_value) {
        this.total_value = total_value;
    }

    public String getToday_gain_loss() {
        return today_gain_loss;
    }

    public void setToday_gain_loss(String today_gain_loss) {
        this.today_gain_loss = today_gain_loss;
    }

    public String getToday_pct_change() {
        return today_pct_change;
    }

    public void setToday_pct_change(String today_pct_change) {
        this.today_pct_change = today_pct_change;
    }

    public String getTotal_gain_loss() {
        return total_gain_loss;
    }

    public void setTotal_gain_loss(String total_gain_loss) {
        this.total_gain_loss = total_gain_loss;
    }

    public String getTotal_pct_change() {
        return total_pct_change;
    }

    public void setTotal_pct_change(String total_pct_change) {
        this.total_pct_change = total_pct_change;
    }

    public String getAs_of_date() {
        return as_of_date;
    }

    public void setAs_of_date(String as_of_date) {
        this.as_of_date = as_of_date;
    }

    public String getAs_of_time() {
        return as_of_time;
    }

    public void setAs_of_time(String as_of_time) {
        this.as_of_time = as_of_time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
