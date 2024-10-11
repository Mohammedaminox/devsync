package com.devsync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "taches")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false, length = 255)
    private String titre;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Column(name = "date_limite", nullable = false)
    private LocalDate dateLimite;

    @Column(name = "is_terminee", nullable = false)
    private boolean isTerminee;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ElementCollection
    @CollectionTable(name = "tache_tags", joinColumns = @JoinColumn(name = "tache_id"))
    @Column(name = "tag")
    private Set<String> tags;  // For multiple tags

    public Tache() {}

    public Tache(String titre, String description, LocalDate dateCreation, LocalDate dateLimite, boolean isTerminee, Utilisateur utilisateur, Set<String> tags) {
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
        this.isTerminee = isTerminee;
        this.utilisateur = utilisateur;
        this.tags = tags;
    }


}
