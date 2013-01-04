package com.dcrux.buran.coredb.iface.propertyTypes.set;

import java.util.Arrays;

/**
 * Buran.
 *
 * @author: ${USER}
 * Date: 04.01.13
 * Time: 18:05
 */
public class ByteContainer {
  private final byte[] data;

  public byte[] getData() {
    return data.clone();
  }

  ByteContainer(byte[] data) {
    if (data == null) {
      throw new IllegalArgumentException("data must not be null");
    }
    if (data.length > SetType.MAX_LEN_BYTES) {
      throw new IllegalArgumentException("too many bytes in array");
    }
    this.data = data;
  }

  public int getNumOfBytes() {
    return this.data.length;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ByteContainer that = (ByteContainer) o;

    if (!Arrays.equals(data, that.data)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }
}
