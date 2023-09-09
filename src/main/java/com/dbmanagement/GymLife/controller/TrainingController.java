package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.Training;
import com.dbmanagement.GymLife.webObject.WebTraining;

import org.springframework.web.bind.annotation.InitBinder;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/trainings")
public class TrainingController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public TrainingController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
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
    public String retrieveAllTrainings(Model theModel) {

        List<Training> allTraining = appDAO.retrieveAllTraining();

        theModel.addAttribute("trainings", allTraining);

        return "retrieve/trainings-retrieve";
    }

    @GetMapping("/add-training")
    public String showTrainingForm(Model theModel) {
        WebTraining webTraining = new WebTraining();

        webTraining.setPreTrainerList(memberDAO.retrieveAllTrainers());
        webTraining.setPreStudentList(memberDAO.retrieveAllGymmers());

        theModel.addAttribute("webTraining", webTraining);

        return "add/training-form";
    }

    @PostMapping("/training-process")
    public String processTraining(@Valid @ModelAttribute("webTraining") WebTraining theWebTraining,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {

            // set the list of all trainers and students again before returning the view
            theWebTraining.setPreTrainerList(memberDAO.retrieveAllTrainers());
            theWebTraining.setPreStudentList(memberDAO.retrieveAllGymmers());

            return "add/training-form";
        }

        Training training = new Training();
        training.setTrainerId(memberDAO.findMemberById(theWebTraining.getTrainerId()));
        training.setStudentId(memberDAO.findMemberById(theWebTraining.getStudentId()));
        training.setDateStart(theWebTraining.getDateStart());
        if (theWebTraining.getDateEnd() != null) {
            training.setDateEnd(theWebTraining.getDateEnd());
        }

        // save the shift
        // check for violation constraint
        try {
            appDAO.save(training);
        } catch (JpaSystemException e) {
            theModel.addAttribute("violationConstraint", "Error: There is conflict in time/date. Please try again.");

            // set the list of all trainers and students again before returning the view
            theWebTraining.setPreTrainerList(memberDAO.retrieveAllTrainers());
            theWebTraining.setPreStudentList(memberDAO.retrieveAllGymmers());
            return "add/training-form";
        }

        // add message
        theModel.addAttribute("successMessage", "Successfully added a training log - ID: " + training.getId());

        // retrieve all work schedule before returning to the retrieve page
        List<Training> allTraining = appDAO.retrieveAllTraining();
        theModel.addAttribute("trainings", allTraining);

        return "retrieve/trainings-retrieve";
    }

    @GetMapping("delete")
    public String deleteATrainingLog(@RequestParam("trainingId") int trainingId, Model theModel) {

        appDAO.deleteTrainingById(trainingId);

        // retrieve all training before returning to the retrieve page
        List<Training> allTraining = appDAO.retrieveAllTraining();
        theModel.addAttribute("trainings", allTraining);

        String message = "Successfully deleted schedule ID: " + trainingId;
        theModel.addAttribute("successfulDelete", message);

        return "retrieve/trainings-retrieve";
    }

}
