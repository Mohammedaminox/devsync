package org.youcode.repository;

import jakarta.persistence.EntityManager;
import org.youcode.interfaces.RepositoryInterface;
import org.youcode.model.Request;
import org.youcode.util.EntityManagerProvider;

import java.util.List;
import java.util.Optional;

public class RequestRepository implements RepositoryInterface<Request> {

    private EntityManager getEntityManager() {
        return EntityManagerProvider.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public Optional<Request> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            Request request = entityManager.find(Request.class, id);
            return Optional.ofNullable(request);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Request> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            List<Request> requests = entityManager.createQuery(
                    "SELECT r FROM Request r LEFT JOIN FETCH r.requester LEFT JOIN FETCH r.task", Request.class).getResultList();
            return requests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Request> create(Request request) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(request);
            entityManager.getTransaction().commit();
            return Optional.of(request);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Request> update(Request request) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Request updatedRequest = entityManager.merge(request);
            entityManager.getTransaction().commit();
            return Optional.of(updatedRequest);
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Request delete(Request request) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(request) ? request : entityManager.merge(request));
            entityManager.getTransaction().commit();
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }
}