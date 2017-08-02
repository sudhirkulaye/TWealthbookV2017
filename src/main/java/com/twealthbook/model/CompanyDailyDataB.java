package com.twealthbook.model;

import com.twealthbook.thirdpartydata.Ticker;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "company_daily_data_b")
public class CompanyDailyDataB implements Serializable{
    private CompanyDailyDataBKey companyDailyDataBKey;
    private String companyNameB;
    private BigDecimal companyDailyClosingPrice;
    private BigDecimal companyDailyPreviousDayClosingPrice;
    private BigDecimal companyDailyVolume;
    private BigDecimal companyDailyVolume30d;
    private BigDecimal companyDailyMarketCap;
    private BigDecimal companyDailySharesOutstanding;
    private BigDecimal companyDailyEps;
    private BigDecimal companyDailyBestEpsLstQtr;
    private BigDecimal companyDailyEstEpsLastQtr;
    private BigDecimal companyDailyEpsSurpriseLastQtr;
    private BigDecimal companyDailyEstimatedEpsYr;
    private BigDecimal companyDailyEstimatedEpsNxtQtr;
    private BigDecimal companyDailyCurrentPe;
    private BigDecimal companyDailyEstimatedPeCurYr;
    private BigDecimal companyDailyPriceBook;
    private BigDecimal companyDailyPriceToSales;
    private BigDecimal companyDailyDividendYield;
    private String companySectorNameB;
    private String companyIndustryNameB;
    private String companySubIndustryNameB;
    private String companyDailyDS199;
    private String companyDailyDS201;
    private BigDecimal companyDaily52WeekLow;
    private BigDecimal companyDaily52WeekHigh;
    private BigDecimal companyDailyPriceChge1D;
    private BigDecimal companyDailyPctChge1D;
    private BigDecimal companyDailyTotalReturn1year;
    private BigDecimal companyDailyTotalReturnYTD;
    private BigDecimal companyDailyMarketCapRank;
    private Date companyDailyLastEarningDate;
    private Date companyDailyNextEarningDate;
    private String companyDailyLatestAnncmtPeriod;
    private int companyDailyShares;

    public CompanyDailyDataB(){
        this.companyDailyDataBKey = new CompanyDailyDataBKey();
    }

