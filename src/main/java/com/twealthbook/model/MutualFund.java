package com.twealthbook.model;

import com.twealthbook.controller.RestApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "mutual_fund")
public class MutualFund implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(MutualFund.class);

    private MutualFundKey mutualFundKey;
    private String isinDivReinvestment;
    private String schemeName;
    private BigDecimal nav;
    private BigDecimal repurchasePrice;
    private BigDecimal salePrice;

    public MutualFund(){}
    public MutualFund(List<String> stingList){
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        java.sql.Date date = null;
        Integer code;
        String isin;
        try {
            //date = new java.sql.Date(format.parse(stingList.get(7)).getTime());
            date = new java.sql.Date(format.parse(stingList.get(5)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        code = Integer.parseInt(stingList.get(0));
        isin = stingList.get(1);
        this.mutualFundKey = new MutualFundKey(date,code,isin);
        this.isinDivReinvestment = stingList.get(2);
        this.schemeName = stingList.get(3);
        try {
            this.nav = stingList.get(4)!=null && !stingList.get(4).isEmpty() ? new BigDecimal(stingList.get(4)) : new BigDecimal(0);
        } catch (NumberFormatException e) {
            logger.debug(String.format("/MutualFund(List)/%s/, %s", code, isin));
            this.nav = new BigDecimal(0);
        }
//        try {
//            this.repurchasePrice = stingList.get(5)!=null && !stingList.get(5).isEmpty() ? new BigDecimal(stingList.get(5)) : new BigDecimal(0);
//            this.salePrice = stingList.get(6)!=null && !stingList.get(6).isEmpty() ? new BigDecimal(stingList.get(6)) : new BigDecimal(0);
//        } catch (NumberFormatException e) {
//            this.repurchasePrice = this.nav;
//            this.salePrice = this.nav;
//        }

    }

    @EmbeddedId
    public MutualFundKey getMutualFundKey() {
        return mutualFundKey;
    }
    public void setMutualFundKey(MutualFundKey mutualFundKey) {
        this.mutualFundKey = mutualFundKey;
    }

    @Column(name = "mf_isin_div_reinvestment")
    public String getIsinDivReinvestment() {
        return isinDivReinvestment;
    }
    public void setIsinDivReinvestment(String isinDivReinvestment) {
        this.isinDivReinvestment = isinDivReinvestment;
    }

    @Column(name = "mf_scheme_name")
    public String getSchemeName() {
        return schemeName;
    }
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    @Column(name = "mf_nav")
    public BigDecimal getNav() {
        return nav;
    }
    public void setNav(BigDecimal nav) {
        this.nav = nav;
    }

    @Column(name = "mf_repurchase_price")
    public BigDecimal getRepurchasePrice() {
        return repurchasePrice;
    }
    public void setRepurchasePrice(BigDecimal repurchasePrice) {
        this.repurchasePrice = repurchasePrice;
    }

    @Column(name = "mf_sale_price")
    public BigDecimal getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @Embeddable
    public static class MutualFundKey implements Serializable {

        private Integer schemeCode;
        private String isinDivPayoutOrIsinGrowth;
        private java.sql.Date mutualFundDate;

        public MutualFundKey(){}
        public MutualFundKey(java.sql.Date mutualFundDate, Integer schemeCode, String isinDivPayoutOrIsinGrowth){
            this.schemeCode = schemeCode;
            this.isinDivPayoutOrIsinGrowth = isinDivPayoutOrIsinGrowth;
            this.mutualFundDate = mutualFundDate;
        }

        @Column(name = "mf_scheme_code")
        public Integer getSchemeCode() {
            return schemeCode;
        }
        public void setSchemeCode(Integer schemeCode) {
            this.schemeCode = schemeCode;
        }

        @Column(name = "mf_isin_div_payout_or_isin_growth")
        public String getIsinDivPayoutOrIsinGrowth() {
            return isinDivPayoutOrIsinGrowth;
        }
        public void setIsinDivPayoutOrIsinGrowth(String isinDivPayoutOrIsinGrowth) {
            this.isinDivPayoutOrIsinGrowth = isinDivPayoutOrIsinGrowth;
        }

        @Column(name = "mf_date")
        public java.sql.Date getMutualFundDate() {
            return mutualFundDate;
        }
        public void setMutualFundDate(java.sql.Date mutualFundDate) {
            this.mutualFundDate = mutualFundDate;
        }
    }

}
