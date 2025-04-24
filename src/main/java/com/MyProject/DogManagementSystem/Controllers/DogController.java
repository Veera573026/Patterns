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
    private DogRepository dogRepo;

    @Autowired
    private TrainerRepository trainerRepo;

    @RequestMapping("dogHome")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping("add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("addNewDog.html");
        mv.addObject("trainers", trainerRepo.findAll());
        return mv;
    }

    @RequestMapping("addNewDog")
    public ModelAndView addNewDog(Dog dog, @RequestParam("trainerId") int id) {
        Trainer trainer = trainerRepo.findById(id).orElse(null);
        if (trainer != null) {
            dog.setTrainer(trainer);
            dogRepo.save(dog);
        }
        return new ModelAndView("home");
    }

    @RequestMapping("viewModifyDelete")
    public ModelAndView viewDogs() {
        ModelAndView mv = new ModelAndView("viewDogs");
        mv.addObject("dogs", dogRepo.findAll());
        return mv;
    }

    @RequestMapping("editDog")
    public ModelAndView editDog(Dog dog) {
        dogRepo.save(dog);
        return new ModelAndView("editDog");
    }

    @RequestMapping("deleteDog")
    public ModelAndView deleteDog(@RequestParam("id") int id) {
        dogRepo.findById(id).ifPresent(dogRepo::delete);
        return home();
    }

    @RequestMapping("search")
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

    @RequestMapping("addTrainer")
    public ModelAndView addTrainer() {
        return new ModelAndView("addNewTrainer.html");
    }

    @RequestMapping("trainerAdded")
    public ModelAndView addNewTrainer(Trainer trainer) {
        trainerRepo.save(trainer);
        return new ModelAndView("home");
    }
}
