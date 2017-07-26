package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "portfolio_historical_holdings")
public class PortfolioHistoricalHoldings implements Serializable {

    PortfolioHistoricalHoldingsKey portfolioHistoricalHoldingsKey;
    private String securityName;
    private Integer securityAssetClassId;
    private Integer securityAssetSubClassId;
    private String securitySectorName;
    private String securityIndustryName;
    private BigDecimal securityQuantity;
    private BigDecimal securityBuyRate;
    private BigDecimal securityBrokerage;
    private BigDecimal securityTax;
    private BigDecimal securityTotalCost;
    private BigDecimal securityCostRate;
    private BigDecimal securitySellRate;
    private BigDecimal securityBrokerageSell;
    private BigDecimal securityTaxSell;
    private BigDecimal securityNetSell;
    private BigDecimal securityNetSellRate;
    private BigDecimal securityRealizedNetProfit;
    private BigDecimal securityHoldingPeriod;
    private BigDecimal securityAbsoluteReturn;
    private BigDecimal securityAnnualizedReturn;

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

    @Column(name = "security_asset_class_id")
    public Integer getSecurityAssetClassId() {
        return securityAssetClassId;
    }
    public void setSecurityAssetClassId(Integer securityAssetClassId) {
        this.securityAssetClassId = securityAssetClassId;
    }

    @Column(name = "security_asset_subclass_id")
    public Integer getSecurityAssetSubClassId() {
        return securityAssetSubClassId;
    }
    public void setSecurityAssetSubClassId(Integer securityAssetSubClassId) {
        this.securityAssetSubClassId = securityAssetSubClassId;
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
    public BigDecimal getSecurityQuantity() {
        return securityQuantity;
    }
    public void setSecurityQuantity(BigDecimal securityQuantity) {
        this.securityQuantity = securityQuantity;
    }

    @Column(name = "security_buy_rate")
    public BigDecimal getSecurityBuyRate() {
        return securityBuyRate;
    }
    public void setSecurityBuyRate(BigDecimal securityBuyRate) {
        this.securityBuyRate = securityBuyRate;
    }

    @Column(name = "security_brokerage")
    public BigDecimal getSecurityBrokerage() {
        return securityBrokerage;
    }
    public void setSecurityBrokerage(BigDecimal securityBrokerage) {
        this.securityBrokerage = securityBrokerage;
    }

    @Column(name = "security_tax")
    public BigDecimal getSecurityTax() {
        return securityTax;
    }
    public void setSecurityTax(BigDecimal securityTax) {
        this.securityTax = securityTax;
    }

    @Column(name = "security_total_cost")
    public BigDecimal getSecurityTotalCost() {
        return securityTotalCost;
    }
    public void setSecurityTotalCost(BigDecimal securityTotalCost) {
        this.securityTotalCost = securityTotalCost;
    }

    @Column(name = "security_cost_rate")
    public BigDecimal getSecurityCostRate() {
        return securityCostRate;
    }
    public void setSecurityCostRate(BigDecimal securityCostRate) {
        this.securityCostRate = securityCostRate;
    }

    @Column(name = "security_sell_rate")
    public BigDecimal getSecuritySellRate() {
        return securitySellRate;
    }
    public void setSecuritySellRate(BigDecimal securitySellRate) {
        this.securitySellRate = securitySellRate;
    }

    @Column(name = "security_brokerage_sell")
    public BigDecimal getSecurityBrokerageSell() {
        return securityBrokerageSell;
    }
    public void setSecurityBrokerageSell(BigDecimal securityBrokerageSell) {
        this.securityBrokerageSell = securityBrokerageSell;
    }

    @Column(name = "security_tax_sell")
    public BigDecimal getSecurityTaxSell() {
        return securityTaxSell;
    }
    public void setSecurityTaxSell(BigDecimal securityTaxSell) {
        this.securityTaxSell = securityTaxSell;
    }

    @Column(name = "security_net_sell")
    public BigDecimal getSecurityNetSell() {
        return securityNetSell;
    }
    public void setSecurityNetSell(BigDecimal securityNetSell) {
        this.securityNetSell = securityNetSell;
    }

    @Column(name = "security_net_sell_rate")
    public BigDecimal getSecurityNetSellRate() {
        return securityNetSellRate;
    }
    public void setSecurityNetSellRate(BigDecimal securityNetSellRate) {
        this.securityNetSellRate = securityNetSellRate;
    }

    @Column(name = "security_realized_net_profit")
    public BigDecimal getSecurityRealizedNetProfit() {
        return securityRealizedNetProfit;
    }
    public void setSecurityRealizedNetProfit(BigDecimal securityRealizedNetProfit) {
        this.securityRealizedNetProfit = securityRealizedNetProfit;
    }

    @Column(name = "security_holding_period")
    public BigDecimal getSecurityHoldingPeriod() {
        return securityHoldingPeriod;
    }
    public void setSecurityHoldingPeriod(BigDecimal securityHoldingPeriod) {
        this.securityHoldingPeriod = securityHoldingPeriod;
    }

    @Column(name = "security_absolute_return")
    public BigDecimal getSecurityAbsoluteReturn() {
        return securityAbsoluteReturn;
    }
    public void setSecurityAbsoluteReturn(BigDecimal securityAbsoluteReturn) {
        this.securityAbsoluteReturn = securityAbsoluteReturn;
    }

    @Column(name = "security_annualized_return")
    public BigDecimal getSecurityAnnualizedReturn() {
        return securityAnnualizedReturn;
    }
    public void setSecurityAnnualizedReturn(BigDecimal securityAnnualizedReturn) {
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
