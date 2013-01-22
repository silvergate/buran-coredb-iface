package com.dcrux.buran.coredb.iface.api.exceptions;

/**
 * Buran.
 *
 * @author: ${USER} Date: 02.01.13 Time: 16:20
 */
public class DomainNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -7655462020916039522L;

    public DomainNotFoundException(String message) {
        super(message);
    }
}
