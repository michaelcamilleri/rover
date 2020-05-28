package com.rover.interview.controller;

import com.rover.interview.model.Owner;
import com.rover.interview.service.OwnerService;
import com.rover.interview.validation.form.OwnerForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owner/{email}")
    public Owner getOwnerByEmail(@PathVariable @NotEmpty String email) {
        return ownerService.findByEmail(email);
    }

    @GetMapping("/owners")
    public List<Owner> listOwners() {
        return ownerService.listOwners();
    }

    @GetMapping("/create/owner")
    public ResponseEntity<String> createOwner(@Valid OwnerForm ownerForm) {
        Owner owner = new Owner(ownerForm);
        ownerService.createOwner(owner);
        return ResponseEntity.ok("Created owner with ID " + owner.getId());
    }

    // TODO Add Update and Delete methods
}
