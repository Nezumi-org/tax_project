package com.ns.kakeibo.service;

public interface DeductionCalculatable {
    double calculateTotalDeductibleAmount(Integer taxPayerId, Integer yearName);
}
