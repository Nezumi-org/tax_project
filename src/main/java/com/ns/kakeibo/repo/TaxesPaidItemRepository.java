package com.ns.kakeibo.repo;

import com.ns.kakeibo.domain.MedicalItem;
import com.ns.kakeibo.domain.TaxesPaidItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaxesPaidItemRepository extends CrudRepository<TaxesPaidItem, Integer> {
    List<TaxesPaidItem> findByTaxPayerId(Integer id);
    List<TaxesPaidItem> findByTaxPayerIdAndYearName(Integer id, Integer yearName);
}
