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

    // Relation Many-to-One avec Utilisateur
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    // Relation Many-to-Many avec Tag
    @ManyToMany
    @JoinTable(
            name = "tache_tags",
            joinColumns = @JoinColumn(name = "tache_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    // Constructeur par défaut
    public Tache() {}

    // Constructeur avec tous les champs
    public Tache(String titre, String description, LocalDate dateCreation, LocalDate dateLimite, boolean isTerminee, Utilisateur utilisateur, Set<Tag> tags) {
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
        this.isTerminee = isTerminee;
        this.utilisateur = utilisateur;
        this.tags = tags;
    }

    // Méthode pour vérifier si la tâche peut être créée (ne doit pas être dans le passé)
    @PrePersist
    private void validateDates() {

        if (dateLimite.isBefore(dateCreation)) {
            throw new IllegalArgumentException("La date limite doit être postérieure à la date de création");
        }
    }
}
