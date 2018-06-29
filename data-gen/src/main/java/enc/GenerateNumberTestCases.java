package enc;

import static java.lang.System.out;

import java.io.IOException;
import java.util.Random;

import org.apache.parquet.bytes.HeapByteBufferAllocator;
import org.apache.parquet.column.values.delta.DeltaBinaryPackingValuesWriterForInteger;
import org.apache.parquet.column.values.delta.DeltaBinaryPackingValuesWriterForLong;
import org.apache.parquet.column.values.plain.PlainValuesWriter;

public class GenerateNumberTestCases {
    private static final HeapByteBufferAllocator A = new HeapByteBufferAllocator();

    private static void printLine() {
        out.println("===============================================\n");
    }

    private static void printBytes(byte[] bytes) {
        out.print("{\ndata: []byte{");
        for (byte b : bytes) {
            out.printf("0x%02X, ", b);
        }
        out.println("},");
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
    
    private static void genInt32s() throws IOException {
        PlainValuesWriter w = new PlainValuesWriter(10000, 10000,  A);
        
        int[] values = new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -100, 234};
        for (int v: values) {
        	out.print(v + ", ");
        	w.writeInteger(v);
        }
        out.println();

        printBytes(w.getBytes().toByteArray());
        printLine();
    }
    
    private static void genDeltaInt32s(int... values) throws IOException {
        DeltaBinaryPackingValuesWriterForInteger w = new DeltaBinaryPackingValuesWriterForInteger(128, 8, 10000, 10000, A);
        for (int v: values) {
        	w.writeInteger(v);
        }
        
        printBytes(w.getBytes().toByteArray());
    
        out.print("decoded: []interface{} {");
        for (int v: values) {
        	out.print("int32(" + v + "), ");
        }
        out.println("},\n},\n");      
    }
    
    private static void genInt64s() throws IOException {
        PlainValuesWriter w = new PlainValuesWriter(10000, 10000,  A);
        
        long[] values = new long[] {Long.MIN_VALUE, Long.MAX_VALUE, 0, -100, 234};
        for (long v: values) {
        	out.print(v + ", ");
        	w.writeLong(v);
        }
        out.println();

        printBytes(w.getBytes().toByteArray());
        printLine();
    }
    
    private static void genDeltaInt64s(long... values) throws IOException {
        DeltaBinaryPackingValuesWriterForLong w = new DeltaBinaryPackingValuesWriterForLong(128, 8, 10000, 10000, A);
        for (long v: values) {
        	w.writeLong(v);
        }
        
        printBytes(w.getBytes().toByteArray());
    
        out.print("decoded: []interface{} {");
        for (long v: values) {
        	out.print("int64(" + v + "), ");
        }
        out.println("},\n},\n");      
    }
    
    public static int[] rangeInt(int s, int e) {
    	int[] r = new int[e-s+1];
    	for (int i = s; i <= e; i++) {
    		r[i-s] = i;
    	}
    	return r;
    }
    
    public static int[] randInts(int n) {
    	Random rnd = new Random(123);
    	int[] r = new int[n];
    	for (int i = 0; i < n; i++) {
    		r[i] = rnd.nextInt(40) + 100;
    	}
    	return r;
    }

    public static long[] rangeLong(int s, int e) {
    	long[] r = new long[e-s+1];
    	for (int i = s; i <= e; i++) {
    		r[i-s] = i;
    	}
    	return r;
    }
    
    public static void main(String[] args) throws IOException {
    	genFloats();
    	genDoubles();
    	genInt32s();
    	genInt64s();
    	
    	genDeltaInt32s(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -100, 234);
    	genDeltaInt32s(200_003, 200_001, 200_002, 200_003, 200_002, 200_001, 200_000);
    	genDeltaInt32s(rangeInt(1, 20));
    	genDeltaInt32s(randInts(200));
    	
    	genDeltaInt64s(Long.MIN_VALUE, Long.MAX_VALUE, 0, -100, 234);
    	genDeltaInt64s(rangeLong(-7, 9));
    	genDeltaInt64s(rangeLong(1000, 1200));
    }
} 
