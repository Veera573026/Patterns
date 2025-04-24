package com.MyProject.DogManagementSystem.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.MyProject.DogManagementSystem.Models.Dog;
import com.MyProject.DogManagementSystem.Models.Trainer;
import com.MyProject.DogManagementSystem.repository.DogRepository;
import com.MyProject.DogManagementSystem.repository.TrainerRepository;

@Controller
public class DogController {

    @Autowired
    DogRepository dogRepo;

    @Autowired
    TrainerRepository trainerRepo;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/dogHome");
    }

    @RequestMapping("/dogHome")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("addNewDog");
        mv.addObject("trainers", trainerRepo.findAll());
        return mv;
    }

    @RequestMapping("/addNewDog")
    public ModelAndView addNewDog(Dog dog, @RequestParam("trainerId") int id) {
        Trainer trainer = trainerRepo.findById(id).orElse(new Trainer());
        dog.setTrainer(trainer);
        dogRepo.save(dog);
        return new ModelAndView("redirect:/dogHome");
    }

    @RequestMapping("/addTrainer")
    public ModelAndView addTrainer() {
        return new ModelAndView("addNewTrainer");
    }

    @RequestMapping("/trainerAdded")
    public ModelAndView addNewTrainer(Trainer trainer) {
        trainerRepo.save(trainer);
        return new ModelAndView("redirect:/dogHome");
    }

    @RequestMapping("/viewModifyDelete")
    public ModelAndView viewDogs() {
        ModelAndView mv = new ModelAndView("viewDogs");
        mv.addObject("dogs", dogRepo.findAll());
        return mv;
    }

    @RequestMapping("/editDog")
    public ModelAndView editDog(Dog dog) {
        dogRepo.save(dog);
        return new ModelAndView("redirect:/viewModifyDelete");
    }

    @RequestMapping("/deleteDog")
    public ModelAndView deleteDog(@RequestParam("id") int id) {
        Dog dog = dogRepo.findById(id).orElse(null);
        if (dog != null) dogRepo.delete(dog);
        return new ModelAndView("redirect:/viewModifyDelete");
    }

    @RequestMapping("/search")
    public ModelAndView searchById(@RequestParam("id") int id) {
        ModelAndView mv = new ModelAndView("searchResults");
        Dog dogFound = dogRepo.findById(id).orElse(new Dog());
        mv.addObject("dog", dogFound);
        return mv;
    }
}