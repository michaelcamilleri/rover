package com.rover.interview.service;

import com.rover.interview.model.Dog;
import com.rover.interview.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogService {

    private final DogRepository dogRepository;

    @Autowired
    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public void create(Dog dog) {
        dogRepository.save(dog);
    }

    public Dog getDog(Long id) {
        return dogRepository.findById(id).orElse(null);
    }
}
