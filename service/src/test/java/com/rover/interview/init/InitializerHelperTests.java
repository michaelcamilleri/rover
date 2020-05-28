package com.rover.interview.init;

import com.rover.interview.model.Owner;
import com.rover.interview.model.Sitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InitializerHelperTests {

    @Autowired
    private InitializerHelper initializerHelper;

    private String rating = "5";
    private String name = "Name S.";
    private String email = "name@initializerhelpertests.rover";
    private String phoneNumber = "+1000111222";
    private String imageUrl = "http://url";
    private String startDate = "2020-02-02";
    private String endDate = "2020-02-20";
    private String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private String dogs = "Nacho|Taco";

    @Test
    public void createOwner() {
        String uniqueEmail = "name1@initializerhelpertests.rover";
        Owner owner = initializerHelper.getOrCreateOwner(name, uniqueEmail, phoneNumber, imageUrl);
        assertNotNull(owner);
        assertEquals(name, owner.getName());
        assertEquals(uniqueEmail, owner.getEmail());
        assertEquals(phoneNumber, owner.getPhoneNumber());
        assertEquals(imageUrl, owner.getImageUrl());
    }

    @Test
    public void createOwnerWithNullImageUrl() {
        String uniqueEmail = "name2@initializerhelpertests.rover";
        Owner owner = initializerHelper.getOrCreateOwner(name, uniqueEmail, phoneNumber, null);
        assertNotNull(owner);
        assertEquals(name, owner.getName());
        assertEquals(uniqueEmail, owner.getEmail());
        assertEquals(phoneNumber, owner.getPhoneNumber());
        assertNull(owner.getImageUrl());
    }

    @Test
    public void createOwnerWithExistingEmail() {
        String uniqueEmail = "name3@initializerhelpertests.rover";
        Owner owner1 = initializerHelper.getOrCreateOwner(name, uniqueEmail, phoneNumber, null);
        assertNotNull(owner1);
        assertEquals(name, owner1.getName());
        assertEquals(uniqueEmail, owner1.getEmail());
        assertEquals(phoneNumber, owner1.getPhoneNumber());
        assertNull(owner1.getImageUrl());

        String duplicateEmail = "name3@initializerhelpertests.rover";
        Owner owner2 = initializerHelper.getOrCreateOwner(name, duplicateEmail, phoneNumber, imageUrl);
        assertNotNull(owner2);
        assertEquals(owner1.getName(), owner2.getName());
        assertEquals(owner1.getEmail(), owner2.getEmail());
        assertEquals(owner1.getPhoneNumber(), owner2.getPhoneNumber());
        assertEquals(owner1.getImageUrl(), owner2.getImageUrl());
    }

    @Test(expected = NullPointerException.class)
    public void createOwnerWithNullName() {
        String uniqueEmail = "name4@initializerhelpertests.rover";
        initializerHelper.getOrCreateOwner(null, uniqueEmail, phoneNumber, imageUrl);
    }

    @Test(expected = NullPointerException.class)
    public void createOwnerWithNullEmail() {
        initializerHelper.getOrCreateOwner(name, null, phoneNumber, imageUrl);
    }

    @Test(expected = NullPointerException.class)
    public void createOwnerWithNullPhoneNumber() {
        initializerHelper.getOrCreateOwner(name, null, phoneNumber, imageUrl);
    }

    @Test
    public void createSitter() {
        String uniqueEmail = "name1@initializerhelpertests.rover";
        Sitter sitter = initializerHelper.getOrCreateSitter(name, uniqueEmail, phoneNumber, imageUrl);
        assertNotNull(sitter);
        assertEquals(name, sitter.getName());
        assertEquals(uniqueEmail, sitter.getEmail());
        assertEquals(phoneNumber, sitter.getPhoneNumber());
        assertEquals(imageUrl, sitter.getImageUrl());
    }

    @Test
    public void createSitterWithNullImageUrl() {
        String uniqueEmail = "name2@initializerhelpertests.rover";
        Sitter sitter = initializerHelper.getOrCreateSitter(name, uniqueEmail, phoneNumber, null);
        assertNotNull(sitter);
        assertEquals(name, sitter.getName());
        assertEquals(uniqueEmail, sitter.getEmail());
        assertEquals(phoneNumber, sitter.getPhoneNumber());
        assertNull(sitter.getImageUrl());
    }

    @Test
    public void createSitterWithExistingEmail() {
        String uniqueEmail = "name3@initializerhelpertests.rover";
        Sitter sitter1 = initializerHelper.getOrCreateSitter(name, uniqueEmail, phoneNumber, null);
        assertNotNull(sitter1);
        assertEquals(name, sitter1.getName());
        assertEquals(uniqueEmail, sitter1.getEmail());
        assertEquals(phoneNumber, sitter1.getPhoneNumber());
        assertNull(sitter1.getImageUrl());

        String duplicateEmail = "name3@initializerhelpertests.rover";
        Sitter sitter2 = initializerHelper.getOrCreateSitter(name, duplicateEmail, phoneNumber, imageUrl);
        assertNotNull(sitter2);
        assertEquals(sitter1.getName(), sitter2.getName());
        assertEquals(sitter1.getEmail(), sitter2.getEmail());
        assertEquals(sitter1.getPhoneNumber(), sitter2.getPhoneNumber());
        assertEquals(sitter1.getImageUrl(), sitter2.getImageUrl());
    }

    @Test(expected = NullPointerException.class)
    public void createSitterWithNullName() {
        String uniqueEmail = "name4@initializerhelpertests.rover";
        initializerHelper.getOrCreateSitter(null, uniqueEmail, phoneNumber, imageUrl);
    }

    @Test(expected = NullPointerException.class)
    public void createSitterWithNullEmail() {
        initializerHelper.getOrCreateSitter(name, null, phoneNumber, imageUrl);
    }

    @Test(expected = NullPointerException.class)
    public void createSitterWithNullPhoneNumber() {
        initializerHelper.getOrCreateSitter(name, null, phoneNumber, imageUrl);
    }

    // TODO Add getOrCreateDogs tests
    // TODO Add createStay tests

    @Test
    public void validateValuesWithCorrectValues() throws ParseException {
        String[] values = {rating, imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber, email};
        assertTrue(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithTextRating() throws ParseException {
        String[] values = {"non-numeric", imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithDecimalRating() throws ParseException {
        String[] values = {"4.0", imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithIncorrectDogs() throws ParseException {
        String[] values = {rating, imageUrl, endDate, text, imageUrl, dogs+"\\| ", name, name, startDate, phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithIncorrectStartDate() throws ParseException {
        String[] values = {rating, imageUrl, endDate, text, imageUrl, dogs, name, name, "2020/01/01", phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithIncorrectEndDate() throws ParseException {
        String[] values = {rating, imageUrl, "01-01-2020", text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithEndDateBeforeStartDate() throws ParseException {
        String[] values = {rating, imageUrl, startDate, text, imageUrl, dogs, name, name, endDate, phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithIncorrectEmailFormat() throws ParseException {
        String[] values = {rating, imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, "name.domain@tld", phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithMissingValue() throws ParseException {
        String[] values = {rating, imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber};
        assertFalse(initializerHelper.validateValues(values));
    }

    @Test
    public void validateValuesWithNullRating() throws ParseException {
        String[] values = {null, imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber, email};
        assertFalse(initializerHelper.validateValues(values));
    }

    // TODO Validate other required values with nulls

    @Test
    public void validateValuesWithNullEmail() throws ParseException {
        String[] values = {rating, imageUrl, endDate, text, imageUrl, dogs, name, name, startDate, phoneNumber, email, phoneNumber, null};
        assertFalse(initializerHelper.validateValues(values));
    }
}
