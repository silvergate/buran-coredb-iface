package com.dcrux.buran.coredb.iface.api;

import com.dcrux.buran.coredb.iface.edge.EdgeLabel;
import com.dcrux.buran.coredb.iface.nodeClass.FieldIndex;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 21.01.13 Time: 21:58
 */
public final class TransferExclusion {

    public static TransferExclusion c() {
        return new TransferExclusion();
    }

    /**
     * Don't transfer the specified property.
     *
     * @param fieldIndex
     * @return This for chaining.
     */
    public TransferExclusion ex(FieldIndex fieldIndex) {
        if (this.excludeAllProperties)
            throw new IllegalArgumentException("this.excludeAllProperties==true");
        this.excludedProperties.add(fieldIndex.getIndex());
        return this;
    }

    /**
     * Don't transfer properties at all.
     *
     * @return This for chaining.
     */
    public TransferExclusion exProperties() {
        if (!this.excludedProperties.isEmpty())
            throw new IllegalArgumentException("!this.excludedProperties.isEmpty()");
        this.excludeAllProperties = true;
        return this;
    }

    /**
     * Don't transfer edge of the specified label.
     *
     * @param edgeLabel
     * @return This for chaining.
     */
    public TransferExclusion ex(EdgeLabel edgeLabel) {
        if (this.excludeAllEdges) throw new IllegalArgumentException("this.excludeAllEdges==true");
        this.excludedEdges.add(edgeLabel);
        return this;
    }

    /**
     * Don't transfer edge at all.
     *
     * @return This for chaining.
     */
    public TransferExclusion exEdges() {
        if (!this.excludedEdges.isEmpty())
            throw new IllegalArgumentException("!this.excludedEdges.isEmpty()");
        this.excludeAllEdges = true;
        return this;
    }

    /**
     * Don't transfer domains at all.
     *
     * @return This for chaining.
     */
    public TransferExclusion exDomains() {
        this.excludeDomains = true;
        return this;
    }

    /**
     * Don't transfer permissions at all.
     *
     * @return This for chaining.
     */
    public TransferExclusion exPermissions() {
        this.excludePermissions = true;
        return this;
    }

    private boolean excludeDomains;
    private boolean excludePermissions;
    private boolean excludeAllProperties;
    private final Set<Short> excludedProperties = new HashSet<Short>();
    private final Set<Short> excludedPropertiesRo =
            Collections.unmodifiableSet(this.excludedProperties);
    private boolean excludeAllEdges;
    private final Set<EdgeLabel> excludedEdges = new HashSet<EdgeLabel>();
    private final Set<EdgeLabel> excludedEdgesRo = Collections.unmodifiableSet(this.excludedEdges);


    public boolean isExcludeDomains() {
        return excludeDomains;
    }

    public boolean isExcludePermissions() {
        return excludePermissions;
    }

    public boolean isExcludeAllProperties() {
        return excludeAllProperties;
    }

    public Set<Short> getExcludedProperties() {
        return excludedPropertiesRo;
    }

    public boolean isExcludeAllEdges() {
        return excludeAllEdges;
    }

    public Set<EdgeLabel> getExcludedEdges() {
        return excludedEdgesRo;
    }
}
