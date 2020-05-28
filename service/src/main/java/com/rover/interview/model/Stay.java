package com.rover.interview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Stay {

    @Id @GeneratedValue private Long id;

    @Setter @NotNull @Min(1) @Max(5)
    private Integer rating;

    @Setter @NotNull
    private Date startDate;

    @Setter @NotNull
    private Date endDate;

    @Setter @Column(length=100000)
    private String text;

    @Setter @ManyToOne @NotNull
    Owner owner;

    @Setter @ManyToOne @NotNull
    Sitter sitter;

    public Stay(@NonNull Integer rating, @NonNull Date startDate, @NonNull Date endDate,
                String text, @NonNull Owner owner, @NonNull Sitter sitter) {
        this.rating = rating;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.owner = owner;
        this.sitter = sitter;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Stay && ((Stay) o).getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
