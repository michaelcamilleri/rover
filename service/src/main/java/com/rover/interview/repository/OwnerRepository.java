package com.rover.interview.repository;

import com.rover.interview.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE LOWER(o.email) = LOWER(:email)")
    Owner findByEmail(@Param(value="email") String email);
}
