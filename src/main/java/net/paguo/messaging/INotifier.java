package net.paguo.messaging;

/**
 * @author Reyentenko
 */
public interface INotifier<T> {
    void doNotify(T entity);
}
