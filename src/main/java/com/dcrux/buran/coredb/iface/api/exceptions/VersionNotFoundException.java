package com.dcrux.buran.coredb.iface.api.exceptions;

/**
 * Is throw if the node was found but the given version is not available.
 *
 * @author: ${USER} Date: 02.05.13 Time: 22:20
 */
public class VersionNotFoundException extends Exception {
    public VersionNotFoundException(String message) {
        super(message);
    }
}
