package com.rover.interview.init;

import com.rover.interview.model.Owner;
import com.rover.interview.model.Sitter;
import com.sun.tools.javac.util.Assert;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Initializer implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    private final InitializerHelper initializerHelper;
    private final Map<String, Integer> map;

    @Value("${filename}")
    private String filename;

    @Autowired
    public Initializer(InitializerHelper initializerHelper) {
        this.initializerHelper = initializerHelper;
        map = new HashMap<>();
        map.put("rating", 0);
        map.put("sitter_image", 1);
        map.put("end_date", 2);
        map.put("text", 3);
        map.put("owner_image", 4);
        map.put("dogs", 5);
        map.put("sitter", 6);
        map.put("owner", 7);
        map.put("start_date", 8);
        map.put("sitter_phone_number", 9);
        map.put("sitter_email", 10);
        map.put("owner_phone_number", 11);
        map.put("owner_email", 12);
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        BufferedReader reader = getReader(filename);
        reader.readLine(); // skip first line
        int lineCounter = 2;
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] values = line.split(",");
                Assert.check(initializerHelper.validateValues(values), "Validation failed for line " + lineCounter);
                createEntities(values);
            } catch (ParseException e) {
                LOG.error("Failed to process line " + lineCounter, e);
            }
            lineCounter++;
        }
    }

    private BufferedReader getReader(@NonNull String filename) throws FileNotFoundException {
        LOG.debug("Opening CSV file " + filename);
        Reader fileReader = new FileReader(filename);
        return new BufferedReader(fileReader);
    }

    private void createEntities(@NonNull String[] values) throws ParseException {
        Owner owner = initializerHelper.getOrCreateOwner(
                values[map.get("owner")], values[map.get("owner_email")], values[map.get("owner_phone_number")], values[map.get("owner_image")]);

        Sitter sitter = initializerHelper.getOrCreateSitter(
                values[map.get("sitter")], values[map.get("sitter_email")], values[map.get("sitter_phone_number")], values[map.get("sitter_image")]);

        initializerHelper.getOrCreateDogs(
                values[map.get("dogs")].split("\\|"), owner);

        initializerHelper.createStay(
                sitter, owner, values[map.get("rating")], values[map.get("start_date")], values[map.get("end_date")], values[map.get("text")]);
    }
}
