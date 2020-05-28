package com.rover.interview.service;

import com.rover.interview.model.Owner;
import com.rover.interview.repository.OwnerRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public void createOwner(@NonNull Owner owner) {
        ownerRepository.save(owner);
    }

    public Owner findByEmail(@NonNull String email) {
        return ownerRepository.findByEmail(email);
    }

    public List<Owner> listOwners() {
        return ownerRepository.findAll();
    }
}
