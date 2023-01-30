package com.ns.kakeibo.domain;

import javax.persistence.Entity;

@Entity
public class TaxesPaidItem extends ItemizedDeductionItem implements Calculatable {
    public final static String code = "TaxesPaid";
    public final static String description =  "Taxes you paid";
    public TaxesPaidItem() { }
    public TaxesPaidItem(TaxPayer taxPayer, Integer yearName, String institutionName, double amount) {
        super(taxPayer, yearName, institutionName, amount);
    }
}
