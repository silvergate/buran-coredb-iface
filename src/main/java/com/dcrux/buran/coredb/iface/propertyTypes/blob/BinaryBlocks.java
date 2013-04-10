package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a growing byte array.
 *
 * @author: ${USER} Date: 18.01.13 Time: 18:20
 */
public class BinaryBlocks implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2264827570449670855L;
	private final int blockSize;
    private long length;
    private List<byte[]> blocks = new ArrayList<>();
    public static final int DEFAULT_BLOCK_SIZE = 32768;

    public BinaryBlocks(int blockSize) {
        this.blockSize = blockSize;
    }

    public BinaryBlocks() {
        this(DEFAULT_BLOCK_SIZE);
    }

    private static class BlockInfo {
        private final long blockNumber;
        private final long blockOffset;

        private BlockInfo(long blockNumber, long blockOffset) {
            this.blockNumber = blockNumber;
            this.blockOffset = blockOffset;
        }

        public long getBlockNumber() {
            return blockNumber;
        }

        public long getBlockOffset() {
            return blockOffset;
        }
    }

    public long getLength() {
        return length;
    }

    private BlockInfo calcBlockInfo(long position) {
        if (position == -1) {
            return new BlockInfo(-1, 0);
        }
        long block = position / this.blockSize;
        long offset = position - (this.blockSize * block);
        return new BlockInfo(block, offset);
    }

    private byte[] readInt(long fromIndex, long toIndex) {
        final byte[] dest = new byte[(int) (toIndex - fromIndex) + 1];

        BlockInfo fromBlock = calcBlockInfo(fromIndex);
        BlockInfo toBlock = calcBlockInfo(toIndex);
        long destIndex = 0;
        for (long blockIndex = fromBlock.getBlockNumber(); blockIndex <= toBlock.getBlockNumber();
             blockIndex++) {
            long from = 0;
            long to = this.blockSize - 1;
            if (blockIndex == fromBlock.getBlockNumber()) {
                /* First block */
                from = fromBlock.getBlockOffset();
            }
            if (blockIndex == toBlock.getBlockNumber()) {
                /* Last block */
                to = toBlock.getBlockOffset();
            }

            final byte[] srcBlock = this.blocks.get((int) blockIndex);
            System.arraycopy(srcBlock, (int) from, dest, (int) destIndex, (int) (to - from) + 1);

            destIndex = destIndex + (to - from);
        }
        return dest;
    }

    public byte[] read(long fromIndex, long toIndex) {
        if (toIndex < fromIndex) throw new IllegalArgumentException("toIndex<fromIndex");
        if (toIndex >= this.length) throw new IllegalArgumentException("toIndex>=this.length");
        return readInt(fromIndex, toIndex);
    }

    private void append(byte[] data, long dataOffset) {
        long dataLength = data.length - dataOffset;
        BlockInfo fromBlock = calcBlockInfo(this.length);
        BlockInfo toBlock = calcBlockInfo((this.length + dataLength) - 1);
        long srcIndex = dataOffset;
        for (long blockIndex = fromBlock.getBlockNumber(); blockIndex <= toBlock.getBlockNumber();
             blockIndex++) {
            long from = 0;
            long to = this.blockSize - 1;
            if (blockIndex == fromBlock.getBlockNumber()) {
                /* First block */
                from = fromBlock.getBlockOffset();
            }
            if (blockIndex == toBlock.getBlockNumber()) {
                /* Last block */
                to = toBlock.getBlockOffset();
            }

            byte[] destBlock;
            if (this.blocks.size() <= blockIndex) {
                /* We need a new block */
                destBlock = new byte[this.blockSize];
                this.blocks.add((int) blockIndex, destBlock);

            } else {
                destBlock = this.blocks.get((int) blockIndex);
            }

            System.out.println(MessageFormat
                    .format("{0}:{1}:{2}:{3}:{4}", data, srcIndex, destBlock, from,
                            (to - from) + 1));
            System.arraycopy(data, (int) srcIndex, destBlock, (int) from, (int) (to - from) + 1);

            srcIndex = srcIndex + (to - from);
        }
        this.length = this.length + dataLength;
    }

    private void replace(int destIndex, byte[] src, int srcOffset, long length) {
        BlockInfo fromBlock = calcBlockInfo(destIndex);
        BlockInfo toBlock = calcBlockInfo((destIndex + length) - 1);

        long srcIndex = srcOffset;
        for (long blockIndex = fromBlock.getBlockNumber(); blockIndex <= toBlock.getBlockNumber();
             blockIndex++) {
            long from = 0;
            long to = this.blockSize - 1;
            if (blockIndex == fromBlock.getBlockNumber()) {
                /* First block */
                from = fromBlock.getBlockOffset();
            }
            if (blockIndex == toBlock.getBlockNumber()) {
                /* Last block */
                to = toBlock.getBlockOffset();
            }

            final byte[] destBlock = this.blocks.get((int) blockIndex);
            System.arraycopy(src, (int) srcIndex, destBlock, (int) from, (int) (to - from) + 1);

            srcIndex = srcIndex + (to - from);
        }
    }

    public boolean setData(int targetIndex, byte[] src, boolean allowReplace) {
        final boolean needsOverwrite = (targetIndex < this.length);
        if (needsOverwrite && (!allowReplace)) return false;

        final long overwriteLen;
        /* Replace */
        if (needsOverwrite) {
            overwriteLen = (this.length - targetIndex);
            replace(targetIndex, src, 0, overwriteLen);
        } else {
            overwriteLen = 0;
        }

        /* Append */
        if (overwriteLen < src.length) {
            append(src, overwriteLen);
        }

        return true;
    }

    private void cropBytesInt(long numBytes) {
        BlockInfo oldBlock = calcBlockInfo(this.length - 1);
        this.length = this.length - numBytes;
        BlockInfo newBlock = calcBlockInfo(this.length - 1);

        for (long removeBlock = oldBlock.getBlockNumber(); removeBlock > newBlock.getBlockNumber();
             removeBlock--) {
            this.blocks.remove((int) removeBlock);
        }
    }

    public void cropBytes(long numBytes) {
        if (numBytes > this.length) throw new IllegalArgumentException("numBytes>this.length");
        cropBytesInt(numBytes);
    }
}
