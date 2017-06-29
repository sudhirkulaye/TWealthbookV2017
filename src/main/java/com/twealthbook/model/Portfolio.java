package com.twealthbook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable{

    private PortfolioKey portfolioKey;
    private int portfolioActiveStatus;
    private String portfolioGoal;
    private Date portfolioStartDate;
    private Date portfolioEndDate;
    private String portfolioCurrentStrategy;    // Aggressive-1 : Speculative Daily Trading in Equity and F&O ,
                                                // Aggressive-2 : Short Term Undiversified Small Cap Equity,
                                                // Aggrassive-3 : Short Term Undiversified Small and Mid Cap Equity
                                                // Aggrassive-4 : Short Term Undiversified Mid and Large Cap Equity,
                                                // Moderate-1 : Diversified Small and Mid Cap Equity through Mutual Funds,
                                                // Moderate-2 : Diversified Large Cap and Midcap through Mutual Funds ,
                                                // Moderate-3 : Index Mutual Funds or ETFs
                                                // Hybrid : Combination of Moderate and Fixed Income Through Debt Mutual Funds,
                                                // Defensive-1 : Long Duration Debt Mutual Funds
                                                // Defensive-2 : Short Duration Debt Mutual Funds
                                                // Fixed Deposits : Only Fixed Deposits
                                                // Real Estate : Investment in Real Estate
                                                // Commodity : Gold, Silver etc.
                                                // Private Equity : Own Business or investment in private business
    private int portfolioBenchmarkType; //1-Standard Index 2-Customized combination of Indices
    private String portfolioBenchmark;

    @EmbeddedId
    public PortfolioKey getPortfolioKey() {
        return portfolioKey;
    }
    public void setPortfolioKey(PortfolioKey portfolioKey) {
        this.portfolioKey = portfolioKey;
    }

    @Column(name = "portfolio_active_status")
    public int getPortfolioActiveStatus() {
        return portfolioActiveStatus;
    }
    public void setPortfolioActiveStatus(int portfolioActiveStatus) {
        this.portfolioActiveStatus = portfolioActiveStatus;
    }

    @Column(name = "portfolio_goal")
    public String getPortfolioGoal() {
        return portfolioGoal;
    }
    public void setPortfolioGoal(String portfolioGoal) {
        this.portfolioGoal = portfolioGoal;
    }

    @Column(name = "portfolio_start_date")
    public Date getPortfolioStartDate() {
        return portfolioStartDate;
    }
    public void setPortfolioStartDate(Date portfolioStartDate) {
        this.portfolioStartDate = portfolioStartDate;
    }

    @Column(name = "portfolio_end_date")
    public Date getPortfolioEndDate() {
        return portfolioEndDate;
    }
    public void setPortfolioEndDate(Date portfolioEndDate) {
        this.portfolioEndDate = portfolioEndDate;
    }

    @Column(name = "portfolio_current_strategy")
    public String getPortfolioCurrentStrategy() {
        return portfolioCurrentStrategy;
    }
    public void setPortfolioCurrentStrategy(String portfolioCurrentStrategy) {
        this.portfolioCurrentStrategy = portfolioCurrentStrategy;
    }

    @Column(name = "portfolio_benchmark_type")
    public int getPortfolioBenchmarkType() {
        return portfolioBenchmarkType;
    }
    public void setPortfolioBenchmarkType(int portfolioBenchmarkType) {
        this.portfolioBenchmarkType = portfolioBenchmarkType;
    }

    @Column(name = "portfolio_benchmark")
    public String getPortfolioBenchmark() {
        return portfolioBenchmark;
    }
    public void setPortfolioBenchmark(String portfolioBenchmark) {
        this.portfolioBenchmark = portfolioBenchmark;
    }

}
