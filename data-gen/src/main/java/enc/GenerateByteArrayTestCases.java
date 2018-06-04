package enc;

import static java.lang.System.out;

import java.io.IOException;

import org.apache.parquet.bytes.HeapByteBufferAllocator;
import org.apache.parquet.column.values.plain.PlainValuesWriter;
import org.apache.parquet.column.values.rle.RunLengthBitPackingHybridEncoder;
import org.apache.parquet.io.api.Binary;

public class GenerateByteArrayTestCases {
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

    private static void genMiscByteArrays() throws IOException {
        PlainValuesWriter w = new PlainValuesWriter(10000, 10000,  A);
        
        for (String s: new String[] {"123", "", "ABCDEF"}) {
        	out.print("[]byte(\"" + s + "\"), ");
        	w.writeBytes(Binary.fromConstantByteArray(s.getBytes()));
        }
        out.println();

        printBytes(w.getBytes().toByteArray());
        printLine();
    }

    public static void main(String[] args) throws IOException {
    	genMiscByteArrays();
    }
} 
