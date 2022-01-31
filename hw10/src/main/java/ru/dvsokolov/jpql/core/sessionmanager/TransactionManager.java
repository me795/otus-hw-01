package ru.dvsokolov.jpql.core.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(ru.dvsokolov.jpql.core.sessionmanager.TransactionAction<T> action);
    <T> T doInReadOnlyTransaction(ru.dvsokolov.jpql.core.sessionmanager.TransactionAction<T> action);
}
