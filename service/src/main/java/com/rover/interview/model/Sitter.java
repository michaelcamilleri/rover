package com.rover.interview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Sitter {

    @Id @GeneratedValue
    private Long id;

    @Setter @NotNull
    private String name;

    @Setter @NotNull
    private String phoneNumber;

    @Setter @NotNull @Email @Column(unique=true)
    private String email;

    @Setter
    private String imageUrl;

    @Setter @NotNull @JsonIgnore
    private Integer stayCount = 0;

    @Setter @NotNull @JsonIgnore
    private Double sitterScore;

    @Setter
    private Double ratingsScore = 0.0;

    @Setter @NotNull// @JsonIgnore
    private Double sitterRank;

    @JsonIgnore @OneToMany(mappedBy = "sitter")
    private Set<Stay> stays;

    public Sitter(@NonNull String name, @NonNull String phoneNumber, @NonNull String email, String imageUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Sitter && ((Sitter) o).getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
