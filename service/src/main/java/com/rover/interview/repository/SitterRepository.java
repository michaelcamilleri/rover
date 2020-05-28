package com.rover.interview.repository;

import com.rover.interview.model.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SitterRepository extends JpaRepository<Sitter, Long> {

    @Query("SELECT s FROM Sitter s WHERE LOWER(s.email) = LOWER(:email)")
    Sitter findByEmail(@Param(value="email") String email);

    @Query("SELECT s FROM Sitter s WHERE s.ratingsScore >= :ratingsScore ORDER BY s.sitterRank DESC")
    List<Sitter> findByMinRatingsScore(@Param(value="ratingsScore") double ratingsScore);
}
