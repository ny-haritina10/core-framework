package nh.core.services;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SequenceService {

    @PersistenceContext
    private EntityManager entityManager;

    public long getNextSequence(String entityName) {
        String sequenceName = entityName.toLowerCase() + "_seq";

        // execute native query to get next sequence value
        Number sequenceValue = (Number) entityManager
            .createNativeQuery("SELECT nextval(:sequenceName)")
            .setParameter("sequenceName", sequenceName)
            .getSingleResult();
        return sequenceValue.longValue();
    }
}