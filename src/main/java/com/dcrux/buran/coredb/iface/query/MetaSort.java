package com.dcrux.buran.coredb.iface.query;

/**
 * @author caelis
 */
public class MetaSort implements ISorting {
    public static enum MetaField {
        firstCommitTime,
        commitTime
    }

    private final MetaField field;
    private final SortDirection direction;

    public MetaSort(MetaField field, SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    public static MetaSort cAsc(MetaField field) {
        return new MetaSort(field, SortDirection.asc);
    }

    public static MetaSort cDesc(MetaField field) {
        return new MetaSort(field, SortDirection.desc);
    }

    public MetaField getField() {
        return field;
    }

    public SortDirection getDirection() {
        return direction;
    }
}
