package com.devsync.services;

import com.devsync.entities.Tache;
import com.devsync.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;

public class TacheService {
    @PersistenceContext
    private EntityManager em;

    public List<Tache> getAllTasks() {
        return em.createQuery("SELECT u FROM Tache u", Tache.class).getResultList();
    }

    public void createTache(Tache tache) throws IllegalArgumentException {
        // Ensure the task isn't created in the past
        if (tache.getDateCreation().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La tâche ne peut pas être créée dans le passé.");
        }

        // Ensure the task's due date is no more than 3 days in the future
        if (tache.getDateLimite().isAfter(LocalDate.now().plusDays(3))) {
            throw new IllegalArgumentException("La tâche ne peut pas être planifiée à plus de 3 jours.");
        }

        // Ensure there are multiple tags
        if (tache.getTags() == null || tache.getTags().size() < 2) {
            throw new IllegalArgumentException("Veuillez entrer plusieurs tags pour la tâche.");
        }

        // Save the task
    }

    public void completeTache(Tache tache) throws IllegalArgumentException {
        // Ensure the task is completed before its due date
        if (LocalDate.now().isAfter(tache.getDateLimite())) {
            throw new IllegalArgumentException("La tâche ne peut pas être marquée comme terminée après la date limite.");
        }

        tache.setTerminee(true);
        // Save the task update
    }
}
