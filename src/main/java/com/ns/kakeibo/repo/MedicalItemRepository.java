package com.ns.kakeibo.repo;

import com.ns.kakeibo.domain.MedicalItem;
import org.springframework.data.jpa.repository.JpaRepository;//list
import org.springframework.data.repository.CrudRepository;//iterable

import java.util.List;

//property based query by Spring repo pattern
public interface MedicalItemRepository extends CrudRepository<MedicalItem, Integer> {
    List<MedicalItem> findByTaxPayerId(Integer id);
    List<MedicalItem> findByTaxPayerIdAndYearName(Integer id, Integer yearName);
}
