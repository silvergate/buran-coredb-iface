package com.dcrux.buran.coredb.iface.nodeClass;

import com.dcrux.buran.coredb.iface.edgeClass.EdgeClass;

import java.io.Serializable;
import java.util.*;

/**
 * @author caelis
 */
public final class NodeClass implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7163989895451951239L;
    private final IType[] types;
    private final Map<String, Short> nameToTypeIndex;
    private final Set<Short> requiredTypes;
    private final Map<Short, EdgeClass> labelsToEdgeClasses;

    protected NodeClass(IType[] types, Map<String, Short> nameToTypeIndex, Set<Short> requiredTypes,
            Map<Short, EdgeClass> labelsToEdgeClasses) {
        this.types = types;
        this.nameToTypeIndex = nameToTypeIndex;
        this.requiredTypes = requiredTypes;
        this.labelsToEdgeClasses = labelsToEdgeClasses;
    }

    public static IBuilder builder() {
        return new IBuilder() {
            private final Map<String, Short> nameToTypeIndex = new HashMap<String, Short>();
            private final List<IType> types = new ArrayList<IType>();
            private final Set<Short> requiredTypes = new HashSet<>();
            private final Map<Short, EdgeClass> labelsToEdgeClasses = new HashMap<>();

            @Override
            public IBuilder add(String name, boolean required, IType type) {
                final short index = (short) this.types.size();
                this.nameToTypeIndex.put(name, index);
                this.types.add(type);
                if (required) {
                    this.requiredTypes.add(index);
                }
                return this;
            }

            @Override
            public IBuilder addEdgeClass(EdgeClass edgeClass) {
                if (this.labelsToEdgeClasses.containsKey(edgeClass.getLabelIndex())) {
                    throw new IllegalStateException("Edge with the same key already exists.");
                }
                this.labelsToEdgeClasses.put(edgeClass.getLabelIndex(), edgeClass);
                return this;
            }

            @Override
            public NodeClass get() {
                return new NodeClass(this.types.toArray(new IType[0]), this.nameToTypeIndex,
                        this.requiredTypes, this.labelsToEdgeClasses);
            }
        };
    }

    public IType getType(short typeIndex) {
        return this.types[typeIndex];
    }

    public <T extends IType> T getType(short typeIndex, Class<T> clz) {
        return (T) getType(typeIndex);
    }

    public boolean isRequired(short typeIndex) {
        return this.requiredTypes.contains(typeIndex);
    }

    public int getNumberOfTypes() {
        return (int) this.types.length;
    }

    public short getTypeIndex(String key) {
        return this.nameToTypeIndex.get(key);
    }

    public IType getType(String name) {
        final short index = this.nameToTypeIndex.get(name);
        return this.types[index];
    }

    public Map<Short, EdgeClass> getEdgeClasses() {
        return Collections.unmodifiableMap(this.labelsToEdgeClasses);
    }
}
