package enc;

import static java.lang.System.out;

import java.io.IOException;

import org.apache.parquet.bytes.HeapByteBufferAllocator;
import org.apache.parquet.column.values.rle.RunLengthBitPackingHybridEncoder;

public class GenerateRLETestCases {
    private static final HeapByteBufferAllocator A = new HeapByteBufferAllocator();

    private static void printLine() {
        out.println("===============================================\n");
    }

    private static void printBytes(byte[] bytes) {
        out.print("[]byte{");
        for (byte b : bytes) {
            out.printf("0x%02X, ", b);
        }
        out.println("}");
    }

    private static void genSingleRLERun_10_Zeroes_1_bit() throws IOException {
        out.println("Single RLE run: 1-bit per value, 10x0");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(1, 10, 10000, A);
        for (int i = 0; i < 10; i++) {
            e.writeInt(0);
        }
        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    private static void genSingleRLERun_300_Ones_20_bit() throws IOException {
        out.println("Single RLE run: 20-bits per value, 300x1");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(20, 10, 10000, A);
        for (int i = 0; i < 300; i++) {
            e.writeInt(1);
        }
        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    private static void gen2RLERuns_10_Zeroes_9_Ones_1_bit() throws IOException {
        out.println("2 RLE runs: 1-bit per value, 10x0, 9x1");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(1, 10, 10000, A);
        for (int i = 0; i < 10; i++) {
            e.writeInt(0);
        }
        for (int i = 0; i < 9; i++) {
            e.writeInt(1);
        }
        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    private static void genSingleBitPackedRun_0to7_3bit() throws IOException {
        out.println("1 bit-packed run: 3 bits per value, 0,1,2,3,4,5,6,7");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(3, 10, 10000, A);
        for (int i = 0; i <= 7; i++) {
            e.writeInt(i);
        }
        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    private static void genRLE_BitPacked_RLE_2bit() throws IOException {
        out.println("RLE run, bit packed run, RLE run: 8x1, 0, 1, 2, 3, 3, 2, 1, 0, 10x2");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(2, 10, 10000, A);
        e.writeInt(1);
        e.writeInt(1);
        e.writeInt(1);
        e.writeInt(1);
        e.writeInt(1);
        e.writeInt(1);
        e.writeInt(1);
        e.writeInt(1);

        e.writeInt(0);
        e.writeInt(1);
        e.writeInt(2);
        e.writeInt(3);
        e.writeInt(1);
        e.writeInt(2);
        e.writeInt(1);
        e.writeInt(0);

        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);
        e.writeInt(2);

        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    public static void main(String[] args) throws IOException {
        genSingleRLERun_10_Zeroes_1_bit();
        genSingleRLERun_300_Ones_20_bit();
        gen2RLERuns_10_Zeroes_9_Ones_1_bit();
        genSingleBitPackedRun_0to7_3bit();
        genRLE_BitPacked_RLE_2bit();
    }
} 
