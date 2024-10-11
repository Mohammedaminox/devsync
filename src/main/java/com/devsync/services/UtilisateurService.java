package com.devsync.services;

import com.devsync.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
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

    public Utilisateur authenticate(String email, String password) {
        try {
            // Query to get the user by email
            Utilisateur utilisateur = em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();

            // Check if the password matches the stored password
            if (utilisateur != null && password.equals(utilisateur.getPassword())) {
                return utilisateur;
            }
        } catch (NoResultException e) {
            return null;
        }
        return null;
    }
}
