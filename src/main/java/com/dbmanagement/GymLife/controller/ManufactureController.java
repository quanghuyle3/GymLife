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
    public String retrieveAllManifactures(Model theModel, String successMessage, String successfulUpdate,
            String successfulDelete) {

        List<Manufacture> allManufacture = appDAO.retrieveAllManufacture();

        theModel.addAttribute("manufactures", allManufacture);
        if (successMessage != null) {
            theModel.addAttribute("successMessage", successMessage);
        } else if (successfulUpdate != null) {
            theModel.addAttribute("successfulUpdate", successfulUpdate);
        } else if (successfulDelete != null) {
            theModel.addAttribute("successfulDelete", successfulDelete);
        }

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
                || memberDAO.findMemberByBankAccount(bankAccount) != null)) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("errorMessage",
                    "Error: Bank account number already associated with an existing manufacture/member. Please try again.");

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
        String successMessage = "Successfully added an new manufacture log - ID: " + manufacture.getId();
        return retrieveAllManifactures(theModel, successMessage, null, null);
    }

    @GetMapping("update")
    public String updateAManufacture(@RequestParam("manufactureId") int manufactureId, Model theModel) {

        // retrieve the Manufacture need to be updated
        Manufacture theManufacture = appDAO.findManufactureById(manufactureId);

        // convert manufacture to webManufacture to display in template
        WebManufacture webManufacture = new WebManufacture(theManufacture);

        // add to model attribute to display in view
        theModel.addAttribute("webManufacture", webManufacture);

        return "update/manufacture-update";
    }

    @PostMapping("/update/process")
    public String processUpdate(@Valid @ModelAttribute("webManufacture") WebManufacture theWebManufacture,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            System.out.println(theBindingResult.getAllErrors());

            return "update/manufacture-update";
        }

        // retrieve the manufacture
        Manufacture manufacture = appDAO.findManufactureById(theWebManufacture.getId());
        if (manufacture == null) {
            String successfulDelete = "Error: This manufacture isn't existed in database anymore. Someone already deleted it!";
            return retrieveAllManifactures(theModel, null, null, successfulDelete);
        }

        // check if bank number is already associated with another manu/member
        String bankAccountNumber = theWebManufacture.getBankAccountNumber();
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(bankAccountNumber);
        Manufacture associatedManufacture = appDAO.findManufactureByBankAccount(bankAccount);
        if (bankAccount != null && ((associatedManufacture != null && associatedManufacture != manufacture)
                || memberDAO.findMemberByBankAccount(bankAccount) != null)) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("errorMessage",
                    "Error: Bank account number already associated with an existing manufacture/member. Please try again.");

            return "update/manufacture-update";
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
            return "update/manufacture-update";
        }
        bankAccount.setRoutineNumber(routineNumber);

        // update new info
        manufacture.setName(theWebManufacture.getName());
        manufacture.setAddress(theWebManufacture.getAddress());
        // if phone/email is null, they'll be set to null
        manufacture.setPhoneNumber(theWebManufacture.getPhoneNumber());
        manufacture.setEmail(theWebManufacture.getEmail());
        manufacture.setBankAccount(bankAccount);

        appDAO.update(manufacture);

        // add message
        String successfulUpdate = "Successfully updated a manufacture - ID: " + manufacture.getId();
        return retrieveAllManifactures(theModel, null, successfulUpdate, null);
    }

    @GetMapping("delete")
    public String deleteAManufacture(@RequestParam("manufactureId") int manufactureId, Model theModel) {

        Manufacture manufacture = appDAO.findManufactureById(manufactureId);
        manufacture.getBankAccount().setManufacture(null);

        appDAO.deleteManufactureById(manufactureId);

        String successfulDelete = "Successfully deleted manufacture ID: " + manufactureId;
        return retrieveAllManifactures(theModel, null, null, successfulDelete);
    }

}
