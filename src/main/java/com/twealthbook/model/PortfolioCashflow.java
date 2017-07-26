package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "portfolio_cashflow")
public class PortfolioCashflow implements Serializable {

    private PortfolioCashflowKey portfolioCashflowKey;
    private BigDecimal cashflowAmount;

    @EmbeddedId
    public PortfolioCashflowKey getPortfolioCashflowKey() {
        return portfolioCashflowKey;
    }
    public void setPortfolioCashflowKey(PortfolioCashflowKey portfolioCashflowKey) {
        this.portfolioCashflowKey = portfolioCashflowKey;
    }

    @Column(name = "cashflow_amount")
    public BigDecimal getCashflowAmount() {
        return cashflowAmount;
    }
    public void setCashflowAmount(BigDecimal cashflowAmount) {
        this.cashflowAmount = cashflowAmount;
    }

    @Embeddable
    public static class PortfolioCashflowKey implements Serializable {
        private Long clientId;
        private int portfolioId;
        private Date cashflowDate;

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

        @Column(name = "cashflow_date")
        public Date getCashflowDate() {
            return cashflowDate;
        }
        public void setCashflowDate(Date cashflowDate) {
            this.cashflowDate = cashflowDate;
        }
    }
}
