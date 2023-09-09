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
    public String retrieveAllEquipment(Model theModel) {

        List<Equipment> allEquipment = appDAO.retrieveAllEquipment();

        theModel.addAttribute("equipment", allEquipment);

        return "retrieve/equipment-retrieve";
    }

    @GetMapping("add-equipment")
    public String showEquipmentForm(Model theModel) {
        WebEquipment webEquipment = new WebEquipment();
        webEquipment.setPreTransactionList(retrieveAvailableTransactionsPaidToManufacture());
        webEquipment.setPreTargetList(retrieveTargetList());

        theModel.addAttribute("webEquipment", webEquipment);

        return "add/equipment-form";
    }

    @PostMapping("equipment-process")
    public String processTransaction(@Valid @ModelAttribute("webEquipment") WebEquipment theWebEquipment,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            theWebEquipment.setPreTransactionList(retrieveAvailableTransactionsPaidToManufacture());
            theWebEquipment.setPreTargetList(retrieveTargetList());
            System.out.println("Transaction ID: " + theWebEquipment.getTransactionId());

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
        theModel.addAttribute("successMessage",
                "Successfully added an imported equipment - ID: " + equipment.getSerials());

        // retrieve all equipment before returning to page
        List<Equipment> allEquipment = appDAO.retrieveAllEquipment();
        theModel.addAttribute("equipment", allEquipment);

        return "retrieve/equipment-retrieve";
    }

    @GetMapping("delete")
    public String deleteAnEquipment(@RequestParam("equipmentSerials") String equipmentSerials, Model theModel) {

        Equipment equipment = appDAO.findEquipmentBySerials(equipmentSerials);
        equipment.getTransactionId().setEquipment(null);

        appDAO.deleteEquipmentBySerials(equipmentSerials);

        // retrieve all equipment before returning to the retrieve page
        List<Equipment> allEquipment = appDAO.retrieveAllEquipment();
        theModel.addAttribute("equipment", allEquipment);

        String message = "Successfully deleted equipment ID: " + equipmentSerials;
        theModel.addAttribute("successfulDelete", message);

        return "retrieve/equipment-retrieve";
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
