package com.ns.kakeibo.trial;
import com.ns.kakeibo.domain.TaxPayer;
import com.ns.kakeibo.domain.Calculatable;

import static com.ns.kakeibo.domain.FilingStatus.marriedFilingSeparately;

public class TaxesPaidItemTaxBox <T extends Calculatable> extends TaxBox<T> {

    final public String code = "TaxesPaidBox";
    final public String desc = "Taxes you paid";
    TaxPayer taxPayer;

    public void setTaxPayer(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public double getTotalAmountForTaxForm() {
        double totalPaid = super.getTotalAmount();
        double defaultMin = taxPayer.getFilingStatus().equals(marriedFilingSeparately) ? 5000:10000;
        return Math.min(totalPaid, defaultMin);
    }
    @Override
    public String getDescription() {
        return desc;
    }
    @Override
    public String getCode() {
        return code;
    }
}
