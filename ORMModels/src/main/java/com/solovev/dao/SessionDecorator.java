package com.solovev.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class SessionDecorator implements AutoCloseable {
    private final Session session = SessionFactorySingleton.getInstance().openSession();

    public Session getSession() {
        return session;
    }

    /**
     * starts transaction, executes function and commits transaction;
     * Transaction will be rollback in case of exception
     *
     * @param methodToExecute function to execute
     * @param <T>             result of function execution
     * @return result of function execution
     */
    public <T> T beginAndCommitTransaction(Function<Session, T> methodToExecute) {
        Transaction transaction = session.beginTransaction();
        try {
            T result = methodToExecute.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    /**
     * starts transaction, executes function and commits transaction;
     * Transaction will be rollback in case of exception
     *
     * @param methodToExecute consumer to execute
     */
    public void beginAndCommitTransaction(Consumer<Session> methodToExecute) {
        Transaction transaction = session.beginTransaction();
        try {
            methodToExecute.accept(session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void close() {
        if (session.isOpen()) {
            session.close();
        }
    }
}
