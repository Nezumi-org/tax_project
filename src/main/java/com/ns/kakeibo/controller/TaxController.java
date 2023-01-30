package com.ns.kakeibo.controller;
import com.ns.kakeibo.domain.MedicalItem;
import com.ns.kakeibo.domain.TaxPayer;
import com.ns.kakeibo.domain.TaxesPaidItem;
import com.ns.kakeibo.service.ImportService;
import com.ns.kakeibo.service.MedicalItemService;
import com.ns.kakeibo.service.TaxPayerService;
import com.ns.kakeibo.service.TaxesPaidItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;
@Controller
@RequestMapping("/tax")
public class TaxController {
    @Autowired
    private final TaxPayerService taxPayerService;
    @Autowired
    private final MedicalItemService medicalItemService;
    @Autowired
    private final TaxesPaidItemService taxesPaidItemService;
    @Autowired
    private final ImportService importService;

    public TaxController(TaxPayerService taxPayerService, MedicalItemService medicalItemService, TaxesPaidItemService taxesPaidItemService, ImportService importService) {
        this.taxPayerService = taxPayerService;
        this.medicalItemService = medicalItemService;
        this.taxesPaidItemService = taxesPaidItemService;
        this.importService = importService;

        //Import data from file into db
        importService.initializeData();
    }


     @RequestMapping(value="/report/{id}/{year}", method=RequestMethod.GET)
     public String displayByTaxPayerByYear(@PathVariable String id, @PathVariable String year, Model model) throws IOException {
        //if (StringUtils.isEmpty(year) || StringUtils.isEmpty(id)){// s security will address this case
         //   return "redirect:/tax/home";
        //}
        System.out.println(" Year "+ year + " TaxPayerId "+ id);
        TaxPayer taxPayer = taxPayerService.findById(Integer.valueOf(id));
        if(taxPayer == null){
            return "redirect:/error";
        }
        List<MedicalItem> medicalItems = medicalItemService.findByTaxPayerIdAndYear(Integer.valueOf(id),Integer.valueOf(year));
         double medicalTotal = (medicalItems.size() > 0) ?
                 medicalItemService.calculateTotalAmount(Integer.valueOf(id), Integer.valueOf(year)) : 0;
         double medicalDeductibleTotal = (medicalItems.size() > 0) ?
             medicalItemService.calculateTotalDeductibleAmount(Integer.valueOf(id), Integer.valueOf(year)) : 0;

        model.addAttribute("taxPayer", taxPayer);
        model.addAttribute("medicalTitle", MedicalItem.description);
        model.addAttribute("medicalItems", medicalItems);
        model.addAttribute("medicalTotal", medicalTotal);
        model.addAttribute("medicalDeductibleTotal", medicalDeductibleTotal);

         List<TaxesPaidItem> taxesPaidItems = taxesPaidItemService.findByTaxPayerIdAndYear(Integer.valueOf(id),Integer.valueOf(year));
         double taxesPaidTotal = (taxesPaidItems.size() > 0) ?
                 taxesPaidItemService.calculateTotalAmount(Integer.valueOf(id), Integer.valueOf(year)) : 0;
         double taxesPaidDeductibleTotal = (taxesPaidItems.size() > 0) ?
                 taxesPaidItemService.calculateTotalDeductibleAmount(Integer.valueOf(id), Integer.valueOf(year)) : 0;
         model.addAttribute("taxesPaidTitle", TaxesPaidItem.description);
         model.addAttribute("taxesPaidItems", taxesPaidItems);
         model.addAttribute("taxesPaidTotal", taxesPaidTotal);
         model.addAttribute("taxesPaidDeductibleTotal", taxesPaidDeductibleTotal);
        return "report";
    }

    @RequestMapping(value="/reports", method=RequestMethod.GET)
    public String displayCurrentYears( Model model) {
        Collection<TaxPayer> taxPayers = (Collection<TaxPayer>) taxPayerService.lookup();
        for ( TaxPayer taxPayer: taxPayers){
            Integer id = taxPayer.getId();
            List<MedicalItem> medicalItems = taxPayer.getMedicalItems();
            System.out.println( " TaxPayerId   "+ id);
            medicalItems.forEach(r->System.out.println(r.getInstitutionName()));
        }
        model.addAttribute("taxPayers", taxPayers);
        return "reports_allTaxPayers";
    }
    @GetMapping("/home") // /tax/home
    public String tax(@RequestParam(name="name", required=false, defaultValue="Itemized Deductions Helper") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }
    @GetMapping("/error") // /tax/error
    public String tax( Model model) {
        return  "notFound";
    }

}
