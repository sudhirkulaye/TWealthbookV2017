package com.twealthbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "setup_dates")
public class SetupDates {

    public Date dateToday;
    public Date dateLastTradingDay;

    public SetupDates(){}

    @Column(name = "date_today")
    @Id
    public Date getDateToday() {
        return dateToday;
    }
    public void setDateToday(Date dateToday) {
        this.dateToday = dateToday;
    }

    @Column(name = "date_last_trading_day")
    public Date getDateLastTradingDay() {
        return dateLastTradingDay;
    }
    public void setDateLastTradingDay(Date dateLastTradingDay) {
        this.dateLastTradingDay = dateLastTradingDay;
    }
}
