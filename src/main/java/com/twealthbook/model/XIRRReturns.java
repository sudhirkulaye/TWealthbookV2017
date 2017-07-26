package com.twealthbook.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class XIRRReturns implements Serializable {

    private BigDecimal returnsSinceInception;
    private BigDecimal returnsSinceCurrentMonth;
    private BigDecimal returnsSinceCurrentQuarter;

    public BigDecimal getReturnsSinceInception() {
        return returnsSinceInception;
    }

    public void setReturnsSinceInception(BigDecimal returnsSinceInception) {
        this.returnsSinceInception = returnsSinceInception;
    }

    public BigDecimal getReturnsSinceCurrentMonth() {
        return returnsSinceCurrentMonth;
    }

    public void setReturnsSinceCurrentMonth(BigDecimal returnsSinceCurrentMonth) {
        this.returnsSinceCurrentMonth = returnsSinceCurrentMonth;
    }

    public BigDecimal getReturnsSinceCurrentQuarter() {
        return returnsSinceCurrentQuarter;
    }

    public void setReturnsSinceCurrentQuarter(BigDecimal returnsSinceCurrentQuarter) {
        this.returnsSinceCurrentQuarter = returnsSinceCurrentQuarter;
    }
}
