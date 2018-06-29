package enc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.parquet.bytes.BytesUtils;

public class GenerateMiscTestCases {
    private static void printBytes(byte[] bytes) {
    	System.out.print("{[]byte{");
        for (byte b : bytes) {
            System.out.printf("0x%02X, ", b);
        }
        System.out.print("}");
    }
    
    public static void zigZagVarInt(int n) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BytesUtils.writeZigZagVarInt(n, out);
    	printBytes(out.toByteArray());
    	System.out.println(", " + n + "},");
    }

    public static void zigZagVarLong(long n) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BytesUtils.writeZigZagVarLong(n, out);
    	printBytes(out.toByteArray());
    	System.out.println(", " + n + "},");
    }
    
	public static void main(String[] args) throws IOException {
		zigZagVarInt(0);
		zigZagVarInt(Integer.MAX_VALUE);
		zigZagVarInt(Integer.MIN_VALUE);
		zigZagVarInt(123456);
		zigZagVarInt(-654321);

		zigZagVarLong(0);
		zigZagVarLong(Long.MAX_VALUE);
		zigZagVarLong(Long.MIN_VALUE);
		zigZagVarLong(123456);
		zigZagVarLong(-654321);

	}
}
