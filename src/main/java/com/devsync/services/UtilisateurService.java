package com.devsync.services;

import com.devsync.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;


public class UtilisateurService {
    @PersistenceContext
    private EntityManager em;

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        em.persist(utilisateur);
        return utilisateur;
    }

    public Utilisateur getUtilisateurById(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return em.merge(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        Utilisateur utilisateur = getUtilisateurById(id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }
}
