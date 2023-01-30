package com.ns.kakeibo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.kakeibo.domain.MedicalItem;
import com.ns.kakeibo.domain.TaxesPaidItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
//TODO add CSV, make service as abstract class

/**
 * Helper class to load data into db.
 */
@Service
public class ImportService {
    private static String importFile = "initial_data.json";
    private final TaxPayerService taxPayerService;
    private final MedicalItemService medicalItemService;
    private final TaxesPaidItemService taxesPaidItemService;

    @Autowired
    public ImportService(TaxPayerService taxPayerService, MedicalItemService medicalItemService, TaxesPaidItemService taxesPaidItemService) {
        this.taxPayerService = taxPayerService;
        this.medicalItemService = medicalItemService;
        this.taxesPaidItemService = taxesPaidItemService;
    }
    private  void createAllTaxPayers() {
        taxPayerService.createTaxPayer(100, "Karen", "Max", "Married filing jointly");
        taxPayerService.createTaxPayer(200, "Joy", "Min", "Single");
    }
    public void initializeData(){
        createAllTaxPayers();
        try {
            createItemizedDeductionItems(importFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private  void createItemizedDeductionItems(String fileToImport) throws IOException {
        ItemizedDeductionItemFile.read(fileToImport).forEach(tourFromFile ->
                createItemizedDeductionItem(Integer.valueOf(tourFromFile.getTaxPayerId()), tourFromFile.getCode(),
                        tourFromFile.getYear(), tourFromFile.getInstitutionName(),tourFromFile.getAmount() )
        );
    }

    private void createItemizedDeductionItem(Integer taxPayerId, String code, String year, String institutionName, String amount) {
        switch (code) {
            case MedicalItem.code:
                medicalItemService.createMedicalItem(taxPayerId, year, institutionName, amount);
                break;
            case TaxesPaidItem.code:
                taxesPaidItemService.createTaxesPaidItem(taxPayerId, year, institutionName, amount);
                break;
            default:
                throw new IllegalStateException("Deduction Item code does not exist: " + code);
        }
    }
    /**
     * import initial tax data
     */
    private static class ItemizedDeductionItemFile {
        //fields
        String taxPayerId;
        String code;
        String year;
        String institutionName;
        String amount;

        ItemizedDeductionItemFile(Map<String, String> record) {
            this.taxPayerId= record.get("taxPayer");
            this.code = record.get("code");
            this.year = record.get("year");
            this.institutionName = record.get("institutionName");
            this.amount = record.get("amount");
        }
        //reader
        static List<ImportService.ItemizedDeductionItemFile> read(String fileToImport) throws IOException {
            List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport),
                            new TypeReference<List<Map<String, String>>>() {
                            });
            return records.stream().map(ImportService.ItemizedDeductionItemFile::new)
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
