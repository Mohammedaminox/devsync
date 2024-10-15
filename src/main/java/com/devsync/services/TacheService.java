package com.devsync.services;

import com.devsync.entities.Tache;
import com.devsync.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class TacheService {

    @PersistenceContext
    private EntityManager em;

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

    // Récupérer toutes les tâches
    public List<Tache> getAllTaches() {
        return em.createQuery("SELECT t FROM Tache t", Tache.class).getResultList();
    }


    // Récupérer toutes les tâches d'un utilisateur
    public List<Tache> getTachesByUtilisateur(Utilisateur utilisateur) {
        return em.createQuery(
                        "SELECT t FROM Tache t WHERE t.utilisateur = :utilisateur", Tache.class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
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
