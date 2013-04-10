package com.dcrux.buran.coredb.iface;

import java.io.Serializable;

/**
 * @author caelis
 */
public class NidVer extends Nid implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3938842815924445001L;

	/**
     * First version of a node.
     */
    public static final int FIRST_VERSION = Integer.MIN_VALUE + 1;

    private final int version;

    public int getVersion() {
        return version;
    }

    public NidVer(long nid, int version) {
        super(nid);
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NidVer nidVer = (NidVer) o;

        if (version != nidVer.version) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + version;
        return result;
    }

    @Override
    public String toString() {
        return "NidVer{" +
                "version=" + version +
                '}';
    }
}
