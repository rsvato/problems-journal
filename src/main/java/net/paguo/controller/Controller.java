package net.paguo.controller;

import java.io.Serializable;
import java.util.Collection;

/**
 * User: slava
 * Date: 15.11.2006
 * Time: 1:34:39
 * Version: $Id$
 */
public interface Controller<E> extends Serializable {
    Collection<E> getAll();
}
