package com.dcrux.buran.coredb.iface.subscription;

import com.dcrux.buran.coredb.iface.NidVer;

/**
 * Buran.
 *
 * @author: ${USER} Date: 07.01.13 Time: 21:57
 */
public interface ISubscriptionEventHandler {
    void handle(NidVer node, SubscriptionEventType eventType, SubscriptionId id);
}
