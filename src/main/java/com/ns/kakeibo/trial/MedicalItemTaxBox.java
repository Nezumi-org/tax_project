package com.ns.kakeibo.trial;

import com.ns.kakeibo.domain.Calculatable;

public class MedicalItemTaxBox<T extends Calculatable> extends TaxBox<T> {

    final public String code = "MedicalBox";
    final public String desc = "Medical and Dental Expenses";
    public MedicalItemTaxBox() {}
    @Override
    public double getTotalAmountForTaxForm() {
       // items.iterator()
        return super.getTotalAmount() - (super.getTotalAmount() * 0.075

        );
    }

    @Override
    public String getDescription() {
        return desc;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
