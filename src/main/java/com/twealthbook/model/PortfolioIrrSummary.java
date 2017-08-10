package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "portfolio_irr_summary")
public class PortfolioIrrSummary implements Serializable {

    private PortfolioIrrSummaryKey portfolioIrrSummaryKey;
    private java.sql.Date returnsDate;
    private BigDecimal returnsIrrSinceCurrentMonth;
    private BigDecimal returnsIrrSinceCurrentQuarter;
    private BigDecimal returnsIrrSinceFinYear;
    private BigDecimal returnsIrrYtd;
    private BigDecimal returnsIrrOneYear;
    private BigDecimal returnsIrrSinceInception;

    @EmbeddedId
    public PortfolioIrrSummaryKey getPortfolioIrrSummaryKey() {
        return portfolioIrrSummaryKey;
    }
    public void setPortfolioIrrSummaryKey(PortfolioIrrSummaryKey portfolioIrrSummaryKey) {
        this.portfolioIrrSummaryKey = portfolioIrrSummaryKey;
    }

    @Column(name = "returns_date")
    public Date getReturnsDate() {
        return returnsDate;
    }
    public void setReturnsDate(Date returnsDate) {
        this.returnsDate = returnsDate;
    }

    @Column(name = "returns_irr_since_current_month")
    public BigDecimal getReturnsIrrSinceCurrentMonth() {
        return returnsIrrSinceCurrentMonth;
    }
    public void setReturnsIrrSinceCurrentMonth(BigDecimal returnsIrrSinceCurrentMonth) {
        this.returnsIrrSinceCurrentMonth = returnsIrrSinceCurrentMonth;
    }

    @Column(name = "returns_irr_since_current_quarter")
    public BigDecimal getReturnsIrrSinceCurrentQuarter() {
        return returnsIrrSinceCurrentQuarter;
    }
    public void setReturnsIrrSinceCurrentQuarter(BigDecimal returnsIrrSinceCurrentQuarter) {
        this.returnsIrrSinceCurrentQuarter = returnsIrrSinceCurrentQuarter;
    }

    @Column(name = "returns_irr_since_fin_year")
    public BigDecimal getReturnsIrrSinceFinYear() {
        return returnsIrrSinceFinYear;
    }
    public void setReturnsIrrSinceFinYear(BigDecimal returnsIrrSinceFinYear) {
        this.returnsIrrSinceFinYear = returnsIrrSinceFinYear;
    }

    @Column(name = "returns_irr_ytd")
    public BigDecimal getReturnsIrrYtd() {
        return returnsIrrYtd;
    }
    public void setReturnsIrrYtd(BigDecimal returnsIrrYtd) {
        this.returnsIrrYtd = returnsIrrYtd;
    }

    @Column(name = "returns_irr_one_year")
    public BigDecimal getReturnsIrrOneYear() {
        return returnsIrrOneYear;
    }
    public void setReturnsIrrOneYear(BigDecimal returnsIrrOneYear) {
        this.returnsIrrOneYear = returnsIrrOneYear;
    }

    @Column(name = "returns_irr_since_inception")
    public BigDecimal getReturnsIrrSinceInception() {
        return returnsIrrSinceInception;
    }
    public void setReturnsIrrSinceInception(BigDecimal returnsIrrSinceInception) {
        this.returnsIrrSinceInception = returnsIrrSinceInception;
    }

    @Embeddable
    public static class PortfolioIrrSummaryKey implements Serializable {
        private Long clientId;
        private int portfolioId;
        private int benchmarkId;

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

        @Column(name = "benchmark_id")
        public int getBenchmarkId() {
            return benchmarkId;
        }
        public void setBenchmarkId(int benchmarkId) {
            this.benchmarkId = benchmarkId;
        }
    }
}
