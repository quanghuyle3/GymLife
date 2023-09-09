package com.dbmanagement.GymLife.controller;

import java.util.List;

import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Manufacture;
import com.dbmanagement.GymLife.webObject.WebManufacture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/manufactures")
public class ManufactureController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public ManufactureController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {

        // remove whitespace - leading and trailing
        // true: trim to null if only whitespace
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        // pass in the customized trimmer editor object to trim all string all string
        // from web
        // request
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/retrieve")
    public String retrieveAllManifactures(Model theModel) {

        List<Manufacture> allManufacture = appDAO.retrieveAllManufacture();

        theModel.addAttribute("manufactures", allManufacture);

        return "retrieve/manufactures-retrieve";
    }

    @GetMapping("/add-manufacture")
    public String showManufactureForm(Model theModel) {
        WebManufacture webManufacture = new WebManufacture();

        theModel.addAttribute("webManufacture", webManufacture);

        return "add/manufacture-form";
    }

    @PostMapping("/manufacture-process")
    public String processManufacture(@Valid @ModelAttribute("webManufacture") WebManufacture theWebManufacture,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            System.out.println(theBindingResult.getAllErrors());

            return "add/manufacture-form";
        }

        // check if bank number is already associated with another manu/member
        String bankAccountNumber = theWebManufacture.getBankAccountNumber();
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(bankAccountNumber);
        if (bankAccount != null && (appDAO.findManufactureByBankAccount(bankAccount) != null
                || appDAO.findMemberByBankAccount(bankAccount) != null)) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("errorMessage",
                    "Error: Bank account number already associated with an existing manufacture/member. Please try again.");

            // set the list of membership types again before returning the view
            // theWebMember.setPreMembershipTypes(appDAO.retrieveAllMembershipTypes());
            return "add/manufacture-form";
        }

        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }

        bankAccount.setBankName(theWebManufacture.getBankName());
        bankAccount.setAccountNumber(theWebManufacture.getBankAccountNumber());
        int routineNumber = 0;
        try {
            routineNumber = Integer.parseInt(theWebManufacture.getRoutineNumber());
        } catch (Exception e) {
            theModel.addAttribute("errorMessage",
                    "Error: Bank routine number is not in corrected format. Please try again.");
            return "add/manufacture-form";
        }
        bankAccount.setRoutineNumber(routineNumber);

        // creat new manufacture
        Manufacture manufacture = new Manufacture();
        manufacture.setName(theWebManufacture.getName());
        manufacture.setAddress(theWebManufacture.getAddress());
        if (theWebManufacture.getPhoneNumber() != null) {
            manufacture.setPhoneNumber(theWebManufacture.getPhoneNumber());
        }
        if (theWebManufacture.getEmail() != null) {
            manufacture.setEmail(theWebManufacture.getEmail());
        }
        manufacture.setBankAccount(bankAccount);
        appDAO.save(manufacture);

        // add message
        theModel.addAttribute("successMessage",
                "Successfully added an new manufacture log - ID: " + manufacture.getId());

        // set the list of all manufactures again before returning the view
        List<Manufacture> allManufacture = appDAO.retrieveAllManufacture();
        theModel.addAttribute("manufactures", allManufacture);

        return "retrieve/manufactures-retrieve";
    }

    @GetMapping("delete")
    public String deleteAManufacture(@RequestParam("manufactureId") int manufactureId, Model theModel) {

        Manufacture manufacture = appDAO.findManufactureById(manufactureId);
        manufacture.getBankAccount().setManufacture(null);

        appDAO.deleteManufactureById(manufactureId);

        // retrieve all manufactures before returning to the retrieve page
        List<Manufacture> allManufacture = appDAO.retrieveAllManufacture();
        theModel.addAttribute("manufactures", allManufacture);

        String message = "Successfully deleted manufacture ID: " + manufactureId;
        theModel.addAttribute("successfulDelete", message);

        return "retrieve/manufactures-retrieve";
    }

}
