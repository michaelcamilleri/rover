package com.rover.interview.init;

import com.rover.interview.model.Dog;
import com.rover.interview.model.Owner;
import com.rover.interview.model.Sitter;
import com.rover.interview.model.Stay;
import com.rover.interview.service.DogService;
import com.rover.interview.service.OwnerService;
import com.rover.interview.service.SitterService;
import com.rover.interview.service.StayService;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitializerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(InitializerHelper.class);

    private final DogService dogService;
    private final OwnerService ownerService;
    private final SitterService sitterService;
    private final StayService stayService;
    private final String datePattern;

    @Autowired
    public InitializerHelper(DogService dogService,
                             OwnerService ownerService,
                             SitterService sitterService,
                             StayService stayService,
                             @Value("${datePattern}") String datePattern) {
        this.dogService = dogService;
        this.ownerService = ownerService;
        this.sitterService = sitterService;
        this.stayService = stayService;
        this.datePattern = datePattern;
    }

    public Owner getOrCreateOwner(@NonNull String name, @NonNull String email, @NonNull String phoneNumber, String imageUrl) {
        Owner owner = ownerService.findByEmail(email);
        if (owner == null) {
            owner = new Owner(name, phoneNumber, email, imageUrl);
            ownerService.createOwner(owner);
        }
        return owner;
    }

    public Sitter getOrCreateSitter(@NonNull String name, @NonNull String email, @NonNull String phoneNumber, String imageUrl) {
        Sitter sitter = sitterService.findByEmail(email);
        if (sitter == null) {
            sitter = new Sitter(name, phoneNumber, email, imageUrl);
            sitterService.create(sitter);
        }
        return sitter;
    }

    public Set<Dog> getOrCreateDogs(@NonNull String[] names, @NonNull Owner owner) {
        Set<Dog> dogs = owner.getDogs();
        if (dogs == null) {
            dogs = new HashSet<>();
        }

        for (String name : names) {
            Dog dog = new Dog(name, owner);
            if (!dogs.contains(dog)) {
                dogService.create(dog);
                dogs.add(dog);
            }
        }
        return dogs;
    }

    public Stay createStay(@NonNull Sitter sitter, @NonNull Owner owner, @NonNull String rating,
                            @NonNull String startDate, @NonNull String endDate, String text) throws ParseException {
        Integer parsedRating = Integer.parseInt(rating);
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        Stay stay = new Stay(parsedRating, parsedStartDate, parsedEndDate, text, owner, sitter);
        stayService.create(stay);
        return stay;
    }

    public boolean validateValues(String[] values) throws ParseException {
        if (values.length != 13) {
            LOG.error("Validation error: Values length is not 13");
            return false;
        }
        if (StringUtils.isAnyBlank(values[0], values[2], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12])) {
            LOG.error("Validation error: Required value is blank");
            return false;
        }
        if (StringUtils.isAnyBlank(values[5].split("\\|"))) {
            LOG.error("Validation error: Dog name is blank");
            return false;
        }
        if (!StringUtils.isNumeric(values[0])) {
            LOG.error("Validation error: Rating not numeric");
            return false;
        }
        if (!validateDate(values[2]) || !validateDate(values[8])) {
            LOG.error("Validation error: Date does not match pattern");
            return false;
        }
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        Date startDate = dateFormat.parse(values[8]);
        Date endDate = dateFormat.parse(values[2]);
        if (endDate.compareTo(startDate) < 0) {
            LOG.error("Validation error: End date must occur after start date");
            return false;
        }
        if (!validateEmail(values[10]) || !validateEmail(values[12])) {
            LOG.error("Validation error: Incorrect email format");
            return false;
        }
        return true;
    }

    private boolean validateDate(String date) {
        if (date.length() != datePattern.length()) return false;

        String[] datePatternItems;
        String[] dateItems;
        if (datePattern.contains("-")) {
            datePatternItems = datePattern.split("-");
            dateItems = date.split("-");
        } else if (datePattern.contains("/")) {
            datePatternItems = datePattern.split("/");
            dateItems = date.split("/");
        } else {
            datePatternItems = new String[]{datePattern};
            dateItems = new String[]{date};
        }

        if (dateItems.length != datePatternItems.length) return false;

        for (int i=0; i<dateItems.length; i++) {
            if (!StringUtils.isNumeric(dateItems[i])) return false;
            if (dateItems[i].length() != datePatternItems[i].length()) return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        String[] nameAndDomain = email.split("@");
        if (nameAndDomain.length != 2) return false;
        String[] domainAndTld = nameAndDomain[1].split("\\.");
        return domainAndTld.length == 2;
    }
}
