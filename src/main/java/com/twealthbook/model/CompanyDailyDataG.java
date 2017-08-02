package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "company_daily_data_g")
public class CompanyDailyDataG implements Serializable{

    private CompanyDailyDataGKey companyDailyDataGKey;
    private String companyShortName;
    private BigDecimal companyDailyLastPrice;
    private BigDecimal companyDailyChange;
    private BigDecimal companyDailyMarketCap;
    private BigDecimal companyDailyVolume;
    private BigDecimal companyDailyOpenPrice;
    private BigDecimal companyDailyHighPrice;
    private BigDecimal companyDailyLowPrice;
    private BigDecimal companyDailyVolumeNo;
    private BigDecimal companyDailyRupeeVolume ;
    private BigDecimal companyDailyMarketCapNo;
    private BigDecimal companyDailyMarketCapRank;

    public CompanyDailyDataG(){
        this.companyDailyDataGKey = new CompanyDailyDataGKey();
    }

    public CompanyDailyDataG(String companyTicker,
                             Date companyDailyDate,
                             String companyShortName,
                             BigDecimal companyDailyLastPrice,
                             BigDecimal companyDailyChange,
                             BigDecimal companyDailyMarketCap,
                             BigDecimal companyDailyVolume,
                             BigDecimal companyDailyOpenPrice,
                             BigDecimal companyDailyHighPrice,
                             BigDecimal companyDailyLowPrice,
                             BigDecimal companyDailyVolumeNo,
                             BigDecimal companyDailyRupeeVolume ,
                             BigDecimal companyDailyMarketCapNo,
                             BigDecimal companyDailyMarketCapRank){
        this.companyDailyDataGKey = new CompanyDailyDataGKey(companyTicker, companyDailyDate);
        this.companyShortName = companyShortName;
        this.companyDailyLastPrice = companyDailyLastPrice;
        this.companyDailyChange = companyDailyChange;
        this.companyDailyMarketCap = companyDailyMarketCap;
        this.companyDailyVolume = companyDailyVolume;
        this.companyDailyOpenPrice = companyDailyOpenPrice;
        this.companyDailyHighPrice = companyDailyHighPrice;
        this.companyDailyLowPrice = companyDailyLowPrice;
        this.companyDailyVolumeNo = companyDailyVolumeNo;
        this.companyDailyRupeeVolume = companyDailyRupeeVolume;
        this.companyDailyMarketCapNo = companyDailyMarketCapNo;
        this.companyDailyMarketCapRank = companyDailyMarketCapRank;
    }

    @EmbeddedId
    public CompanyDailyDataGKey getCompanyDailyDataGKey() {
        return companyDailyDataGKey;
    }
    public void setCompanyDailyDataGKey(CompanyDailyDataGKey companyDailyDataGKey) {
        this.companyDailyDataGKey = companyDailyDataGKey;
    }

    @Column(name = "company_short_name")
    public String getCompanyShortName() {
        return companyShortName;
    }
    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    @Column(name = "company_daily_last_price")
    public BigDecimal getCompanyDailyLastPrice() {
        return companyDailyLastPrice;
    }
    public void setCompanyDailyLastPrice(BigDecimal companyDailyLastPrice) {
        this.companyDailyLastPrice = companyDailyLastPrice;
    }

    @Column(name = "company_daily_change")
    public BigDecimal getCompanyDailyChange() {
        return companyDailyChange;
    }
    public void setCompanyDailyChange(BigDecimal companyDailyChange) {
        this.companyDailyChange = companyDailyChange;
    }

    @Column(name = "company_daily_market_cap")
            public BigDecimal getCompanyDailyMarketCap() {
        return companyDailyMarketCap;
    }
    public void setCompanyDailyMarketCap(BigDecimal companyDailyMarketCap) {
        this.companyDailyMarketCap = companyDailyMarketCap;
    }

    @Column(name = "company_daily_volume")
    public BigDecimal getCompanyDailyVolume() {
        return companyDailyVolume;
    }
    public void setCompanyDailyVolume(BigDecimal companyDailyVolume) {
        this.companyDailyVolume = companyDailyVolume;
    }

    @Column(name = "company_daily_open_price")
    public BigDecimal getCompanyDailyOpenPrice() {
        return companyDailyOpenPrice;
    }
    public void setCompanyDailyOpenPrice(BigDecimal companyDailyOpenPrice) {
        this.companyDailyOpenPrice = companyDailyOpenPrice;
    }

    @Column(name = "company_daily_high_price")
    public BigDecimal getCompanyDailyHighPrice() {
        return companyDailyHighPrice;
    }
    public void setCompanyDailyHighPrice(BigDecimal companyDailyHighPrice) {
        this.companyDailyHighPrice = companyDailyHighPrice;
    }

    @Column(name = "company_daily_low_price")
    public BigDecimal getCompanyDailyLowPrice() {
        return companyDailyLowPrice;
    }
    public void setCompanyDailyLowPrice(BigDecimal companyDailyLowPrice) {
        this.companyDailyLowPrice = companyDailyLowPrice;
    }

    @Column(name = "company_daily_volume_no")
    public BigDecimal getCompanyDailyVolumeNo() {
        return companyDailyVolumeNo;
    }
    public void setCompanyDailyVolumeNo(BigDecimal companyDailyVolumeNo) {
        this.companyDailyVolumeNo = companyDailyVolumeNo;
    }

    @Column(name = "company_daily_rupee_volume")
    public BigDecimal getCompanyDailyRupeeVolume() {
        return companyDailyRupeeVolume;
    }
    public void setCompanyDailyRupeeVolume(BigDecimal companyDailyRupeeVolume) {
        this.companyDailyRupeeVolume = companyDailyRupeeVolume;
    }

    @Column(name = "company_daily_market_cap_no")
    public BigDecimal getCompanyDailyMarketCapNo() {
        return companyDailyMarketCapNo;
    }
    public void setCompanyDailyMarketCapNo(BigDecimal companyDailyMarketCapNo) {
        this.companyDailyMarketCapNo = companyDailyMarketCapNo;
    }

    @Column(name = "company_daily_market_cap_rank")
    public BigDecimal getCompanyDailyMarketCapRank() {
        return companyDailyMarketCapRank;
    }
    public void setCompanyDailyMarketCapRank(BigDecimal companyDailyMarketCapRank) {
        this.companyDailyMarketCapRank = companyDailyMarketCapRank;
    }

    @Embeddable
    public static class CompanyDailyDataGKey implements Serializable {
        private String companyTicker;
        private Date companyDailyDate;

        public CompanyDailyDataGKey(){}

        public CompanyDailyDataGKey(String companyTicker, Date companyDailyDate){
            this.companyTicker = companyTicker;
            this.companyDailyDate = companyDailyDate;
        }

        @Column(name = "company_ticker")
        public String getCompanyTicker() {
            return companyTicker;
        }
        public void setCompanyTicker(String companyTicker) {
            this.companyTicker = companyTicker;
        }

        @Column(name = "company_daily_date")
        public Date getCompanyDailyDate() {
            return companyDailyDate;
        }
        public void setCompanyDailyDate(Date companyDailyDate) {
            this.companyDailyDate = companyDailyDate;
        }
    }
}

