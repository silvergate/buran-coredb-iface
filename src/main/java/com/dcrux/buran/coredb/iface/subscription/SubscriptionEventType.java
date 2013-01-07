package com.dcrux.buran.coredb.iface.subscription;

/**
 * Buran.
 *
 * @author: ${USER} Date: 07.01.13 Time: 21:31
 */
public enum SubscriptionEventType {
    /**
     * It's a new node (first version), not replacing another node.
     */
    newNode,
    /**
     * It's a node - not first version. Updating a previous version.
     */
    nodeUpdatingOther,
    /**
     * It's a node that's been updated.
     */
    nodeHistorizedReplaced,
    /**
     * It's a node that's been historized and not updated by a new version (aka. node has been
     * marked as deleted).
     */
    nodeHistorized
}
