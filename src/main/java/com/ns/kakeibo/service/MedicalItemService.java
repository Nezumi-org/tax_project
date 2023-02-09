package com.ns.kakeibo.service;

import com.ns.kakeibo.domain.Calculatable;
import com.ns.kakeibo.domain.MedicalItem;
import com.ns.kakeibo.domain.TaxPayer;
import com.ns.kakeibo.repo.MedicalItemRepository;
import com.ns.kakeibo.repo.TaxPayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalItemService implements DeductionCalculatable{
    private MedicalItemRepository medicalItemRepository;
    private TaxPayerRepository taxPayerRepository;
    @Autowired
    public MedicalItemService(MedicalItemRepository medicalItemRepository, TaxPayerRepository taxPayerRepository) {
        this.medicalItemRepository = medicalItemRepository;
        this.taxPayerRepository = taxPayerRepository;
    }
    public MedicalItem createMedicalItem(Integer taxPayerId,  String year, String institutionName, String amount){
        TaxPayer taxPayer = taxPayerRepository.findById(taxPayerId).orElseThrow(() ->
        new RuntimeException("TaxPayer does not exist. Id: " + taxPayerId));
        MedicalItem medicalItem = new MedicalItem(taxPayer, Integer.parseInt(year), institutionName, Double.parseDouble(amount));
        return medicalItemRepository.save(medicalItem);
    }
    public void deleteMedicalItem(Integer taxPayerId,  Integer itemId) {
        TaxPayer taxPayer = taxPayerRepository.findById(taxPayerId).orElseThrow(() ->
               new RuntimeException("TaxPayer does not exist. Id: " + taxPayerId));
        medicalItemRepository.deleteById(itemId);
        taxPayer.deleteMedicalItem(itemId);
    }
    /**
     *return amount adjusted based on the Medical and Dental Expenses formula
     * @param taxPayerId
     * @param yearName
     * @return
     */
    @Override
    public double calculateTotalDeductibleAmount(Integer taxPayerId, Integer yearName){
        double sum = calculateTotalAmount(taxPayerId, yearName);
        return sum - (sum * 0.075);
    }
    public double calculateTotalAmount(Integer taxPayerId, Integer yearName){
        List<MedicalItem> records = medicalItemRepository.findByTaxPayerIdAndYearName(taxPayerId, yearName);
        return records.stream().mapToDouble(Calculatable::getAmount).sum();
    }
    public List<MedicalItem> findByTaxPayerIdAndYear(Integer taxPayerId, Integer yearName){
        return medicalItemRepository.findByTaxPayerIdAndYearName(taxPayerId, yearName);
    }

}
