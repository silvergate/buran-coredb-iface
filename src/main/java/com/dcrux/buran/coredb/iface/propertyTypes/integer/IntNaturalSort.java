package com.dcrux.buran.coredb.iface.propertyTypes.integer;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * @author caelis
 */
public class IntNaturalSort implements ISorter {

  public static final SorterRef REF = SorterRefs.NATURAL;
  public static final IntNaturalSort SINGLETON = new IntNaturalSort();

  @Override
  public ISorter getSingleton() {
    return SINGLETON;
  }

  @Override
  public SorterRef getRef() {
    return REF;
  }

  @Override
  public int compare(Object o1, Object o2) {
    //TODO: Check this!
    if (o1 == o2) {
      return 0;
    }
    if (o1 == null) {
      return -1;
    }
    if (o2 == null) {
      return 1;
    }
    return ((Integer) o1).compareTo((Integer) o2);
  }
}
