package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a growing byte array.
 *
 * @author: ${USER} Date: 18.01.13 Time: 18:20
 */
public class BinaryBlocks {

    private final int blockSize;
    private int length;
    private List<byte[]> blocks = new ArrayList<>();
    public static final int DEFAULT_BLOCK_SIZE = 32768;

    public BinaryBlocks(int blockSize) {
        this.blockSize = blockSize;
    }

    public BinaryBlocks() {
        this(DEFAULT_BLOCK_SIZE);
    }

    private static class BlockInfo {
        private final int blockNumber;
        private final int blockOffset;

        private BlockInfo(int blockNumber, int blockOffset) {
            this.blockNumber = blockNumber;
            this.blockOffset = blockOffset;
        }

        public int getBlockNumber() {
            return blockNumber;
        }

        public int getBlockOffset() {
            return blockOffset;
        }
    }

    public int getLength() {
        return length;
    }

    private BlockInfo calcBlockInfo(int position) {
        if (position == -1) {
            return new BlockInfo(-1, 0);
        }
        int block = position / this.blockSize;
        int offset = position - (this.blockSize * block);
        return new BlockInfo(block, offset);
    }

    private byte[] readInt(int fromIndex, int toIndex) {
        final byte[] dest = new byte[(toIndex - fromIndex) + 1];

        BlockInfo fromBlock = calcBlockInfo(fromIndex);
        BlockInfo toBlock = calcBlockInfo(toIndex);
        int destIndex = 0;
        for (int blockIndex = fromBlock.getBlockNumber(); blockIndex <= toBlock.getBlockNumber();
             blockIndex++) {
            int from = 0;
            int to = this.blockSize - 1;
            if (blockIndex == fromBlock.getBlockNumber()) {
                /* First block */
                from = fromBlock.getBlockOffset();
            }
            if (blockIndex == toBlock.getBlockNumber()) {
                /* Last block */
                to = toBlock.getBlockOffset();
            }

            final byte[] srcBlock = this.blocks.get(blockIndex);
            System.arraycopy(srcBlock, from, dest, destIndex, (to - from) + 1);

            destIndex = destIndex + (to - from);
        }
        return dest;
    }

    public byte[] read(int fromIndex, int toIndex) {
        if (toIndex < fromIndex) throw new IllegalArgumentException("toIndex<fromIndex");
        if (toIndex >= this.length) throw new IllegalArgumentException("toIndex>=this.length");
        return readInt(fromIndex, toIndex);
    }

    private void append(byte[] data, int dataOffset) {
        int dataLength = data.length - dataOffset;
        BlockInfo fromBlock = calcBlockInfo(this.length);
        BlockInfo toBlock = calcBlockInfo((this.length + dataLength) - 1);
        int srcIndex = dataOffset;
        for (int blockIndex = fromBlock.getBlockNumber(); blockIndex <= toBlock.getBlockNumber();
             blockIndex++) {
            int from = 0;
            int to = this.blockSize - 1;
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
                this.blocks.add(blockIndex, destBlock);

            } else {
                destBlock = this.blocks.get(blockIndex);
            }

            System.out.println(MessageFormat
                    .format("{0}:{1}:{2}:{3}:{4}", data, srcIndex, destBlock, from,
                            (to - from) + 1));
            System.arraycopy(data, srcIndex, destBlock, from, (to - from) + 1);

            srcIndex = srcIndex + (to - from);
        }
        this.length = this.length + dataLength;
    }

    private void replace(int destIndex, byte[] src, int srcOffset, int length) {
        BlockInfo fromBlock = calcBlockInfo(destIndex);
        BlockInfo toBlock = calcBlockInfo((destIndex + length) - 1);

        int srcIndex = srcOffset;
        for (int blockIndex = fromBlock.getBlockNumber(); blockIndex <= toBlock.getBlockNumber();
             blockIndex++) {
            int from = 0;
            int to = this.blockSize - 1;
            if (blockIndex == fromBlock.getBlockNumber()) {
                /* First block */
                from = fromBlock.getBlockOffset();
            }
            if (blockIndex == toBlock.getBlockNumber()) {
                /* Last block */
                to = toBlock.getBlockOffset();
            }

            final byte[] destBlock = this.blocks.get(blockIndex);
            System.arraycopy(src, srcIndex, destBlock, from, (to - from) + 1);

            srcIndex = srcIndex + (to - from);
        }
    }

    public boolean setData(int targetIndex, byte[] src, boolean allowReplace) {
        final boolean needsOverwrite = (targetIndex < this.length);
        if (needsOverwrite && (!allowReplace)) return false;

        final int overwriteLen;
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

    private void cropBytesInt(int numBytes) {
        BlockInfo oldBlock = calcBlockInfo(this.length - 1);
        this.length = this.length - numBytes;
        BlockInfo newBlock = calcBlockInfo(this.length - 1);

        for (int removeBlock = oldBlock.getBlockNumber(); removeBlock > newBlock.getBlockNumber();
             removeBlock--) {
            this.blocks.remove(removeBlock);
        }
    }

    public void cropBytes(int numBytes) {
        if (numBytes > this.length) throw new IllegalArgumentException("numBytes>this.length");
        cropBytesInt(numBytes);
    }
}
