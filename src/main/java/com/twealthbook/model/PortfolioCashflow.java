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

    @Embeddable
    public static class PortfolioCashflowKey implements Serializable {
        private Long clientId;
        private int portfolioId;
        private Date date;

        @Column(name = "client_id")
        public Long getClientId() {
            return clientId;
        }
        public void setClientId(Long clientId) {
            this.clientId = clientId;
        }

        @Column(name = "portfolio_id")
        public int getPortfolioId() {
            return portfolioId;
        }
        public void setPortfolioId(int portfolioId) {
            this.portfolioId = portfolioId;
        }

        @Column(name = "date")
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
    }
}
