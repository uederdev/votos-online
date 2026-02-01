package br.com.ueder.votoonline.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtils {

    @PersistenceContext
    private EntityManager em;

    public <T> Object getEntityId(Class<T> clazz){
        String jpaQuery = "SELECT max(e.id)+1 FROM " + clazz.getSimpleName() + " e";
        return em.createQuery(jpaQuery).getSingleResult();
    }

}
