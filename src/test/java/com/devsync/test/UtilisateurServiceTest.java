package com.devsync.test;

import com.devsync.entities.Utilisateur;
import com.devsync.services.UtilisateurService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UtilisateurServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("John", "Doe", "johndoe", "password123", "john.doe@example.com", false);

        // Act
        utilisateurService.createUtilisateur(utilisateur);

        // Assert
        verify(entityManager, times(1)).persist(utilisateur); // Verify that the entity is persisted once
    }

    @Test
    public void testGetUtilisateurById() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("kamal", "khalid", "kamalKhalid", "password123", "kamalkhalid@gmail.com", true);
        when(entityManager.find(Utilisateur.class, 1L)).thenReturn(utilisateur);

        // Act
        Utilisateur result = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("kamal", result.getNom());
        assertEquals("khalid", result.getPrenom());
        assertEquals("kamalkhalid@gmail.com", result.getEmail());
    }

    @Test
    public void testGetAllUtilisateurs() {
        // Arrange
        Utilisateur user1 = new Utilisateur("benmade", "amine", "medamine", "medamine123", "medamine@example.com", false);
        Utilisateur user2 = new Utilisateur("kamal", "khalid", "kamalKhalid", "password123", "kamalkhalid@gmail.com", true);
        List<Utilisateur> utilisateurs = Arrays.asList(user1, user2);

        TypedQuery<Utilisateur> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(utilisateurs);

        // Act
        List<Utilisateur> result = utilisateurService.getAllUtilisateurs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("benmade", result.get(0).getNom());
        assertEquals("kamal", result.get(1).getNom());
    }

    @Test
    public void testUpdateUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("benmade", "amine", "medamine", "medamine123", "medamine@example.com", false);
        utilisateur.setId(1L);
        when(entityManager.merge(utilisateur)).thenReturn(utilisateur);

        // Act
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(utilisateur);

        // Assert
        assertNotNull(updatedUtilisateur);
        assertEquals("benmade", updatedUtilisateur.getNom());
        verify(entityManager, times(1)).merge(utilisateur); // Verify that the entity is merged once
    }

    @Test
    public void testDeleteUtilisateur() {
        // Arrange
        Utilisateur utilisateur = new Utilisateur("kamal", "khalid", "kamalKhalid", "password123", "kamalkhalid@gmail.com", true);
        utilisateur.setId(1L);
        when(entityManager.find(Utilisateur.class, 1L)).thenReturn(utilisateur);

        // Act
        utilisateurService.deleteUtilisateur(1L);

        // Assert
        verify(entityManager, times(1)).remove(utilisateur); // Verify that the entity is removed once
    }
}
