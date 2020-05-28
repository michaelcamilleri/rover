package com.rover.interview.service;

import com.rover.interview.model.Dog;
import com.rover.interview.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class DogServiceTests {

    @Autowired private DogService dogService;
    @Autowired private OwnerService ownerService;

    @Test
    public void createGoodDog() {
        Owner owner = new Owner("Name S.", "+10001112222", "name1@dogservicetests.rover", null);
        ownerService.createOwner(owner);

        Dog dog = new Dog("Spud", owner);
        dogService.create(dog);

        Dog savedDog = dogService.getDog(dog.getId());
        assertNotNull(savedDog);
        assertEquals(savedDog.getName(), dog.getName());
        assertEquals(savedDog.getOwner(), dog.getOwner());
        assertEquals(savedDog.getId(), dog.getId());
        assertEquals(savedDog, dog);
    }

    @Test
    public void createAnonymousDog() {
        Owner owner = new Owner("Name S.", "+10001112222", "name2@dogservicetests.rover", null);
        ownerService.createOwner(owner);

        Dog dog = new Dog();
        dog.setOwner(owner);
        Assertions.assertThrows(TransactionSystemException.class, () -> dogService.create(dog));
    }

    @Test
    public void createOrphanDog() {
        Dog dog = new Dog();
        dog.setName("Bacon");
        Assertions.assertThrows(TransactionSystemException.class, () -> dogService.create(dog));
    }
}
