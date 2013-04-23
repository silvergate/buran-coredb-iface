package com.dcrux.buran.coredb.iface.subscription;

import com.dcrux.buran.coredb.iface.UserId;
import com.dcrux.buran.coredb.iface.query.ICondNode;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * Buran.
 *
 * @author: ${USER} Date: 07.01.13 Time: 21:42
 */
public class Subscription implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8488509764947945105L;
    private final UserId receiver;
    private final UserId sender;
    private final EnumSet<SubscriptionEventType> eventTypes;
    private final ICondNode query;
    private final ISubscriptionEventHandler handler;
    @Deprecated
    private final boolean weakHandlerReference;

    public Subscription(UserId receiver, UserId sender, EnumSet<SubscriptionEventType> eventTypes,
            ICondNode query, ISubscriptionEventHandler handler) {
        if (eventTypes.isEmpty())
            throw new IllegalArgumentException("Need at least one event type.");
        this.receiver = receiver;
        this.sender = sender;
        this.eventTypes = eventTypes;
        this.query = query;
        this.handler = handler;
        this.weakHandlerReference = false;
    }

    public EnumSet<SubscriptionEventType> getEventTypes() {
        return eventTypes;
    }

    public ICondNode getQuery() {
        return query;
    }

    public ISubscriptionEventHandler getHandler() {
        return handler;
    }

    @Deprecated
    public boolean isWeakHandlerReference() {
        return weakHandlerReference;
    }

    public UserId getReceiver() {
        return receiver;
    }

    public UserId getSender() {
        return sender;
    }
}
