package com.twealthbook.model;


import java.io.Serializable;
import java.sql.Date;

public class PortfolioViewModel implements Serializable {
    private String relationship;
    private Long clientId;
    private int portfolioId;
    private String portfolioGoal;
    private Date portfolioStartDate;
    private Date portfolioEndDate;
    private int portfolioActiveStatus;
    private String portfolioCurrentStrategy;
    private int portfolioBenchmarkType;
    private String portfolioBenchmark;

    public PortfolioViewModel(){}
    public PortfolioViewModel(String relationship, Portfolio portfolio){
        this.relationship = relationship;
        this.clientId = portfolio.getPortfolioKey().getClientId();
        this.portfolioId = portfolio.getPortfolioKey().getPortfolioId();
        this.portfolioGoal = portfolio.getPortfolioGoal();
        this.portfolioStartDate = portfolio.getPortfolioStartDate();
        this.portfolioEndDate = portfolio.getPortfolioEndDate();
        this.portfolioActiveStatus = portfolio.getPortfolioActiveStatus();
        this.portfolioCurrentStrategy = portfolio.getPortfolioCurrentStrategy();
        this.portfolioBenchmarkType = portfolio.getPortfolioBenchmarkType();
        this.portfolioBenchmark = portfolio.getPortfolioBenchmark();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioGoal() {
        return portfolioGoal;
    }

    public void setPortfolioGoal(String portfolioGoal) {
        this.portfolioGoal = portfolioGoal;
    }

    public Date getPortfolioStartDate() {
        return portfolioStartDate;
    }

    public void setPortfolioStartDate(Date portfolioStartDate) {
        this.portfolioStartDate = portfolioStartDate;
    }

    public Date getPortfolioEndDate() {
        return portfolioEndDate;
    }

    public void setPortfolioEndDate(Date portfolioEndDate) {
        this.portfolioEndDate = portfolioEndDate;
    }

    public int getPortfolioActiveStatus() {
        return portfolioActiveStatus;
    }

    public void setPortfolioActiveStatus(int portfolioActiveStatus) {
        this.portfolioActiveStatus = portfolioActiveStatus;
    }

    public String getPortfolioCurrentStrategy() {
        return portfolioCurrentStrategy;
    }

    public void setPortfolioCurrentStrategy(String portfolioCurrentStrategy) {
        this.portfolioCurrentStrategy = portfolioCurrentStrategy;
    }

    public int getPortfolioBenchmarkType() {
        return portfolioBenchmarkType;
    }

    public void setPortfolioBenchmarkType(int portfolioBenchmarkType) {
        this.portfolioBenchmarkType = portfolioBenchmarkType;
    }

    public String getPortfolioBenchmark() {
        return portfolioBenchmark;
    }

    public void setPortfolioBenchmark(String portfolioBenchmark) {
        this.portfolioBenchmark = portfolioBenchmark;
    }

    @Override
    public String toString() {
        return "PortfolioViewModel{" +
                "relationship='" + relationship + '\'' +
                ", clientId=" + clientId +
                ", portfolioId=" + portfolioId +
                ", portfolioGoal='" + portfolioGoal + '\'' +
                ", portfolioStartDate=" + portfolioStartDate +
                ", portfolioEndDate=" + portfolioEndDate +
                ", portfolioActiveStatus=" + portfolioActiveStatus +
                ", portfolioCurrentStrategy='" + portfolioCurrentStrategy + '\'' +
                ", portfolioBenchmarkType=" + portfolioBenchmarkType +
                ", portfolioBenchmark='" + portfolioBenchmark + '\'' +
                '}';
    }
}
