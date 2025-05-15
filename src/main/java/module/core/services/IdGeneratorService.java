package module.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class IdGeneratorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String generate(String prefix, String sequenceName) {
        Long nextVal = ((Number) entityManager
                .createNativeQuery("SELECT nextval('" + sequenceName + "')")
                .getSingleResult()).longValue();

        return String.format("%s%06d", prefix, nextVal);
    }
}