    public CompanyDailyDataB(Ticker ticker){
        java.util.Date utimeToUtilDate = new java.util.Date((long)ticker.getUTIME()*1000);
        this.companyDailyDataBKey = new CompanyDailyDataBKey(ticker.getTicker(),new java.sql.Date(utimeToUtilDate.getTime()));
        this.companyNameB = ticker.getDisp_name();
        this.companyDailyClosingPrice = ticker.getLast_price()!= null ? new BigDecimal(ticker.getLast_price()):new BigDecimal(0);
        this.companyDailyPreviousDayClosingPrice = new BigDecimal(0);
        this.companyDailyVolume = ticker.getVolume()!= null ? new BigDecimal(ticker.getVolume()) : new BigDecimal(0);
        this.companyDailyVolume30d = ticker.getVolume_30d() != null ? new BigDecimal(ticker.getVolume_30d()) : new BigDecimal(0);
        this.companyDailyMarketCap = ticker.getMarket_cap() != null ? new BigDecimal(ticker.getMarket_cap()) : new BigDecimal(0);
        if (this.getCompanyDailyMarketCap() != null && this.getCompanyDailyClosingPrice() != null && ((float) this.getCompanyDailyClosingPrice().floatValue()) > 0.0)
            this.companyDailySharesOutstanding = this.getCompanyDailyMarketCap().divide(this.getCompanyDailyClosingPrice(), 2, RoundingMode.HALF_UP);
        this.companyDailyEps = ticker.getEps() != null ? new BigDecimal(ticker.getEps()) : new BigDecimal(0);
        this.companyDailyBestEpsLstQtr = ticker.getBest_eps_lst_qtr() != null ? new BigDecimal(ticker.getBest_eps_lst_qtr()) : new BigDecimal(0);
        this.companyDailyEstEpsLastQtr = ticker.getEst_eps_last_qtr() != null ? new BigDecimal(ticker.getEst_eps_last_qtr()) : new BigDecimal(0);
        this.companyDailyEpsSurpriseLastQtr = ticker.getEps_surprise_last_qtr() != null ? new BigDecimal(ticker.getEps_surprise_last_qtr()) : new BigDecimal(0);
        this.companyDailyEstimatedEpsYr = ticker.getEstimated_eps_yr() != null ? new BigDecimal(ticker.getEstimated_eps_yr()) : new BigDecimal(0);
        this.companyDailyEstimatedEpsNxtQtr = ticker.getEstimated_eps_nxt_qtr() != null ? new BigDecimal(ticker.getEstimated_eps_nxt_qtr()) : new BigDecimal(0);
        this.companyDailyCurrentPe = ticker.getCurrent_pe() != null ? new BigDecimal(ticker.getCurrent_pe()) : new BigDecimal(0);
        this.companyDailyEstimatedPeCurYr = ticker.getEstimated_pe_cur_yr() != null ? new BigDecimal(ticker.getEstimated_pe_cur_yr()) : new BigDecimal(0);
        this.companyDailyPriceBook = ticker.getPrice_book() != null ? new BigDecimal(ticker.getPrice_book()) : new BigDecimal(0);
        this.companyDailyPriceToSales = new BigDecimal(0);
        this.companyDailyDividendYield = ticker.getDividend_indicated_gross_yield() != null ? new BigDecimal(ticker.getDividend_indicated_gross_yield()) : new BigDecimal(0);
        this.companySectorNameB = ticker.getCompany_sector();
        this.companyIndustryNameB = ticker.getCompany_industry();
        this.companySubIndustryNameB = "";
        this.companyDailyDS199 = ticker.getDS199();
        this.companyDailyDS201 = ticker.getDS201();
        this.companyDaily52WeekLow = ticker.getRange_52wk_low() != null ? new BigDecimal(ticker.getRange_52wk_low()) : new BigDecimal(0);
        this.companyDaily52WeekHigh = ticker.getRange_52wk_high() != null ? new BigDecimal(ticker.getRange_52wk_high()) : new BigDecimal(0);
        this.companyDailyPriceChge1D = ticker.getPrice_chge_1D() != null ? new BigDecimal(ticker.getPrice_chge_1D()) : new BigDecimal(0);
        this.companyDailyPctChge1D = ticker.getPct_chge_1D() != null ? new BigDecimal(ticker.getPct_chge_1D()) : new BigDecimal(0);
        this.companyDailyTotalReturn1year = ticker.getPct_return_52wk() != null ? new BigDecimal(ticker.getPct_return_52wk()): new BigDecimal(0);
        this.companyDailyTotalReturnYTD = new BigDecimal(0);
        this.companyDailyMarketCapRank = new BigDecimal(0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date lastEarningDate = null;
        java.sql.Date nextEarningDate = null;
        try {
            lastEarningDate = ticker.getLast_earning_date() != null && !ticker.getLast_earning_date().isEmpty()? new java.sql.Date(format.parse(ticker.getLast_earning_date()).getTime()):new java.sql.Date(format.parse("2000-01-01").getTime());
            nextEarningDate = ticker.getNext_earning_date() != null && !ticker.getNext_earning_date().isEmpty()? new java.sql.Date(format.parse(ticker.getNext_earning_date()).getTime()):new java.sql.Date(format.parse("2000-01-01").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.companyDailyLastEarningDate = lastEarningDate;
        this.companyDailyNextEarningDate = nextEarningDate;
        this.companyDailyLatestAnncmtPeriod = ticker.getLatest_anncmt_period();
        this.companyDailyShares = ticker.getShares();
    }

    @EmbeddedId
    public CompanyDailyDataBKey getCompanyDailyDataBKey() {
        return companyDailyDataBKey;
    }
    public void setCompanyDailyDataBKey(CompanyDailyDataBKey companyDailyDataBKey) {
        this.companyDailyDataBKey = companyDailyDataBKey;
    }

    @Column(name = "company_name_b")
    public String getCompanyNameB() {
        return companyNameB;
    }
    public void setCompanyNameB(String companyNameB) {
        this.companyNameB = companyNameB;
    }

    @Column(name = "company_daily_closing_price")
    public BigDecimal getCompanyDailyClosingPrice() {
        return companyDailyClosingPrice;
    }
    public void setCompanyDailyClosingPrice(BigDecimal companyDailyClosingPrice) {
        this.companyDailyClosingPrice = companyDailyClosingPrice;
    }

    @Column(name = "company_daily_previous_day_closing_price")
    public BigDecimal getCompanyDailyPreviousDayClosingPrice() {
        return companyDailyPreviousDayClosingPrice;
    }
    public void setCompanyDailyPreviousDayClosingPrice(BigDecimal companyDailyPreviousDayClosingPrice) {
        this.companyDailyPreviousDayClosingPrice = companyDailyPreviousDayClosingPrice;
    }

    @Column(name = "company_daily_volume")
    public BigDecimal getCompanyDailyVolume() {
        return companyDailyVolume;
    }
    public void setCompanyDailyVolume(BigDecimal companyDailyVolume) {
        this.companyDailyVolume = companyDailyVolume;
    }

    @Column(name = "company_daily_volume_30d")
    public BigDecimal getCompanyDailyVolume30d() {
        return companyDailyVolume30d;
    }
    public void setCompanyDailyVolume30d(BigDecimal companyDailyVolume30d) {
        this.companyDailyVolume30d = companyDailyVolume30d;
    }

    @Column(name = "company_daily_market_cap")
    public BigDecimal getCompanyDailyMarketCap() {
        return companyDailyMarketCap;
    }
    public void setCompanyDailyMarketCap(BigDecimal companyDailyMarketCap) {
        this.companyDailyMarketCap = companyDailyMarketCap;
    }

    @Column(name = "company_daily_shares_outstanding")
    public BigDecimal getCompanyDailySharesOutstanding() {
        return companyDailySharesOutstanding;
    }
    public void setCompanyDailySharesOutstanding(BigDecimal companyDailySharesOutstanding) {
        this.companyDailySharesOutstanding = companyDailySharesOutstanding;
    }

    @Column(name = "company_daily_eps")
    public BigDecimal getCompanyDailyEps() {
        return companyDailyEps;
    }
    public void setCompanyDailyEps(BigDecimal companyDailyEps) {
        this.companyDailyEps = companyDailyEps;
    }

    @Column(name = "company_daily_best_eps_lst_qtr")
    public BigDecimal getCompanyDailyBestEpsLstQtr() {
        return companyDailyBestEpsLstQtr;
    }
    public void setCompanyDailyBestEpsLstQtr(BigDecimal companyDailyBestEpsLstQtr) {
        this.companyDailyBestEpsLstQtr = companyDailyBestEpsLstQtr;
    }

    @Column(name = "company_daily_est_eps_last_qtr")
    public BigDecimal getCompanyDailyEstEpsLastQtr() {
        return companyDailyEstEpsLastQtr;
    }
    public void setCompanyDailyEstEpsLastQtr(BigDecimal companyDailyEstEpsLastQtr) {
        this.companyDailyEstEpsLastQtr = companyDailyEstEpsLastQtr;
    }

    @Column(name = "company_daily_eps_surprise_last_qtr")
    public BigDecimal getCompanyDailyEpsSurpriseLastQtr() {
        return companyDailyEpsSurpriseLastQtr;
    }
    public void setCompanyDailyEpsSurpriseLastQtr(BigDecimal companyDailyEpsSurpriseLastQtr) {
        this.companyDailyEpsSurpriseLastQtr = companyDailyEpsSurpriseLastQtr;
    }

    @Column(name = "company_daily_estimated_eps_yr")
    public BigDecimal getCompanyDailyEstimatedEpsYr() {
        return companyDailyEstimatedEpsYr;
    }
    public void setCompanyDailyEstimatedEpsYr(BigDecimal companyDailyEstimatedEpsYr) {
        this.companyDailyEstimatedEpsYr = companyDailyEstimatedEpsYr;
    }

    @Column(name = "company_daily_estimated_eps_nxt_qtr")
    public BigDecimal getCompanyDailyEstimatedEpsNxtQtr() {
        return companyDailyEstimatedEpsNxtQtr;
    }
    public void setCompanyDailyEstimatedEpsNxtQtr(BigDecimal companyDailyEstimatedEpsNxtQtr) {
        this.companyDailyEstimatedEpsNxtQtr = companyDailyEstimatedEpsNxtQtr;
    }

    @Column(name = "company_daily_current_pe")
    public BigDecimal getCompanyDailyCurrentPe() {
        return companyDailyCurrentPe;
    }
    public void setCompanyDailyCurrentPe(BigDecimal companyDailyCurrentPe) {
        this.companyDailyCurrentPe = companyDailyCurrentPe;
    }

    @Column(name = "company_daily_estimated_pe_cur_yr")
    public BigDecimal getCompanyDailyEstimatedPeCurYr() {
        return companyDailyEstimatedPeCurYr;
    }
    public void setCompanyDailyEstimatedPeCurYr(BigDecimal companyDailyEstimatedPeCurYr) {
        this.companyDailyEstimatedPeCurYr = companyDailyEstimatedPeCurYr;
    }

    @Column(name = "company_daily_price_book")
    public BigDecimal getCompanyDailyPriceBook() {
        return companyDailyPriceBook;
    }
    public void setCompanyDailyPriceBook(BigDecimal companyDailyPriceBook) {
        this.companyDailyPriceBook = companyDailyPriceBook;
    }

    @Column(name = "company_daily_price_to_sales")
    public BigDecimal getCompanyDailyPriceToSales() {
        return companyDailyPriceToSales;
    }
    public void setCompanyDailyPriceToSales(BigDecimal companyDailyPriceToSales) {
        this.companyDailyPriceToSales = companyDailyPriceToSales;
    }

    @Column(name = "company_daily_dividend_yield")
    public BigDecimal getCompanyDailyDividendYield() {
        return companyDailyDividendYield;
    }
    public void setCompanyDailyDividendYield(BigDecimal companyDailyDividendYield) {
        this.companyDailyDividendYield = companyDailyDividendYield;
    }

    @Column(name = "company_sector_name_b")
    public String getCompanySectorNameB() {
        return companySectorNameB;
    }
    public void setCompanySectorNameB(String companySectorNameB) {
        this.companySectorNameB = companySectorNameB;
    }

    @Column(name = "company_industry_name_b")
    public String getCompanyIndustryNameB() {
        return companyIndustryNameB;
    }
    public void setCompanyIndustryNameB(String companyIndustryNameB) {
        this.companyIndustryNameB = companyIndustryNameB;
    }

    @Column(name = "company_sub_industry_name_b")
    public String getCompanySubIndustryNameB() {
        return companySubIndustryNameB;
    }
    public void setCompanySubIndustryNameB(String companySubIndustryNameB) {
        this.companySubIndustryNameB = companySubIndustryNameB;
    }

    @Column(name = "company_daily_DS199")
    public String getCompanyDailyDS199() {
        return companyDailyDS199;
    }
    public void setCompanyDailyDS199(String companyDailyDS199) {
        this.companyDailyDS199 = companyDailyDS199;
    }

    @Column(name = "company_daily_DS201")
    public String getCompanyDailyDS201() {
        return companyDailyDS201;
    }
    public void setCompanyDailyDS201(String companyDailyDS201) {
        this.companyDailyDS201 = companyDailyDS201;
    }

    @Column(name = "company_daily_52week_low")
    public BigDecimal getCompanyDaily52WeekLow() {
        return companyDaily52WeekLow;
    }
    public void setCompanyDaily52WeekLow(BigDecimal companyDaily52WeekLow) {
        this.companyDaily52WeekLow = companyDaily52WeekLow;
    }

    @Column(name = "company_daily_52week_high")
    public BigDecimal getCompanyDaily52WeekHigh() {
        return companyDaily52WeekHigh;
    }
    public void setCompanyDaily52WeekHigh(BigDecimal companyDaily52WeekHigh) {
        this.companyDaily52WeekHigh = companyDaily52WeekHigh;
    }

    @Column(name = "company_daily_price_chge_1D")
    public BigDecimal getCompanyDailyPriceChge1D() {
        return companyDailyPriceChge1D;
    }
    public void setCompanyDailyPriceChge1D(BigDecimal companyDailyPriceChge1D) {
        this.companyDailyPriceChge1D = companyDailyPriceChge1D;
    }

    @Column(name = "company_daily_pct_chge_1D")
    public BigDecimal getCompanyDailyPctChge1D() {
        return companyDailyPctChge1D;
    }
    public void setCompanyDailyPctChge1D(BigDecimal companyDailyPctChge1D) {
        this.companyDailyPctChge1D = companyDailyPctChge1D;
    }

    @Column(name = "company_daily_total_return_1year")
    public BigDecimal getCompanyDailyTotalReturn1year() {
        return companyDailyTotalReturn1year;
    }
    public void setCompanyDailyTotalReturn1year(BigDecimal companyDailyTotalReturn1year) {
        this.companyDailyTotalReturn1year = companyDailyTotalReturn1year;
    }

    @Column(name = "company_daily_total_return_YTD")
    public BigDecimal getCompanyDailyTotalReturnYTD() {
        return companyDailyTotalReturnYTD;
    }
    public void setCompanyDailyTotalReturnYTD(BigDecimal companyDailyTotalReturnYTD) {
        this.companyDailyTotalReturnYTD = companyDailyTotalReturnYTD;
    }

    @Column(name = "company_daily_market_cap_rank")
    public BigDecimal getCompanyDailyMarketCapRank() {
        return companyDailyMarketCapRank;
    }
    public void setCompanyDailyMarketCapRank(BigDecimal companyDailyMarketCapRank) {
        this.companyDailyMarketCapRank = companyDailyMarketCapRank;
    }

    @Column(name = "company_daily_last_earning_date")
    public Date getCompanyDailyLastEarningDate() {
        return companyDailyLastEarningDate;
    }
    public void setCompanyDailyLastEarningDate(Date companyDailyLastEarningDate) {
        this.companyDailyLastEarningDate = companyDailyLastEarningDate;
    }

    @Column(name = "company_daily_next_earning_date")
    public Date getCompanyDailyNextEarningDate() {
        return companyDailyNextEarningDate;
    }
    public void setCompanyDailyNextEarningDate(Date companyDailyNextEarningDate) {
        this.companyDailyNextEarningDate = companyDailyNextEarningDate;
    }

    @Column(name = "company_daily_latest_anncmt_period")
    public String getCompanyDailyLatestAnncmtPeriod() {
        return companyDailyLatestAnncmtPeriod;
    }
    public void setCompanyDailyLatestAnncmtPeriod(String companyDailyLatestAnncmtPeriod) {
        this.companyDailyLatestAnncmtPeriod = companyDailyLatestAnncmtPeriod;
    }

    @Column(name = "company_daily_shares")
    public int getCompanyDailyShares() {
        return companyDailyShares;
    }
    public void setCompanyDailyShares(int companyDailyShares) {
        this.companyDailyShares = companyDailyShares;
    }

    @Embeddable
    public static class CompanyDailyDataBKey implements Serializable{
        private String companyTickerB;
        private Date companyDailyDate;

        public CompanyDailyDataBKey(){}

        public CompanyDailyDataBKey(String companyTickerB, Date companyDailyDate){
            this.companyTickerB = companyTickerB;
            this.companyDailyDate = companyDailyDate;
        }

        @Column(name = "company_ticker_b")
        public String getCompanyTickerB() {
            return companyTickerB;
        }
        public void setCompanyTickerB(String companyTickerB) {
            this.companyTickerB = companyTickerB;
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
