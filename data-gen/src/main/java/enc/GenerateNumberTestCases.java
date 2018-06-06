package enc;

import static java.lang.System.out;

import java.io.IOException;

import org.apache.parquet.bytes.HeapByteBufferAllocator;
import org.apache.parquet.column.values.plain.PlainValuesWriter;

public class GenerateNumberTestCases {
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

    private static void genFloats() throws IOException {
        PlainValuesWriter w = new PlainValuesWriter(10000, 10000,  A);
        
        float[] values = new float[] {/* Float.NaN, */ Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 0.0f, 1.0f, -1.0f};
        for (float v: values) {
        	out.print(v + ", ");
        	w.writeFloat(v);
        }
        out.println();

        printBytes(w.getBytes().toByteArray());
        printLine();
    }

    private static void genDoubles() throws IOException {
        PlainValuesWriter w = new PlainValuesWriter(10000, 10000,  A);
        
        double[] values = new double[] {Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0, 1.5, -1.25};
        for (double v: values) {
        	out.print(v + ", ");
        	w.writeDouble(v);
        }
        out.println();

        printBytes(w.getBytes().toByteArray());
        printLine();
    }

    public static void main(String[] args) throws IOException {
    	genFloats();
    	genDoubles();
    }
} 
