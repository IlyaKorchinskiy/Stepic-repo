package ru.korchinskiy.dbService.dao;

import org.hibernate.Session;
import ru.korchinskiy.dbService.dataSets.UsersDataSet;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) {
        return session.get(UsersDataSet.class, id);
    }

    public UsersDataSet getUser(String login) {
        EntityManager entityManager = session.getSessionFactory().createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteria = builder.createQuery(UsersDataSet.class);
        Root<UsersDataSet> root = criteria.from(UsersDataSet.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("login"), login));
        return entityManager.createQuery(criteria)
                .getSingleResult();
    }

    public long insertUser(String login, String password) {
        return (long) session.save(new UsersDataSet(login, password));
    }

}
