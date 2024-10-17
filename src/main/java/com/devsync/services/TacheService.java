package com.devsync.services;

import com.devsync.entities.Tache;
import com.devsync.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class TacheService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UtilisateurService utilisateurService;

    // Créer une nouvelle tâche
    public Tache createTache(Tache tache) {
        em.persist(tache);
        return tache;
    }

    // Obtenir une tâche par son ID
    public Tache getTacheById(Long id) {
        Tache tache = em.find(Tache.class, id);
        if (tache != null) {
            Hibernate.initialize(tache.getTags());  // Initialize the tags collection
        }
        return tache;
    }




    public List<Tache> getTachesForUser(Utilisateur user) {
        if (user.isManager()) {
            // If the user is a manager, return all tasks
            return em.createQuery("SELECT t FROM Tache t", Tache.class).getResultList();
        } else {
            // Otherwise, return only the tasks created by the user
            return em.createQuery("SELECT t FROM Tache t WHERE t.utilisateur.id = :userId", Tache.class)
                    .setParameter("userId", user.getId())
                    .getResultList();
        }
    }

    public void assignTacheToUser(Long tacheId, Long userId) {
        // Retrieve the task and user
        Tache tache = getTacheById(tacheId);
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);

        // Check if both exist
        if (tache != null && utilisateur != null) {
            // Assign the user to the task
            tache.setUtilisateur(utilisateur);

            // Persist the updated task
            em.merge(tache);
        } else {
            throw new IllegalArgumentException("Task or User not found.");
        }
    }




    // Mettre à jour une tâche
    public Tache updateTache(Tache tache) {
        return em.merge(tache);
    }

    // Supprimer une tâche
    public void deleteTache(Long id) {
        Tache tache = getTacheById(id);
        if (tache != null) {
            em.remove(tache);
        }
    }

    // Marquer une tâche comme terminée
    public void markTacheAsCompleted(Long id) {
        Tache tache = getTacheById(id);
        if (tache != null) {
            tache.setTerminee(true);
            em.merge(tache);
        }
    }

    // Récupérer les tâches en retard (dépassant la date limite)
    public List<Tache> getLateTaches() {
        return em.createQuery(
                        "SELECT t FROM Tache t WHERE t.isTerminee = false AND t.dateLimite < CURRENT_DATE", Tache.class)
                .getResultList();
    }
}
