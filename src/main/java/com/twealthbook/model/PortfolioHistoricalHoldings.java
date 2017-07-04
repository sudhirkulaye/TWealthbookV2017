package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "portfolio_historical_holdings")
public class PortfolioHistoricalHoldings implements Serializable {

    PortfolioHistoricalHoldingsKey portfolioHistoricalHoldingsKey;
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
    private Float securitySellRate;
    private Float securityBrokerageSell;
    private Float securityTaxSell;
    private Float securityNetSell;
    private Float securityNetSellRate;
    private Float securityRealizedNetProfit;
    private Float securityHoldingPeriod;
    private Float securityAbsoluteReturn;
    private Float securityAnnualizedReturn;

    @EmbeddedId
    public PortfolioHistoricalHoldingsKey getPortfolioHistoricalHoldingsKey() {
        return portfolioHistoricalHoldingsKey;
    }
    public void setPortfolioHistoricalHoldingsKey(PortfolioHistoricalHoldingsKey portfolioHistoricalHoldingsKey) {
        this.portfolioHistoricalHoldingsKey = portfolioHistoricalHoldingsKey;
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

    @Column(name = "security_sell_rate")
    public Float getSecuritySellRate() {
        return securitySellRate;
    }
    public void setSecuritySellRate(Float securitySellRate) {
        this.securitySellRate = securitySellRate;
    }

    @Column(name = "security_brokerage_sell")
    public Float getSecurityBrokerageSell() {
        return securityBrokerageSell;
    }
    public void setSecurityBrokerageSell(Float securityBrokerageSell) {
        this.securityBrokerageSell = securityBrokerageSell;
    }

    @Column(name = "security_tax_sell")
    public Float getSecurityTaxSell() {
        return securityTaxSell;
    }
    public void setSecurityTaxSell(Float securityTaxSell) {
        this.securityTaxSell = securityTaxSell;
    }

    @Column(name = "security_net_sell")
    public Float getSecurityNetSell() {
        return securityNetSell;
    }
    public void setSecurityNetSell(Float securityNetSell) {
        this.securityNetSell = securityNetSell;
    }

    @Column(name = "security_net_sell_rate")
    public Float getSecurityNetSellRate() {
        return securityNetSellRate;
    }
    public void setSecurityNetSellRate(Float securityNetSellRate) {
        this.securityNetSellRate = securityNetSellRate;
    }

    @Column(name = "security_realized_net_profit")
    public Float getSecurityRealizedNetProfit() {
        return securityRealizedNetProfit;
    }
    public void setSecurityRealizedNetProfit(Float securityRealizedNetProfit) {
        this.securityRealizedNetProfit = securityRealizedNetProfit;
    }

    @Column(name = "security_holding_period")
    public Float getSecurityHoldingPeriod() {
        return securityHoldingPeriod;
    }
    public void setSecurityHoldingPeriod(Float securityHoldingPeriod) {
        this.securityHoldingPeriod = securityHoldingPeriod;
    }

    @Column(name = "security_absolute_return")
    public Float getSecurityAbsoluteReturn() {
        return securityAbsoluteReturn;
    }
    public void setSecurityAbsoluteReturn(Float securityAbsoluteReturn) {
        this.securityAbsoluteReturn = securityAbsoluteReturn;
    }

    @Column(name = "security_annualized_return")
    public Float getSecurityAnnualizedReturn() {
        return securityAnnualizedReturn;
    }
    public void setSecurityAnnualizedReturn(Float securityAnnualizedReturn) {
        this.securityAnnualizedReturn = securityAnnualizedReturn;
    }

    @Embeddable
    public static class PortfolioHistoricalHoldingsKey implements Serializable {
        private Long clientId;
        private int portfolioId;
        private String securityId;
        private Date securityBuyDate;
        private Date securitySellDate;

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

        @Column(name = "security_sell_date")
        public Date getSecuritySellDate() {
            return securitySellDate;
        }
        public void setSecuritySellDate(Date securitySellDate) {
            this.securitySellDate = securitySellDate;
        }
    }

}
