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
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @RequestMapping("/dogHome")
    public ModelAndView dogHome() {
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("addNewDog");
        Iterable<Trainer> trainerList = trainerRepo.findAll();
        mv.addObject("trainers", trainerList);
        return mv;
    }

    @RequestMapping("/addNewDog")
    public ModelAndView addNewDog(Dog dog, @RequestParam("trainerId") int id) {
        Trainer trainer = trainerRepo.findById(id).orElse(null);
        if (trainer != null) {
            dog.setTrainer(trainer);
            dogRepo.save(dog);
        }
        ModelAndView mv = new ModelAndView("redirect:/dogHome");
        return mv;
    }

    @RequestMapping("/viewModifyDelete")
    public ModelAndView viewDogs() {
        ModelAndView mv = new ModelAndView("viewDogs");
        Iterable<Dog> dogList = dogRepo.findAll();
        mv.addObject("dogs", dogList);
        return mv;
    }

    @RequestMapping("/editDog")
    public ModelAndView editDog(Dog dog) {
        dogRepo.save(dog);
        ModelAndView mv = new ModelAndView("editDog");
        mv.addObject("message", "Dog updated successfully!");
        return mv;
    }

    @RequestMapping("/deleteDog")
    public ModelAndView deleteDog(@RequestParam("id") int id) {
        Dog dog = dogRepo.findById(id).orElse(null);
        if (dog != null) {
            dogRepo.delete(dog);
        }
        return new ModelAndView("redirect:/viewModifyDelete");
    }

    @RequestMapping("/search")
    public ModelAndView searchById(@RequestParam("id") int id) {
        ModelAndView mv = new ModelAndView();
        Dog dogFound = dogRepo.findById(id).orElse(null);
        if (dogFound != null) {
            mv.addObject("dog", dogFound);
            mv.setViewName("searchResults");
        } else {
            mv.addObject("message", "Dog not found!");
            mv.setViewName("error");
        }
        return mv;
    }

    @RequestMapping("/addTrainer")
    public ModelAndView addTrainer() {
        ModelAndView mv = new ModelAndView("addNewTrainer");
        return mv;
    }

    @RequestMapping("/trainerAdded")
    public ModelAndView addNewTrainer(Trainer trainer) {
        trainerRepo.save(trainer);
        ModelAndView mv = new ModelAndView("redirect:/dogHome");
        return mv;
    }
}