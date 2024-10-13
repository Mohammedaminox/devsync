package com.devsync.services;

import com.devsync.entities.Tag;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class TagService {

    @PersistenceContext
    private EntityManager em;

    // Créer un nouveau tag
    public Tag createTag(Tag tag) {
        em.persist(tag);
        return tag;
    }

    // Obtenir un tag par son ID
    public Tag getTagById(Long id) {
        return em.find(Tag.class, id);
    }

    // Récupérer tous les tags
    public List<Tag> getAllTags() {
        return em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
    }

    // Mettre à jour un tag
    public Tag updateTag(Tag tag) {
        return em.merge(tag);
    }

    // Supprimer un tag
    public void deleteTag(Long id) {
        Tag tag = getTagById(id);
        if (tag != null) {
            em.remove(tag);
        }
    }
}
