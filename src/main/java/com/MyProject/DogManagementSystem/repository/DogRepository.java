package com.MyProject.DogManagementSystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.MyProject.DogManagementSystem.Models.Dog;

public interface DogRepository extends CrudRepository<Dog, Integer> {
	List<Dog> findByname(String name);

}
