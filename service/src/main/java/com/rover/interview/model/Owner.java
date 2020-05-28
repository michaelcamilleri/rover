package com.rover.interview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rover.interview.validation.form.OwnerForm;
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
public class Owner {

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

    @OneToMany(mappedBy = "owner", fetch=FetchType.EAGER)
    private Set<Dog> dogs;

    @JsonIgnore @OneToMany(mappedBy = "owner")
    private Set<Stay> stays;

    public Owner(@NonNull String name, @NonNull String phoneNumber, @NonNull String email, String imageUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Owner(@NonNull OwnerForm ownerForm) {
        this.name = ownerForm.getName();
        this.phoneNumber = ownerForm.getPhoneNumber();
        this.email = ownerForm.getEmail();
        this.imageUrl = ownerForm.getImageUrl();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Owner && ((Owner) o).getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
