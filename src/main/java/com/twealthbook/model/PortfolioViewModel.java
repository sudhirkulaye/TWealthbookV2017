package com.twealthbook.model;


import com.twealthbook.service.ApiService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class PortfolioViewModel implements Serializable {
    private String relationship;
    private Long clientId;
    private int portfolioId;
    private String portfolioDescription;
    private Date portfolioStartDate;
    private Date portfolioEndDate;
    private int portfolioActiveStatus;
    private String portfolioCurrentStrategy;
    private int portfolioBenchmarkId;
    private String portfolioBenchmarkDescription;
    private BigDecimal portfolioValue;

    public PortfolioViewModel(){}
    public PortfolioViewModel(String relationship, Portfolio portfolio){
        this.relationship = relationship;
        this.clientId = portfolio.getPortfolioKey().getClientId();
        this.portfolioId = portfolio.getPortfolioKey().getPortfolioId();
        this.portfolioDescription = portfolio.getPortfolioDescription();
        this.portfolioStartDate = portfolio.getPortfolioStartDate();
        this.portfolioEndDate = portfolio.getPortfolioEndDate();
        this.portfolioActiveStatus = portfolio.getPortfolioActiveStatus();
        this.portfolioCurrentStrategy = portfolio.getPortfolioCurrentStrategy();
        this.portfolioBenchmarkId = portfolio.getPortfolioBenchmarkId();
        this.portfolioBenchmarkDescription = ApiService.getBenchmarkDescription(this.portfolioBenchmarkId);
        this.portfolioValue = portfolio.getPortfolioValue();
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

    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
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

    public int getPortfolioBenchmarkId() {
        return portfolioBenchmarkId;
    }
    public void setPortfolioBenchmarkId(int portfolioBenchmarkId) {
        this.portfolioBenchmarkId = portfolioBenchmarkId;
    }

    public String getPortfolioBenchmarkDescription() {
        return portfolioBenchmarkDescription;
    }
    public void setPortfolioBenchmarkDescription(String portfolioBenchmarkDescription) {
        this.portfolioBenchmarkDescription = portfolioBenchmarkDescription;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }
    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

}
