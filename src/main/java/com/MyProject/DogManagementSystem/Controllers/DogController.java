package com.MyProject.DogManagementSystem.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.MyProject.DogManagementSystem.Models.Dog;
import com.MyProject.DogManagementSystem.Models.Trainer;
import com.MyProject.DogManagementSystem.repository.DogRepository;
import com.MyProject.DogManagementSystem.repository.TrainerRepository;

@Controller
public class DogController {

    @Autowired
    private DogRepository dogRepo;

    @Autowired
    private TrainerRepository trainerRepo;

    @GetMapping("/dogHome")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/add")
    public ModelAndView addDogForm() {
        ModelAndView mv = new ModelAndView("addNewDog");
        mv.addObject("trainers", trainerRepo.findAll());
        mv.addObject("dog", new Dog()); // important for form binding
        return mv;
    }

    @PostMapping("/addNewDog")
    public ModelAndView addNewDog(@ModelAttribute Dog dog, @RequestParam("trainerId") int id) {
        Trainer trainer = trainerRepo.findById(id).orElse(null);
        if (trainer != null) {
            dog.setTrainer(trainer);
            dogRepo.save(dog);
        }
        return new ModelAndView("redirect:/dogHome");
    }

    @GetMapping("/viewModifyDelete")
    public ModelAndView viewDogs() {
        ModelAndView mv = new ModelAndView("viewDogs");
        mv.addObject("dogs", dogRepo.findAll());
        return mv;
    }

    @PostMapping("/editDog")
    public ModelAndView editDog(@ModelAttribute Dog dog) {
        dogRepo.save(dog);
        return new ModelAndView("redirect:/viewModifyDelete");
    }

    @GetMapping("/deleteDog")
    public ModelAndView deleteDog(@RequestParam("id") int id) {
        dogRepo.findById(id).ifPresent(dogRepo::delete);
        return new ModelAndView("redirect:/viewModifyDelete");
    }

    @GetMapping("/search")
    public ModelAndView searchById(@RequestParam("id") int id) {
        ModelAndView mv = new ModelAndView();
        Dog dog = dogRepo.findById(id).orElse(null);
        if (dog != null) {
            mv.setViewName("searchresults");
            mv.addObject("dog", dog);
        } else {
            mv.setViewName("error");
            mv.addObject("message", "Dog not found!");
        }
        return mv;
    }

    @GetMapping("/addTrainer")
    public ModelAndView addTrainerForm() {
        return new ModelAndView("addNewTrainer")
            .addObject("trainer", new Trainer()); // for form binding
    }

    @PostMapping("/trainerAdded")
    public ModelAndView addNewTrainer(@ModelAttribute Trainer trainer) {
        trainerRepo.save(trainer);
        return new ModelAndView("redirect:/dogHome");
    }
}
