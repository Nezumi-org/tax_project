package com.ns.kakeibo.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//TODO
public class GiftItem extends ItemizedDeductionItem {

    /* Amount that is actually deducted. Will be used for the next year's carryover */
    private double deductedAmount;

    public GiftItem(TaxPayer taxPayer, Integer yearName, String institutionName, double amount, double deductedAmount) {
        super(taxPayer, yearName, institutionName, amount);
        this.deductedAmount = deductedAmount;
    }
}
