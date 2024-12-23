package com.huy.imdb_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    private Long genreId;

    @Column(nullable = false , length = 100)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;
}
