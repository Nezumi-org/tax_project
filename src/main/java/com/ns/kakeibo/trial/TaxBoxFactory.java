package com.ns.kakeibo.trial;

import com.ns.kakeibo.domain.MedicalItem;
import com.ns.kakeibo.domain.TaxPayer;
import com.ns.kakeibo.domain.TaxesPaidItem;
import com.ns.kakeibo.trial.MedicalItemTaxBox;
import com.ns.kakeibo.trial.TaxBox;
import com.ns.kakeibo.trial.TaxesPaidItemTaxBox;

import static com.ns.kakeibo.domain.FilingStatus.marriedFilingJointly;

public class TaxBoxFactory {

    public static <T> TaxBox create(String code){
        switch (code) {
            case "MedicalBox":
                return new MedicalItemTaxBox();
            case "TaxesPaidBox":
                return new TaxesPaidItemTaxBox();
            default:
                throw new IllegalStateException("Unexpected value: " + code);
        }
    }
    public static void main(String args[]) {


    }


}
