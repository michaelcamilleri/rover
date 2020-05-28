package com.rover.interview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Dog {

    @Id @GeneratedValue
    private Long id;

    @Setter @NotNull
    String name;

    @Setter @ManyToOne @NotNull @JsonIgnore
    private Owner owner;

    public Dog(@NonNull String name, @NonNull Owner owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Dog
                && Objects.equals(((Dog) o).getName(), this.getName())
                && Objects.equals(((Dog) o).getOwner().getId(), this.getOwner().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getOwner().getId(), name);
    }
}
