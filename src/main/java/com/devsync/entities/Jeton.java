package com.devsync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "jetons")
public class Jeton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)  // "remplacement" or "suppression"
    private String type;

    @Column(name = "date_utilisation", nullable = false)
    private LocalDate dateUtilisation;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    public Jeton() {}

    public Jeton(String type, LocalDate dateUtilisation, Utilisateur utilisateur) {
        this.type = type;
        this.dateUtilisation = dateUtilisation;
        this.utilisateur = utilisateur;
    }

    // Getters and setters...
}
