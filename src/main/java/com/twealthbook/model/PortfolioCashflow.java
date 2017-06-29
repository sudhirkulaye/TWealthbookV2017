package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "portfolio_cashflow")
public class PortfolioCashflow implements Serializable {

    private PortfolioCashflowKey portfolioCashflowKey;
    private Float amount;

    @EmbeddedId
    public PortfolioCashflowKey getPortfolioCashflowKey() {
        return portfolioCashflowKey;
    }
    public void setPortfolioCashflowKey(PortfolioCashflowKey portfolioCashflowKey) {
        this.portfolioCashflowKey = portfolioCashflowKey;
    }

    @Column(name = "amount")
    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

}
