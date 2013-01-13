package com.dcrux.buran.coredb.iface.subscription;

/**
 * Buran.
 *
 * @author: ${USER} Date: 07.01.13 Time: 21:30
 */
public final class SubscriptionId {
    private final int id;

    public SubscriptionId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionId that = (SubscriptionId) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
