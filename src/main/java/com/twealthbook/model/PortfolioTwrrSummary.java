package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "portfolio_twrr_summary")
public class PortfolioTwrrSummary implements Serializable {

    private PortfolioTwrrSummaryKey portfolioTwrrSummaryKey;
    private Date returnsDate;
    private BigDecimal returnsTwrrSinceCurrentMonth;
    private BigDecimal returnsTwrrSinceCurrentQuarter;
    private BigDecimal returnsTwrrSinceFinYear;
    private BigDecimal returnsTwrrYtd;
    private BigDecimal returnsTwrrOneYear;
    private BigDecimal returnsTwrrSinceInception;

    @EmbeddedId
    public PortfolioTwrrSummaryKey getPortfolioTwrrSummaryKey() {
        return portfolioTwrrSummaryKey;
    }
    public void setPortfolioTwrrSummaryKey(PortfolioTwrrSummaryKey portfolioTwrrSummaryKey) {
        this.portfolioTwrrSummaryKey = portfolioTwrrSummaryKey;
    }

    @Column(name = "returns_date")
    public Date getReturnsDate() {
        return returnsDate;
    }
    public void setReturnsDate(Date returnsDate) {
        this.returnsDate = returnsDate;
    }

    @Column(name = "returns_twrr_since_current_month")
    public BigDecimal getReturnsTwrrSinceCurrentMonth() {
        return returnsTwrrSinceCurrentMonth;
    }
    public void setReturnsTwrrSinceCurrentMonth(BigDecimal returnsTwrrSinceCurrentMonth) {
        this.returnsTwrrSinceCurrentMonth = returnsTwrrSinceCurrentMonth;
    }

    @Column(name = "returns_twrr_since_current_quarter")
    public BigDecimal getReturnsTwrrSinceCurrentQuarter() {
        return returnsTwrrSinceCurrentQuarter;
    }
    public void setReturnsTwrrSinceCurrentQuarter(BigDecimal ReturnsTwrrSinceCurrentQuarter) {
        this.returnsTwrrSinceCurrentQuarter = ReturnsTwrrSinceCurrentQuarter;
    }

    @Column(name = "returns_twrr_since_fin_year")
    public BigDecimal getReturnsTwrrSinceFinYear() {
        return returnsTwrrSinceFinYear;
    }
    public void setReturnsTwrrSinceFinYear(BigDecimal returnsTwrrSinceFinYear) {
        this.returnsTwrrSinceFinYear = returnsTwrrSinceFinYear;
    }

    @Column(name = "returns_twrr_ytd")
    public BigDecimal getReturnsTwrrYtd() {
        return returnsTwrrYtd;
    }
    public void setReturnsTwrrYtd(BigDecimal returnsTwrrYtd) {
        this.returnsTwrrYtd = returnsTwrrYtd;
    }

    @Column(name = "returns_twrr_one_year")
    public BigDecimal getReturnsTwrrOneYear() {
        return this.returnsTwrrOneYear;
    }
    public void setReturnsTwrrOneYear(BigDecimal returnsTwrrOneYear) {
        this.returnsTwrrOneYear = returnsTwrrOneYear;
    }

    @Column(name = "returns_twrr_since_inception")
    public BigDecimal getReturnsTwrrSinceInception() {
        return returnsTwrrSinceInception;
    }
    public void setReturnsTwrrSinceInception(BigDecimal returnsTwrrSinceInception) {
        this.returnsTwrrSinceInception = returnsTwrrSinceInception;
    }

    @Embeddable
    public static class PortfolioTwrrSummaryKey implements Serializable {
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
