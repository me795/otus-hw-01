package ru.dvsokolov.core.sessionmanager;

public interface TransactionRunner {

    <T> T doInTransaction(ru.dvsokolov.core.sessionmanager.TransactionAction<T> action);
}
