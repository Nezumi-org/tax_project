package com.ns.kakeibo.trial;



import com.ns.kakeibo.domain.Calculatable;
import com.ns.kakeibo.domain.TaxPayer;
import com.ns.kakeibo.trial.TaxCalculatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaxBox<T extends Calculatable> implements TaxCalculatable, Iterator {
    private List<T> items;
    TaxPayer taxpayer;
    String code;
    String description;
       public TaxBox(){
        this.items = new ArrayList<>();

    }
    public void addItem(T t){
        this.items.add(t);
    }

    public List<T> getItems() {
        return items;
    }

    public double getTotalAmount(){
        return this.items.stream().mapToDouble(Calculatable::getAmount).sum();
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
public void print(){
    Iterator<T> it = items.iterator();
    while(it.hasNext()){
        T next = it.next();
        System.out.println("next....... " + next);
    }
}
    @Override
    public String toString() {
        return "Box{" +
                "items=" + items +
                '}';
    }
    @Override
    public double getTotalAmountForTaxForm() {
        return this.getTotalAmount();
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}