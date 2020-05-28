package com.rover.interview.controller;

import com.rover.interview.model.Owner;
import com.rover.interview.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class OwnerControllerTests {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String name = "Name S.";
    private String phoneNumber = "+10001112222";
    private String imageUrl = "https://url";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetOwners() throws Exception {
        Owner owner1 = new Owner(name, phoneNumber, "name1@ownercontrollertests.rover", imageUrl);
        Owner owner2 = new Owner(name, phoneNumber, "name2@ownercontrollertests.rover", imageUrl);
        ownerService.createOwner(owner1);
        ownerService.createOwner(owner2);

        mockMvc.perform(get("/owners")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(195)))
                .andExpect(jsonPath("$[193].name", is(owner1.getName())))
                .andExpect(jsonPath("$[193].phoneNumber", is(owner1.getPhoneNumber())))
                .andExpect(jsonPath("$[193].email", is(owner1.getEmail())))
                .andExpect(jsonPath("$[193].imageUrl", is(owner1.getImageUrl())))
                .andExpect(jsonPath("$[194].name", is(owner2.getName())))
                .andExpect(jsonPath("$[194].phoneNumber", is(owner2.getPhoneNumber())))
                .andExpect(jsonPath("$[194].email", is(owner2.getEmail())))
                .andExpect(jsonPath("$[194].imageUrl", is(owner2.getImageUrl())));
    }

    @Test
    public void testGetOwnerByEmail() throws Exception {
        String email = "name3@ownercontrollertests.rover";
        Owner owner = new Owner(name, phoneNumber, email, imageUrl);
        ownerService.createOwner(owner);

        mockMvc.perform(get("/owner/" + email)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.phoneNumber", is(phoneNumber)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.imageUrl", is(imageUrl)));
    }

    @Test
    public void testCreateOwnerByEmailWithInvalidEmailAndMissingName() throws Exception {
        String email = "name4atownercontrollertests.rover";
        mockMvc.perform(get("/create/owner/?email=" +  email + "&phoneNumber=" + phoneNumber)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.details.name", is("Name is required")))
                .andExpect(jsonPath("$.details.email", is("Incorrect email format")));
    }

    // TODO Add tests to cover more cases and to test other controllers
}
