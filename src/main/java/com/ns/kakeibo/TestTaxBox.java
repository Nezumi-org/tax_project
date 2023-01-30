package com.ns.kakeibo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.kakeibo.domain.TaxForm;
import com.ns.kakeibo.domain.TaxPayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.ns.kakeibo.domain.FilingStatus.marriedFilingJointly;

public class TestTaxBox {
    @Value("${ec.importfile}")
    private String importFile;
    @Autowired
    //private
    public static void main(String[] args) {
      /*  TaxPayer taxPayer = new TaxPayer(300, "Taxpayer1 First","last", marriedFilingJointly);
        TaxForm taxForm = new TaxForm( taxPayer);
        System.out.println(" "+ taxForm.getTaxPayer());
        ItemizedDeductionImp itemizedDeduction =  new ItemizedDeductionImp(taxPayer);
*/
        //taxForm.setItemizedDeduction();
        /*MedicalItemTaxBox<MedicalItem> medicalItemBox2 = new MedicalItemTaxBox<>();
    //    medicalItemBox2.setTaxpayer(taxpayer);
        medicalItemBox2.addItem(new MedicalItem(2022, "Wallgreen", 20.5,taxPayer));
        medicalItemBox2.addItem(new MedicalItem(2022, "Evergreen", 10.5,taxPayer));
        System.out.println("medical gross total: "+ medicalItemBox2.getTotalAmount());
        System.out.println("medical total to enter in tax form: "+ String.format("%.2f",medicalItemBox2.getTotalAmountForTaxForm()));
        //System.out.println("medical total to enter in tax form: "+ medicalItemBox2.getTotalAmountForTaxForm());
        TaxesPaidItemTaxBox<TaxesPaidItem> taxItemBox = new TaxesPaidItemTaxBox<>();
        ((TaxesPaidItemTaxBox) taxItemBox).setTaxPayer(taxPayer);
  //      taxItemBox.setTaxpayer(taxpayer);
        taxItemBox.addItem(new TaxesPaidItem(2022, "Local tax", 2000,taxPayer));
        taxItemBox.addItem(new TaxesPaidItem(2022, "State tax", 22000,taxPayer));
        System.out.println("tax paid gross total: "+ taxItemBox.getTotalAmount());
        System.out.println("tax paid total to enter in tax form base on filing status : "+ taxItemBox.getTotalAmountForTaxForm());
        itemizedDeduction.setItemListMedical(medicalItemBox2);
        itemizedDeduction.setItemListTaxPaid(taxItemBox);

        Iterator it = itemizedDeduction.getItemListMedical().getItems().iterator();
        while(it.hasNext()){
            MedicalItem next = (MedicalItem) it.next();
            System.out.println("next....... " + next.getInstitutionName() +next.getAmount());
        }*/
    }//end main
    /*private void createTours(String fileToImport) throws IOException {
        TestTaxBox.TourFromFile.read(fileToImport).forEach(tourFromFile ->
                createTour(tourFromFile.getCode(),
                        tourFromFile.getYear(), tourFromFile.getAmount(), tourFromFile.getInstitutionName(), taxp)
        );
    }
    private void createTour(String code, String year, String amount, String institutionName, String linkToReceipt) {
        switch (code) {
            case "MedicalBox":
                itemListMedical.addItem(new MedicalItem(Integer.parseInt(year), institutionName, Double.parseDouble(amount), linkToReceipt));
                break;
            case "TaxesPaidBox":
                itemListTaxPaid.addItem(new TaxesPaidItem(Integer.parseInt(year), institutionName, Double.parseDouble(amount), linkToReceipt));
                break;
        }
        //  taxBoxes.add(taxboxm);
        // taxBoxes.add(taxboxt);
    }
*/
    private static class TourFromFile {
        //fields
        String taxPayerId;
        String code;
        String year;
        String institutionName;
        String amount;

        TourFromFile(Map<String, String> record) {
            this.taxPayerId= record.get("taxPayer");
            this.code = record.get("code");
            this.year = record.get("year");
            this.institutionName = record.get("institutionName");
            this.amount = record.get("amount");
        }
        //reader
        static List<TestTaxBox.TourFromFile> read(String fileToImport) throws IOException {
            List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport),
                            new TypeReference<List<Map<String, String>>>() {
                            });
            return records.stream().map(TestTaxBox.TourFromFile::new)
                    .collect(Collectors.toList());
        }
        public String getTaxPayerId() {
            return taxPayerId;
        }
        public String getCode() {
            return code;
        }
        public String getYear() {
            return year;
        }
        public String getInstitutionName() {
            return institutionName;
        }
        public String getAmount() {
            return amount;
        }

    }
}
