package com.ns.kakeibo.domain;

import javax.persistence.*;
@Entity
public class MedicalItem extends ItemizedDeductionItem implements Calculatable {

  public final static String code = "Medical";
  public final static String description = "Medical and Dental Expenses";

    public MedicalItem() {
    }
    public MedicalItem(TaxPayer taxPayer, Integer yearName, String institutionName, double amount) {
        super(taxPayer,  yearName, institutionName, amount);
    }

}
