package gen;

import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BOOLEAN;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.DOUBLE;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.FIXED_LEN_BYTE_ARRAY;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.FLOAT;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.INT32;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.INT64;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.INT96;
import static org.apache.parquet.schema.Type.Repetition.REQUIRED;

import java.io.File;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.NanoTime;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.io.api.Binary;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.PrimitiveType;

public class AllTypesDict {
    private static byte[] randomBinary(Random rnd, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[i] = (byte) ('A' + rnd.nextInt(60));
        }
        return b;
    }

    public static void main(String[] args) throws Exception {
        new File(args[0]).delete();
        Path p = new Path(args[0]);

        MessageType schema = new MessageType("AllTypes",
        		new PrimitiveType(REQUIRED, BOOLEAN, "Boolean"),
                new PrimitiveType(REQUIRED, BINARY, "ByteArray"),
                new PrimitiveType(REQUIRED, FIXED_LEN_BYTE_ARRAY, 10, "FixedLenByteArray"),
                new PrimitiveType(REQUIRED, FLOAT, "Float"),
                new PrimitiveType(REQUIRED, DOUBLE, "Double"),
                new PrimitiveType(REQUIRED, INT32, "Int32"),
                new PrimitiveType(REQUIRED, INT64, "Int64"),
                new PrimitiveType(REQUIRED, INT96, "Int96"));

        Random rnd = new Random();

        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(schema, conf);
        ParquetWriter<Group> w = new GroupParquetWriterBuilder(p)
                .withConf(conf)
                .withPageSize(64)
                .withDictionaryEncoding(true)
                .withRowGroupSize(1024*1024)
                .build();

        // NOTE: Do not change field initialization order and add new fields to the end!
        for (int i = 0; i < 200; i++) {
            rnd.setSeed(i);
            rnd.setSeed(rnd.nextInt(5));

            SimpleGroup r = new SimpleGroup(schema);

            // NOTE: BOOLEAN is not encoded using DICTIONARY encoding
            r.add("Boolean", rnd.nextBoolean());
            // System.err.println(r.getBoolean("Boolean", 0));

            r.add("ByteArray", Binary.fromConstantByteArray(randomBinary(rnd, rnd.nextInt(10) + 5)));
            // System.err.println(new String(r.getBinary("ByteArray", 0).getBytes()));

            // NOTE: FIXED_LEN_BYTE_ARRAY is not encoded using DICTIONARY encoding in PARQUET 1.0.
            // A comment from DefaultV1ValuesWriterFactory:
            //   dictionary encoding was not enabled in PARQUET 1.0
            r.add("FixedLenByteArray", Binary.fromConstantByteArray(randomBinary(rnd, 10)));
            // System.err.println(r.getBinary("FixedByteArray10", 0).toStringUsingUTF8());

            r.add("Float", rnd.nextFloat() + 1);
            // System.err.println(r.getFloat("Float", 0));

            r.add("Double", rnd.nextDouble() + 1);
            // System.err.println(r.getDouble("Double", 0));

            r.add("Int32", rnd.nextInt());
            // System.err.println(r.getInteger("Int32", 0));

            r.add("Int64", rnd.nextLong());
            // System.err.println(r.getLong("Int64", 0));

            r.add("Int96", NanoTime.fromBinary(Binary.fromConstantByteArray(randomBinary(rnd, 12))));
            // System.err.println(toString(r.getInt96("Int96", 0).getBytes()));

            //System.err.println(r);
            w.write(r);
        }
        w.close();
    }

	public static String toString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for(byte b: bytes) {
			if (sb.length() != 0) {
				sb.append(' ');
			}
			sb.append(b);
		}
		return "[" + sb.toString() + "]";
	}
}
