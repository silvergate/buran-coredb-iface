package com.dcrux.buran.coredb.iface.edge;

import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

/**
 * @author caelis
 */
public class EdgeLabel implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 8489172729557334152L;

    private final PrivateEdgeLabel privateLabel;
    private final PublicEdgeLabel publicLabel;

    private EdgeLabel(PrivateEdgeLabel privateLabel, PublicEdgeLabel publicLabel) {
        this.privateLabel = privateLabel;
        this.publicLabel = publicLabel;
    }

    public static short toHash(final String string) {
        short hash = 7;
        for (int i = 0; i < string.length(); i++) {
            hash = (short) (hash * 31 + string.charAt(i));
        }
        return hash;
    }

    private static byte setBit(byte input, byte pos, boolean set) {
        BitSet bs = BitSet.valueOf(new byte[]{input});
        bs.set(pos, set);
        return bs.toByteArray()[0];
    }

    public static EdgeLabel publicEdge(boolean queryable, byte[] data) {
        if (data.length != 17) {
            throw new IllegalArgumentException("data.length!=17");
        }
        final byte[] dataClone = data.clone();
        dataClone[16] = setBit(dataClone[16], (byte) 7, queryable);
        return new EdgeLabel(null, new PublicEdgeLabel(dataClone));
    }

    public static EdgeLabel privateEdge(ClassId classId, String name) {
        short index = toHash(name);
        return new EdgeLabel(new PrivateEdgeLabel(classId, index), null);
    }

    public static EdgeLabel privateEdge(ClassId classId, EdgeLabelIndex labelIndex) {
        return new EdgeLabel(new PrivateEdgeLabel(classId, labelIndex.getLabelIndex()), null);
    }

    public static EdgeLabel createPublic(boolean queryable) {
        System.out.println("TODO: Funktion richtig implementieren.");
        final byte[] data = new byte[17];
        Random random = new Random();
        random.nextBytes(data);
        return publicEdge(queryable, data);
    }

    public boolean isPublic() {
        return this.publicLabel != null;
    }

    public boolean isPublicQueryable() {
        if (!isPublic()) {
            throw new IllegalStateException("This method is only valid for public edges.");
        }
        BitSet bs = BitSet.valueOf(new byte[]{this.publicLabel.getData()[16]});
        return bs.get(7);
    }

    public short getPrivateEdgeIndex() {
        if (!isPrivate()) {
            throw new IllegalStateException("Operation is only allowed on private edges");
        }
        return this.privateLabel.getIndex();
    }

    public ClassId getPrivateClassId() {
        if (!isPrivate()) {
            throw new IllegalStateException("Operation is only allowed on private edges");
        }
        return this.privateLabel.getClassId();
    }

    public boolean isPrivate() {
        return !isPublic();
    }

    public String getString() {
        if (this.isPublic()) {
            return "_" + Hex.encodeHexString(this.publicLabel.getData());
        } else {
            return Long.toHexString(this.privateLabel.getClassId().getId()) +
                    Integer.toHexString(this.privateLabel.getIndex());
        }
    }

    @Override
    public String toString() {
        return "EdgeLabel{" +
                "'" + getString() + "'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeLabel edgeLabel = (EdgeLabel) o;

        if (privateLabel != null ? !privateLabel.equals(edgeLabel.privateLabel) :
                edgeLabel.privateLabel != null) return false;
        if (publicLabel != null ? !publicLabel.equals(edgeLabel.publicLabel) :
                edgeLabel.publicLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privateLabel != null ? privateLabel.hashCode() : 0;
        result = 31 * result + (publicLabel != null ? publicLabel.hashCode() : 0);
        return result;
    }

    private static class PublicEdgeLabel implements Serializable {
        private final byte[] data;

        private PublicEdgeLabel(byte[] data) {
            this.data = data;
        }

        private byte[] getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PublicEdgeLabel that = (PublicEdgeLabel) o;

            if (!Arrays.equals(data, that.data)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(data);
        }
    }

    public static class PrivateEdgeLabel implements Serializable {
        private final ClassId classId;
        private final short index;

        public PrivateEdgeLabel(ClassId classId, short index) {
            this.classId = classId;
            this.index = index;
        }

        public ClassId getClassId() {
            return classId;
        }

        public short getIndex() {
            return index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PrivateEdgeLabel that = (PrivateEdgeLabel) o;

            if (index != that.index) return false;
            if (!classId.equals(that.classId)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = classId.hashCode();
            result = 31 * result + (int) index;
            return result;
        }
    }
}
