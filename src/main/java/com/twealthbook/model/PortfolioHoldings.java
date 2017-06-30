package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "portfolio_holdings")
public class PortfolioHoldings implements Serializable {

    PortfolioHoldingsKey portfolioHoldingsKey;
    private String securityName;
    private String securityAssetClass;
    private String securityAssetSubClass;
    private String securitySectorName;
    private String securityIndustryName;
    private Float securityQuantity;
    private Float securityBuyRate;
    private Float securityBrokerage;
    private Float securityTax;
    private Float securityTotalCost;
    private Float securityCostRate;
    private Float securityCmp;
    private Float securityHoldingPeriod;
    private Float securityMarketValue;
    private Float securityNetProfit;
    private Float securityAnnualizedReturn;
    private Float securityAbsoluteReturn;
    private Float securityMaturityValue;
    private Date securityMaturityDate;

    @EmbeddedId
    public PortfolioHoldingsKey getPortfolioHoldingsKey() {
        return portfolioHoldingsKey;
    }
    public void setPortfolioHoldingsKey(PortfolioHoldingsKey portfolioHoldingsKey) {
        this.portfolioHoldingsKey = portfolioHoldingsKey;
    }

    @Column(name = "security_name")
    public String getSecurityName() {
        return securityName;
    }
    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    @Column(name = "security_asset_class")
    public String getSecurityAssetClass() {
        return securityAssetClass;
    }
    public void setSecurityAssetClass(String securityAssetClass) {
        this.securityAssetClass = securityAssetClass;
    }

    @Column(name = "security_asset_sub_class")
    public String getSecurityAssetSubClass() {
        return securityAssetSubClass;
    }
    public void setSecurityAssetSubClass(String securityAssetSubClass) {
        this.securityAssetSubClass = securityAssetSubClass;
    }

    @Column(name = "security_sector_name")
    public String getSecuritySectorName() {
        return securitySectorName;
    }
    public void setSecuritySectorName(String securitySectorName) {
        this.securitySectorName = securitySectorName;
    }

    @Column(name = "security_industry_name")
    public String getSecurityIndustryName() {
        return securityIndustryName;
    }
    public void setSecurityIndustryName(String securityIndustryName) {
        this.securityIndustryName = securityIndustryName;
    }

    @Column(name = "security_quantity")
    public Float getSecurityQuantity() {
        return securityQuantity;
    }
    public void setSecurityQuantity(Float securityQuantity) {
        this.securityQuantity = securityQuantity;
    }

    @Column(name = "security_buy_rate")
    public Float getSecurityBuyRate() {
        return securityBuyRate;
    }
    public void setSecurityBuyRate(Float securityBuyRate) {
        this.securityBuyRate = securityBuyRate;
    }

    @Column(name = "security_brokerage")
    public Float getSecurityBrokerage() {
        return securityBrokerage;
    }
    public void setSecurityBrokerage(Float securityBrokerage) {
        this.securityBrokerage = securityBrokerage;
    }

    @Column(name = "security_tax")
    public Float getSecurityTax() {
        return securityTax;
    }
    public void setSecurityTax(Float securityTax) {
        this.securityTax = securityTax;
    }

    @Column(name = "security_total_cost")
    public Float getSecurityTotalCost() {
        return securityTotalCost;
    }
    public void setSecurityTotalCost(Float securityTotalCost) {
        this.securityTotalCost = securityTotalCost;
    }

    @Column(name = "security_cost_rate")
    public Float getSecurityCostRate() {
        return securityCostRate;
    }
    public void setSecurityCostRate(Float securityCostRate) {
        this.securityCostRate = securityCostRate;
    }

    @Column(name = "security_cmp")
    public Float getSecurityCmp() {
        return securityCmp;
    }
    public void setSecurityCmp(Float securityCmp) {
        this.securityCmp = securityCmp;
    }

    @Column(name = "security_holding_period")
    public Float getSecurityHoldingPeriod() {
        return securityHoldingPeriod;
    }
    public void setSecurityHoldingPeriod(Float securityHoldingPeriod) {
        this.securityHoldingPeriod = securityHoldingPeriod;
    }

    @Column(name = "security_market_value")
    public Float getSecurityMarketValue() {
        return securityMarketValue;
    }
    public void setSecurityMarketValue(Float securityMarketValue) {
        this.securityMarketValue = securityMarketValue;
    }

    @Column(name = "security_net_profit")
    public Float getSecurityNetProfit() {
        return securityNetProfit;
    }
    public void setSecurityNetProfit(Float securityNetProfit) {
        this.securityNetProfit = securityNetProfit;
    }

    @Column(name = "security_annualized_return")
    public Float getSecurityAnnualizedReturn() {
        return securityAnnualizedReturn;
    }
    public void setSecurityAnnualizedReturn(Float securityAnnualizedReturn) {
        this.securityAnnualizedReturn = securityAnnualizedReturn;
    }

    @Column(name = "security_absolute_return")
    public Float getSecurityAbsoluteReturn() {
        return securityAbsoluteReturn;
    }
    public void setSecurityAbsoluteReturn(Float securityAbsoluteReturn) {
        this.securityAbsoluteReturn = securityAbsoluteReturn;
    }

    @Column(name = "security_maturity_value")
    public Float getSecurityMaturityValue() {
        return securityMaturityValue;
    }
    public void setSecurityMaturityValue(Float securityMaturityValue) {
        this.securityMaturityValue = securityMaturityValue;
    }

    @Column(name = "security_maturity_date")
    public Date getSecurityMaturityDate() {
        return securityMaturityDate;
    }
    public void setSecurityMaturityDate(Date securityMaturityDate) {
        this.securityMaturityDate = securityMaturityDate;
    }

    @Embeddable
    public static class PortfolioHoldingsKey implements Serializable {
        private Long clientId;
        private int portfolioId;
        private String securityId;
        private Date securityBuyDate;

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

        @Column(name = "security_id")
        public String getSecurityId() {
            return securityId;
        }
        public void setSecurityId(String securityId) {
            this.securityId = securityId;
        }

        @Column(name = "security_buy_date")
        public Date getSecurityBuyDate() {
            return securityBuyDate;
        }
        public void setSecurityBuyDate(Date securityBuyDate) {
            this.securityBuyDate = securityBuyDate;
        }
    }

}
