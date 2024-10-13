package com.devsync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Relation Many-to-Many avec Tache
    @ManyToMany(mappedBy = "tags")
    private List<Tache> taches;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }
}
