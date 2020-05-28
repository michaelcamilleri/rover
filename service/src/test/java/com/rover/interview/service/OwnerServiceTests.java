package com.rover.interview.service;

import com.rover.interview.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class OwnerServiceTests {

    @Autowired
    private OwnerService ownerService;

    private String name = "Name S.";
    private String phoneNumber = "+10001112222";
    private String imageUrl = "https://url";

    @Test
    public void createValidOwner() {
        String email = "name1@ownerservicetests.rover";
        Owner owner = new Owner(name, phoneNumber, email, imageUrl);
        ownerService.createOwner(owner);

        Owner savedOwner = ownerService.findByEmail(email);
        assertNotNull(savedOwner);
        assertEquals(savedOwner, owner);
        assertEquals(savedOwner.getId(), owner.getId());
        assertEquals(savedOwner.getEmail(), owner.getEmail());
        assertEquals(savedOwner.getPhoneNumber(), owner.getPhoneNumber());
        assertEquals(savedOwner.getName(), owner.getName());
        assertEquals(savedOwner.getImageUrl(), owner.getImageUrl());
    }

    @Test
    public void createValidOwnerWithNullImageUrl() {
        String email = "name2@ownerservicetests.rover";
        Owner owner = new Owner(name, phoneNumber, email, null);
        ownerService.createOwner(owner);

        Owner savedOwner = ownerService.findByEmail(email);
        assertNotNull(savedOwner);
        assertEquals(savedOwner.getId(), owner.getId());
        assertEquals(savedOwner.getEmail(), owner.getEmail());
        assertEquals(savedOwner.getPhoneNumber(), owner.getPhoneNumber());
        assertEquals(savedOwner.getName(), owner.getName());
        assertEquals(savedOwner.getImageUrl(), owner.getImageUrl());
    }

    @Test
    public void createOwnerWithNullEmailUsingConstructor() {
        Assertions.assertThrows(NullPointerException.class, () -> new Owner(name, phoneNumber, null, imageUrl));
    }

    @Test
    public void createOwnerWithNullEmailUsingSetters() {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setPhoneNumber(phoneNumber);
        owner.setImageUrl(imageUrl);
        Assertions.assertThrows(TransactionSystemException.class, () -> ownerService.createOwner(owner));
    }

    @Test
    public void createOwnerWithDuplicateEmail() {
        String email = "name3@ownerservicetests.rover";
        Owner owner1 = new Owner(name, phoneNumber, email, imageUrl);
        ownerService.createOwner(owner1);

        Owner owner2 = new Owner(name, phoneNumber, email, imageUrl);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> ownerService.createOwner(owner2));
    }
}
