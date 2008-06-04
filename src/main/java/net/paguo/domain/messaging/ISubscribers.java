package net.paguo.domain.messaging;

import net.paguo.domain.users.LocalUser;

import java.util.Set;

/**
 * @author Reyentenko
 */
public interface ISubscribers {
    Set<LocalUser> getSubscribers();
}
