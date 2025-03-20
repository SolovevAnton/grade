package com.solovev.dao.namedquery;

import com.solovev.dao.SessionDecorator;
import com.solovev.model.DaoEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DaoFacade<T extends DaoEntity> {
    private final Class<T> self;

    public Collection<T> getResults(String queryName) {
        return getResults(queryName, Map.of());
    }

    public Collection<T> getResults(String queryName, String key, Object value) {
        return getResults(queryName, Map.of(key, value));
    }

    public Collection<T> getResults(String queryName, Map<String, Object> params) {
        try (SessionDecorator decorator = new SessionDecorator()) {
            Query<T> query = decorator.getSession().createNamedQuery(queryName, self);
            params.forEach(query::setParameter);
            return query.getResultList();
        }
    }

    public Optional<T> getResult(String queryName) {
        return getResult(queryName, Map.of());
    }

    public Optional<T> getResult(String queryName, String key, Object value) {
        return getResult(queryName, Map.of(key, value));
    }

    public Optional<T> getResult(String queryName, Map<String, Object> params) {
        try (SessionDecorator decorator = new SessionDecorator()) {
            Query<T> query = decorator.getSession().createNamedQuery(queryName, self);
            params.forEach(query::setParameter);
            return query.uniqueResultOptional();
        }
    }

    public long executeNativeUpdate(String queryName, Map<String, Object> params, T entity) {
        try (SessionDecorator decorator = new SessionDecorator()) {
            Query<T> updateQuery = decorator.getSession().getNamedNativeQuery(queryName);
            params.forEach(updateQuery::setParameter);

            Query<Long> getIdQuery =
                    decorator.getSession().createQuery("select MAX(id) from " + self.getSimpleName(), Long.class);
            AtomicLong id = new AtomicLong();

            Supplier<Integer> executeQuery = () -> {
                var res = updateQuery.executeUpdate();
                id.set(getIdQuery.getSingleResult());
                return res;
            };
            decorator.beginAndCommitTransactionUpdateQuery(executeQuery);
            return id.longValue();
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean executeNamedUpdate(String queryName, Map<String, Object> params) {
        try (SessionDecorator decorator = new SessionDecorator()) {
            Query<T> query = decorator.getSession().createNamedQuery(queryName);
            params.forEach(query::setParameter);
            var res = decorator.beginAndCommitTransactionUpdateQuery(query::executeUpdate);
            if (res < 1) {
                throw new IllegalArgumentException("No matching entities found");
            }
            return true;
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
