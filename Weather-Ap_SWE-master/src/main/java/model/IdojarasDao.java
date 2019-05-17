package model;

import com.google.inject.persist.Transactional;
import jpa.GenJpaDao;

public class IdojarasDao extends GenJpaDao<Idojaras> {
    public IdojarasDao() {
        super(Idojaras.class);
    }

    @Transactional
    public Idojaras lastLocation() {
        return entityManager.createQuery("SELECT r FROM Idojaras r ORDER BY r.id DESC", Idojaras.class).setMaxResults(1).getResultList().get(0);
    }
}