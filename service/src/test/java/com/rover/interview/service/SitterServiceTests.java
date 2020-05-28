package com.rover.interview.service;

import com.rover.interview.model.Sitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class SitterServiceTests {

    @Autowired
    private SitterService sitterService;

    private String name = "Name S.";
    private String phoneNumber = "+10001112222";
    private String imageUrl = "https://url";

    @Test
    public void createValidSitter() {
        String email = "name1@sitterservicetests.rover";
        Sitter sitter = new Sitter(name, phoneNumber, email, imageUrl);
        sitterService.create(sitter);

        Sitter savedSitter = sitterService.findByEmail(email);
        assertNotNull(savedSitter);
        assertEquals(savedSitter.getId(), sitter.getId());
        assertEquals(savedSitter.getEmail(), sitter.getEmail());
        assertEquals(savedSitter.getPhoneNumber(), sitter.getPhoneNumber());
        assertEquals(savedSitter.getName(), sitter.getName());
        assertEquals(savedSitter.getImageUrl(), sitter.getImageUrl());
    }

    @Test
    public void createValidSitterWithNullImageUrl() {
        String email = "name2@sitterservicetests.rover";
        Sitter sitter = new Sitter(name, phoneNumber, email, null);
        sitterService.create(sitter);

        Sitter savedSitter = sitterService.findByEmail(email);
        assertNotNull(savedSitter);
        assertEquals(savedSitter.getId(), sitter.getId());
        assertEquals(savedSitter.getEmail(), sitter.getEmail());
        assertEquals(savedSitter.getPhoneNumber(), sitter.getPhoneNumber());
        assertEquals(savedSitter.getName(), sitter.getName());
        assertEquals(savedSitter.getImageUrl(), sitter.getImageUrl());
    }

    @Test
    public void createSitterWithNullEmailUsingConstructor() {
        Assertions.assertThrows(NullPointerException.class, () -> new Sitter(name, phoneNumber, null, imageUrl));
    }

    @Test
    public void createSitterWithNullEmailUsingSetters() {
        Sitter sitter = new Sitter();
        sitter.setName(name);
        sitter.setPhoneNumber(phoneNumber);
        sitter.setImageUrl(imageUrl);
        Assertions.assertThrows(TransactionSystemException.class, () -> sitterService.create(sitter));
    }

    @Test
    public void createSitterWithDuplicateEmail() {
        String email = "name3@sitterservicetests.rover";
        Sitter sitter1 = new Sitter(name, phoneNumber, email, imageUrl);
        sitterService.create(sitter1);

        Sitter sitter2 = new Sitter(name, phoneNumber, email, imageUrl);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> sitterService.create(sitter2));
    }
}
