package com.ns.kakeibo.service;

import com.ns.kakeibo.domain.FilingStatus;
import com.ns.kakeibo.domain.TaxPayer;
import com.ns.kakeibo.repo.TaxPayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaxPayerService {
    @Autowired
    private TaxPayerRepository taxPayerRepository;
    @Autowired
    public TaxPayerService(TaxPayerRepository taxPayerRepository) {
        this.taxPayerRepository = taxPayerRepository;
    }
    /**
     *
     * @return  new or existing tax payer
     */
    public TaxPayer createTaxPayer(Integer id, String firstName, String lastName, String filingStatus){

        return taxPayerRepository.findById(id)
                .orElse(taxPayerRepository.save(new TaxPayer(id, firstName, lastName, FilingStatus.findByValue(filingStatus))));
    }
    public void save(TaxPayer taxPayer){
        taxPayerRepository.save(taxPayer);
    }
    public Iterable<TaxPayer> lookup(){
        return taxPayerRepository.findAll();
    }
    public TaxPayer findById(Integer id){
        Optional<TaxPayer> taxPayer = taxPayerRepository.findById(id);
        if (taxPayer.isPresent()){
            return taxPayer.get();
        }else{
            new RuntimeException("TaxPayer does not exist. Id: " + id);
        }
        return null;
    }
    public String findStatus(){
        Optional<TaxPayer> taxPayer = taxPayerRepository.findById(100);
        return taxPayer.get().getFilingStatus().toString();
    }
    public long total(){return taxPayerRepository.count();}
}
