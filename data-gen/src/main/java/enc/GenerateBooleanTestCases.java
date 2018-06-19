package enc;

import static java.lang.System.out;

import java.io.IOException;

import org.apache.parquet.bytes.HeapByteBufferAllocator;
import org.apache.parquet.column.values.rle.RunLengthBitPackingHybridValuesWriter;

public class GenerateBooleanTestCases {
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

    private static void gen(boolean... values) throws IOException {
        RunLengthBitPackingHybridValuesWriter w = new RunLengthBitPackingHybridValuesWriter(1, 1000, 1000, A);
        
        for (boolean v: values) {
        	out.print(v + ", ");
        	w.writeBoolean(v);
        }
        out.println();

        printBytes(w.getBytes().toByteArray());
        printLine();
    }

    public static void main(String[] args) throws IOException {
    	gen(true, false, true, false, true);
    	gen(true, true, true, true, true, true, true, true);
    	gen(false, false, false, false, false, false, false, false);
    }
} 
