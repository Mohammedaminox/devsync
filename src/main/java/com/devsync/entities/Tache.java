package com.devsync.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "taches")
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateEcheance;

    @Column(nullable = false)
    private boolean isTerminee;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur; // Association avec l'utilisateur

    @ElementCollection
    @CollectionTable(name = "tache_tags", joinColumns = @JoinColumn(name = "tache_id"))
    @Column(name = "tag")
    private List<String> tags; // Liste de tags

    // Getters et setters
}

