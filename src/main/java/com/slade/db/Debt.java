package com.slade.db;

import java.math.BigDecimal;

public class Debt {

    private String creditor;
    private BigDecimal totalAmount;
    private BigDecimal repaymentCurr;
    private BigDecimal repaymentNext;
    private String repaymentDate;

    public Debt(String creditor, BigDecimal totalAmount, BigDecimal repaymentCurr, BigDecimal repaymentNext, String repaymentDate) {
        this.creditor = creditor;
        this.totalAmount = totalAmount;
        this.repaymentCurr = repaymentCurr;
        this.repaymentNext = repaymentNext;
        this.repaymentDate = repaymentDate;
    }

    public String getCreditor() {
        return creditor;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getRepaymentCurr() {
        return repaymentCurr;
    }

    public BigDecimal getRepaymentNext() {
        return repaymentNext;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "creditor='" + creditor + '\'' +
                ", totalAmount=" + totalAmount +
                ", repaymentCurr=" + repaymentCurr +
                ", repaymentNext=" + repaymentNext +
                ", repaymentDate='" + repaymentDate + '\'' +
                '}';
    }

}
