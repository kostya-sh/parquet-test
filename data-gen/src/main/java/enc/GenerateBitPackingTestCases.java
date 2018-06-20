package enc;

import java.io.IOException;
import java.util.Arrays;

import org.apache.parquet.column.values.bitpacking.BytePacker;
import org.apache.parquet.column.values.bitpacking.Packer;

public class GenerateBitPackingTestCases {
    private static void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.printf("0x%02X, ", b);
        }
    }

    private static void printTestCase(BytePacker bp, int[] in) {
        byte[] out = new byte[bp.getBitWidth()];
        bp.pack8Values(in, 0, out, 0);

        // {3, []byte{0x00, 0x00, 0x00}, [8]int32{0, 0, 0, 0, 0, 0, 0, 0}},
        System.out.print("{\n" + bp.getBitWidth() + ", \n[]byte{");
        printBytes(out);
        System.out.print("},\n [8]int32");
        System.out.print(Arrays.toString(in).replace("[", "{")
                .replace("]", "},\n"));
        System.out.println("},");
    }

    private static void generate1() {
        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(1);
        printTestCase(packer, new int[] { 1, 0, 1, 1, 0, 0, 1, 0 });
        printTestCase(packer, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        printTestCase(packer, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });
    }

    private static void generate2() {
        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(2);
        printTestCase(packer, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        printTestCase(packer, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });
        printTestCase(packer, new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
        printTestCase(packer, new int[] { 0, 1, 2, 2, 1, 0, 0, 1 });
    }

    private static void generate3() {
        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(3);
        printTestCase(packer, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        printTestCase(packer, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
        printTestCase(packer, new int[] { 7, 6, 5, 4, 3, 2, 1, 0 });
    }

    private static void generate4() {
        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(4);
        printTestCase(packer, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        printTestCase(packer, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
        printTestCase(packer, new int[] { 7, 6, 5, 4, 3, 2, 1, 0 });
        printTestCase(packer, new int[] { 15, 14, 13, 12, 11, 10, 9, 8 });
    }

    private static void generate(int n) {
        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(n);
        System.out.println("// bit width = " + n);
        printTestCase(packer, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        printTestCase(packer, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
        printTestCase(packer, new int[] { 7, 6, 5, 4, 3, 2, 1, 0 });
        int[] values = new int[8];
        for (int i = (1 << n) - 1, j =0; j < 8; i--, j++) {
        	values[j] = i;
        }
        printTestCase(packer, values);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        generate1();
        generate2();
        generate3();
        generate4();
        for (int i = 5; i <= 32; i++) {
        	generate(i);
        }
    }
}
