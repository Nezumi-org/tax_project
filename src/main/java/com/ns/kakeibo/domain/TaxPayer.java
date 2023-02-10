package com.ns.kakeibo.domain;

import com.ns.kakeibo.domain.FilingStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class TaxPayer {
    @Id
    private Integer id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private FilingStatus filingStatus;

    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "tax_payer_id")
    private Set<MedicalItem> medicalItems= new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tax_payer_id")
    private Set<TaxesPaidItem> taxesPaidItems= new HashSet<>();
    
    public TaxPayer(Integer id, String firstName, String lastName, FilingStatus filingStatus) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.filingStatus = filingStatus;

    }
    public TaxPayer() { }
    public FilingStatus getFilingStatus() {
        return filingStatus;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

     public Set<MedicalItem> getMedicalItems() {
        return medicalItems;
    }
    public void addMedicalItem(MedicalItem medicalItem){
        getMedicalItems().add(medicalItem);}
    public Set<TaxesPaidItem> getTaxesPaidItems() {      return taxesPaidItems;  }
    public void addTaxesPaidItem(TaxesPaidItem taxesPaidItems){
        getTaxesPaidItems().add(taxesPaidItems);
    }
    public void deleteMedicalItem(Integer itemId) {
        Set<TaxesPaidItem> items = getTaxesPaidItems();
        for(TaxesPaidItem item: items){
            if (item.getItemId().equals(itemId)){
                items.remove(item);
                break;
            }
        }
    }
    @Override
    public String toString() {
        return "TaxPayer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", filingStatus=" + filingStatus +
                '}';
    }
}
