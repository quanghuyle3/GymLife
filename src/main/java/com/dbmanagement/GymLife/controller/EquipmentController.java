package com.dbmanagement.GymLife.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbmanagement.GymLife.entity.Equipment;
import com.dbmanagement.GymLife.entity.Transaction;
import com.dbmanagement.GymLife.webObject.WebEquipment;

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

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public EquipmentController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
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
    public String retrieveAllEquipment(Model theModel, String successMessage, String successfulUpdate,
            String successfulDelete) {

        List<Equipment> allEquipment = appDAO.retrieveAllEquipment();

        theModel.addAttribute("equipment", allEquipment);
        if (successMessage != null) {
            theModel.addAttribute("successMessage", successMessage);
        } else if (successfulUpdate != null) {
            theModel.addAttribute("successfulUpdate", successfulUpdate);
        } else if (successfulDelete != null) {
            theModel.addAttribute("successfulDelete", successfulDelete);
        }

        return "retrieve/equipment-retrieve";
    }

    @GetMapping("/add-equipment")
    public String showEquipmentForm(Model theModel) {
        WebEquipment webEquipment = new WebEquipment();
        webEquipment.setPreTransactionList(retrieveAvailableTransactionsPaidToManufacture());
        webEquipment.setPreTargetList(retrieveTargetList());

        theModel.addAttribute("webEquipment", webEquipment);

        return "add/equipment-form";
    }

    @PostMapping("/equipment-process")
    public String processEquipment(@Valid @ModelAttribute("webEquipment") WebEquipment theWebEquipment,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            theWebEquipment.setPreTransactionList(retrieveAvailableTransactionsPaidToManufacture());
            theWebEquipment.setPreTargetList(retrieveTargetList());

            return "add/equipment-form";
        }

        Equipment equipment = new Equipment();

        equipment.setSerials(theWebEquipment.getSerials());
        equipment.setName(theWebEquipment.getName());
        equipment.setTransactionId(appDAO.findTransactionById(theWebEquipment.getTransactionId()));
        if (theWebEquipment.getTarget() != null) {
            equipment.setTarget(theWebEquipment.getTarget());
        }
        if (theWebEquipment.getDateImported() != null) {
            equipment.setDateImported(theWebEquipment.getDateImported());
        }

        // save this new equipment
        appDAO.save(equipment);

        // add message
        String successMessage = "Successfully added an imported equipment - ID: " + equipment.getSerials();
        return retrieveAllEquipment(theModel, successMessage, null, null);
    }

    @GetMapping("/update")
    public String updateAnEquipment(@RequestParam("equipmentSerials") String equipmentSerials, Model theModel) {

        // retrieve the equipment need to be updated
        Equipment theEquipment = appDAO.findEquipmentBySerials(equipmentSerials);

        // convert equipment to webEquipment to display in template
        WebEquipment webEquipment = new WebEquipment(theEquipment, retrieveAvailableTransactionsPaidToManufacture(),
                retrieveTargetList());

        // add to model attribute to display in view
        theModel.addAttribute("webEquipment", webEquipment);

        return "update/equipment-update";
    }

    @PostMapping("/update/process")
    public String processUpdate(@Valid @ModelAttribute("webEquipment") WebEquipment theWebEquipment,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            theWebEquipment.setPreTransactionList(retrieveAvailableTransactionsPaidToManufacture());
            theWebEquipment.setPreTargetList(retrieveTargetList());
            return "update/equipment-update";
        }

        // retrieve the equipment
        Equipment equipment = appDAO.findEquipmentBySerials(theWebEquipment.getSerials());
        if (equipment == null) {
            String successfulDelete = "Error: This Equipment isn't existed in database anymore. Someone already deleted it!";
            return retrieveAllEquipment(theModel, null, null, successfulDelete);
        }

        // equipment.setSerials(theWebEquipment.getSerials()); // this field won't
        // change since it it set to 'readonly' in update view
        equipment.setName(theWebEquipment.getName());
        equipment.setTransactionId(appDAO.findTransactionById(theWebEquipment.getTransactionId()));
        equipment.setTarget(theWebEquipment.getTarget());
        equipment.setDateImported(theWebEquipment.getDateImported());

        // save this new equipment
        appDAO.update(equipment);

        // add message
        String successfulUpdate = "Successfully updated an equipment - Serials: " + equipment.getSerials();
        return retrieveAllEquipment(theModel, null, successfulUpdate, null);
    }

    @GetMapping("/delete")
    public String deleteAnEquipment(@RequestParam("equipmentSerials") String equipmentSerials, Model theModel) {

        Equipment equipment = appDAO.findEquipmentBySerials(equipmentSerials);
        equipment.getTransactionId().setEquipment(null);

        appDAO.deleteEquipmentBySerials(equipmentSerials);

        // add message
        String successfulDelete = "Successfully deleted equipment ID: " + equipmentSerials;
        return retrieveAllEquipment(theModel, null, null, successfulDelete);
    }

    // filter only transactions that are not linked to any paid equipment
    public List<Transaction> retrieveAvailableTransactionsPaidToManufacture() {
        List<Transaction> transactions = appDAO.retrieveAllTransaction();

        List<Transaction> filterResult = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAccountReceive().getManufacture() != null && t.getEquipment() == null) {
                filterResult.add(t);
            }
        }

        return filterResult;
    }

    public List<String> retrieveTargetList() {
        List<String> targets = new ArrayList<>();

        targets.add("");
        targets.add("Cardio");
        targets.add("Chest");
        targets.add("Back");
        targets.add("Shoulder");
        targets.add("Legs");
        targets.add("Arms");

        return targets;
    }

}
