package enc;

import static java.lang.System.out;

import java.io.IOException;

import org.apache.parquet.column.values.rle.RunLengthBitPackingHybridEncoder;

public class GenerateRLETestCases {
    private static void printLine() {
        out.println("===============================================\n");
    }

    private static void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            out.printf("0x%02X ", b);
        }
        out.println();
    }

    private static void genSingleRLERun_10_Zeroes_1_bit() throws IOException {
        out.println("Single RLE run: 1-bit per value, 10x0");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(
                1, 10, 10000);
        for (int i = 0; i < 10; i++) {
            e.writeInt(0);
        }
        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    private static void genSingleRLERun_300_Ones_20_bit() throws IOException {
        out.println("Single RLE run: 20-bits per value, 300x1");
        RunLengthBitPackingHybridEncoder e = new RunLengthBitPackingHybridEncoder(
                20, 10, 10000);
        for (int i = 0; i < 300; i++) {
            e.writeInt(1);
        }
        printBytes(e.toBytes().toByteArray());
        printLine();
    }

    public static void main(String[] args) throws IOException {
        genSingleRLERun_10_Zeroes_1_bit();
        genSingleRLERun_300_Ones_20_bit();
    }
}
