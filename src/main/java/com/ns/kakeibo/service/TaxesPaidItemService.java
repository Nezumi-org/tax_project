package com.ns.kakeibo.service;

import com.ns.kakeibo.domain.*;
import com.ns.kakeibo.repo.TaxPayerRepository;
import com.ns.kakeibo.repo.TaxesPaidItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.ns.kakeibo.domain.FilingStatus.marriedFilingSeparately;

@Service
public class TaxesPaidItemService implements DeductionCalculatable{
    private  TaxesPaidItemRepository taxesPaidItemRepository;
    private TaxPayerRepository taxPayerRepository;

    public TaxesPaidItemService(TaxesPaidItemRepository taxesPaidItemRepository, TaxPayerRepository taxPayerRepository) {
        this.taxesPaidItemRepository = taxesPaidItemRepository;
        this.taxPayerRepository = taxPayerRepository;
    }
    public TaxesPaidItem createTaxesPaidItem(Integer taxPayerId, String year, String institutionName, String amount){
        TaxPayer taxPayer = taxPayerRepository.findById(taxPayerId).orElseThrow(() ->
                new RuntimeException("TaxPayer does not exist. Id: " + taxPayerId));
        return taxesPaidItemRepository.save(new TaxesPaidItem(taxPayer, Integer.parseInt(year), institutionName, Double.parseDouble(amount)));
    }
    /**
     *return amount adjusted based on the Taxes you paid category's formula
     * @param taxPayerId
     * @param yearName
     * @return
     */
    @Override
    public double calculateTotalDeductibleAmount(Integer taxPayerId, Integer yearName){
        FilingStatus fillingStatus = getFilingStatus(taxPayerId);
        double sum = calculateTotalAmount(taxPayerId, yearName);
        double defaultMin = fillingStatus.equals(marriedFilingSeparately) ? 5000:10000;
        return Math.min(sum, defaultMin);
    }
    public List<TaxesPaidItem> findByTaxPayerIdAndYear(Integer taxPayerId, Integer yearName){
        return taxesPaidItemRepository.findByTaxPayerIdAndYearName(taxPayerId, yearName);
    }
    public double calculateTotalAmount(Integer taxPayerId, Integer yearName){
        List<TaxesPaidItem> records = taxesPaidItemRepository.findByTaxPayerIdAndYearName(taxPayerId, yearName);
        return records.stream().mapToDouble(Calculatable::getAmount).sum();
    }
    private FilingStatus getFilingStatus(Integer taxPayerId) {
        Optional<TaxPayer> taxPayer = taxPayerRepository.findById(taxPayerId);
        if (taxPayer.isPresent()){
            return taxPayer.get().getFilingStatus();
        }else{
            new RuntimeException("TaxPayer does not exist. Id: " + taxPayerId);
        }
        return null;
    }
}
