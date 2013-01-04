package com.dcrux.buran.coredb.iface.nodeClass;

/**
 * @author caelis
 */
public final class SorterRef {
  private final short ref;

  public static SorterRef c(int ref) {
    if ((ref < Short.MIN_VALUE) || (ref > Short.MAX_VALUE)) {
      throw new IllegalArgumentException("Sorter reference out of bounds of a short.");
    }
    return new SorterRef((short) ref);
  }

  private SorterRef(short ref) {
    this.ref = ref;
  }

  public short getRef() {
    return ref;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SorterRef sorterRef = (SorterRef) o;

    if (ref != sorterRef.ref) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return (int) ref;
  }
}
