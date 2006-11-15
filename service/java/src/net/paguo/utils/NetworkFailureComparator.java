package net.paguo.utils;

import net.paguo.domain.problems.NetworkFailure;

import java.util.Comparator;
import java.util.Date;

/**
 * User: slava
 * Date: 02.10.2006
 * Time: 1:09:34
 * Version: $Id$
 */
public class NetworkFailureComparator implements Comparator<NetworkFailure> {

    public int compare(NetworkFailure o1, NetworkFailure o2) {
        return new Long(o2.getFailureTime().getTime()).compareTo(new Long(o1.getFailureTime().getTime()));
    }
}
