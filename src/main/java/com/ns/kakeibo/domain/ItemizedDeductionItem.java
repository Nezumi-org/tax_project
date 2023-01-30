package com.ns.kakeibo.domain;

import javax.persistence.*;

/**
 * ItemizedDeductionItem encapsulates a deduction item record for Itemized Deductions
 */
@MappedSuperclass
public class ItemizedDeductionItem {
    @Id
    @GeneratedValue
    private Integer itemId;
    private Integer yearName;
    private String institutionName;
    private double  amount;

    @ManyToOne
    private
    TaxPayer taxPayer;

    public ItemizedDeductionItem(TaxPayer taxPayer, Integer yearName, String institutionName, double amount) {
        this.taxPayer = taxPayer;
        this.institutionName = institutionName;
        this.amount = amount;
        this.yearName = yearName;
    }
    public ItemizedDeductionItem() {
    }
    public Integer getItemId() {
        return itemId;
    }
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public double  getAmount() {
        return amount;
    }

    public Integer getYearName() {
        return yearName;
    }

    public void setAmount(double  amount) {
        this.amount = amount;
    }

    public TaxPayer getTaxPayer() {
        return taxPayer;
    }

    public void setTaxPayer(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    public void setYearName(Integer yearName) {
        this.yearName = yearName;
    }

    @Override
    public String toString() {
        return "DeductionItem{" +
                ", year=" + yearName +
                ", institutionName='" + institutionName + '\'' +
                ", amount=" + amount +
                ", taxPayer='" + taxPayer + '\'' +
                '}';
    }
}
