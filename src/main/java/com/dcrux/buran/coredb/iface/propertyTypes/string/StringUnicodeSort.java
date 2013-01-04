package com.dcrux.buran.coredb.iface.propertyTypes.string;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * @author caelis
 */
public class StringUnicodeSort implements ISorter {

  public static final SorterRef REF = SorterRefs.NATURAL;
  public static final StringUnicodeSort SINGLETON = new StringUnicodeSort();

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
    return ((String) o1).compareTo((String) o2);
  }
}
