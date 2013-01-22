package com.dcrux.buran.coredb.iface.api.exceptions;

/**
 * Buran.
 *
 * @author: ${USER} Date: 02.01.13 Time: 16:49
 */
public class NodeNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 4781751645402675548L;

    public NodeNotFoundException(String message) {
        super(message);
    }
}
