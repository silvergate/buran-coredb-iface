package com.dcrux.buran.coredb.iface.domains;

/**
 * @author caelis
 */
public final class DomainId {
    private final long id;

    public DomainId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainId domainId = (DomainId) o;

        if (id != domainId.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
