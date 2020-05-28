package com.rover.interview.service;

import com.rover.interview.model.Owner;
import com.rover.interview.model.Sitter;
import com.rover.interview.model.Stay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class StayServiceTests {

    private static int testCounter = 0;

    @Autowired private StayService stayService;
    @Autowired private OwnerService ownerService;
    @Autowired private SitterService sitterService;

    @Value("${datePattern}")
    private String datePattern;

    private int rating = 5;
    private String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private Date startDate;
    private Date endDate;
    private Owner owner;
    private Sitter sitter;

    @BeforeEach
    void init() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        startDate = dateFormat.parse("2020-01-01");
        endDate = dateFormat.parse("2020-01-02");
        owner = new Owner("Name S.", "+10001112222", "owner" + testCounter + "@stayservicetests.rover", "http://url");
        ownerService.createOwner(owner);
        sitter = new Sitter("Name S.", "+10001112222", "sitter" + testCounter + "@stayservicetests.rover", "http://url");
        sitterService.create(sitter);
        testCounter++;
    }

    @Test
    public void createValidStay() {
        Stay stay = new Stay(rating, startDate, endDate, text, owner, sitter);
        stayService.create(stay);

        Stay savedStay = stayService.getStay(stay.getId());
        assertNotNull(savedStay);
        assertEquals(savedStay.getId(), stay.getId());
        assertEquals(savedStay.getOwner(), stay.getOwner());
        assertEquals(stay.getSitter(), stay.getSitter());
    }

    @Test
    public void createStayWithNullStartDateUsingConstructor() {
        Assertions.assertThrows(NullPointerException.class, () -> new Stay(rating, null, endDate, text, owner, sitter));
    }

    @Test
    public void createStayWithNullStartDateUsingSetters() {
        Stay stay = new Stay();
        stay.setRating(rating);
        stay.setEndDate(endDate);
        stay.setText(text);
        stay.setOwner(owner);
        stay.setSitter(sitter);
        Assertions.assertThrows(NullPointerException.class, () -> stayService.create(stay));
    }

    @Test
    public void createStayWithNullEndDateUsingConstructor() {
        Assertions.assertThrows(NullPointerException.class, () -> new Stay(rating, startDate, null, text, owner, sitter));
    }

    @Test
    public void createStayWithNullEndDateUsingSetters() {
        Stay stay = new Stay();
        stay.setRating(rating);
        stay.setStartDate(startDate);
        stay.setText(text);
        stay.setOwner(owner);
        stay.setSitter(sitter);
        Assertions.assertThrows(NullPointerException.class, () -> stayService.create(stay));
    }

    @Test
    public void createStayWithEndDateBeforeStartDate() {
        Stay stay = new Stay(rating, endDate, startDate, text, owner, sitter);
        Assertions.assertThrows(IllegalArgumentException.class, () -> stayService.create(stay));
    }

    @Test
    public void createStayWithNullText() {
        Stay stay = new Stay(rating, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
    }

    @Test
    public void createStayWithNullOwner() {
        Stay stay = new Stay();
        stay.setRating(rating);
        stay.setStartDate(startDate);
        stay.setEndDate(endDate);
        stay.setText(text);
        stay.setSitter(sitter);
        Assertions.assertThrows(TransactionSystemException.class, () -> stayService.create(stay));
    }

    @Test
    public void createStayWithNullSitter() {
        Stay stay = new Stay();
        stay.setRating(rating);
        stay.setStartDate(startDate);
        stay.setEndDate(endDate);
        stay.setText(text);
        stay.setOwner(owner);
        Assertions.assertThrows(TransactionSystemException.class, () -> stayService.create(stay));
    }

    @Test
    public void testRankAlgorithm() {
        Sitter sitter = new Sitter("ABCDEFGHIJKLM", "+10001112222", "ABCDEFGHIJKLM@stayservicetests.rover", null);
        sitterService.create(sitter);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(0, sitter.getStayCount(), 0);
        assertEquals(0, sitter.getRatingsScore(), 0);
        assertEquals(2.5, sitter.getSitterRank(), 0);

        Stay stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(1, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(2.75, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(2, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(3.0, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(3, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(3.25, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(4, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(3.5, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(5, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(3.75, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(6, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(4.0, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(7, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(4.25, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(8, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(4.5, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(9, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(4.75, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(10, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(5.0, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(11, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(5.0, sitter.getSitterRank(), 0);

        stay = new Stay(5, startDate, endDate, null, owner, sitter);
        stayService.create(stay);
        assertEquals(2.5, sitter.getSitterScore(), 0);
        assertEquals(12, sitter.getStayCount(), 0);
        assertEquals(5, sitter.getRatingsScore(), 0);
        assertEquals(5.0, sitter.getSitterRank(), 0);
    }
}
