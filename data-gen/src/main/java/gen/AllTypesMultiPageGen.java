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
import org.apache.parquet.column.ParquetProperties.WriterVersion;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.NanoTime;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.io.api.Binary;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.PrimitiveType;

public class AllTypesMultiPageGen {
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

        CompressionCodecName codec = CompressionCodecName.UNCOMPRESSED;
        if (args.length > 1) {
        	codec = CompressionCodecName.valueOf(args[1].toUpperCase());
        }
        WriterVersion version = WriterVersion.PARQUET_1_0;
        if (args.length > 2) {
        	version = WriterVersion.valueOf(args[2].toUpperCase());
        }
        
        MessageType schema = new MessageType("AllTypes",
                new PrimitiveType(REQUIRED, BINARY, "RequiredBinary"),
                new PrimitiveType(REQUIRED, BOOLEAN, "RequiredBoolean"),
                new PrimitiveType(REQUIRED, DOUBLE, "RequiredDouble"),
                new PrimitiveType(REQUIRED, FIXED_LEN_BYTE_ARRAY, 10, "RequiredFixed10"),
                new PrimitiveType(REQUIRED, FLOAT, "RequiredFloat"),
                new PrimitiveType(REQUIRED, INT32, "RequiredInt32"),
                new PrimitiveType(REQUIRED, INT64, "RequiredInt64"),
                new PrimitiveType(REQUIRED, INT96, "RequiredInt96"));

        Random rnd = new Random();

        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(schema, conf);
		ParquetWriter<Group> w = new GroupParquetWriterBuilder(p)
                .withConf(conf)
                .withWriterVersion(version)
                .withPageSize(64)
                .withDictionaryEncoding(false)
                .withRowGroupSize(1024*1024)
                .withCompressionCodec(codec)
                .build();

        // NOTE: Do not change field initialization order and add new fields to the end!
        for (int i = 0; i < 1000; i++) {
            rnd.setSeed(i);

            SimpleGroup r = new SimpleGroup(schema);
            r.add("RequiredBinary", Binary.fromConstantByteArray(randomBinary(rnd, rnd.nextInt(10) + 5)));
            // System.err.println(new String(r.getBinary("RequiredBinary", 0).getBytes()));
            r.add("RequiredBoolean", rnd.nextBoolean());
            // System.err.println(r.getBoolean("RequiredBoolean", 0));
            r.add("RequiredDouble", rnd.nextDouble() + 1);
            // System.err.println(r.getDouble("RequiredDouble", 0));
            r.add("RequiredFixed10", Binary.fromConstantByteArray(randomBinary(rnd, 10)));
            // System.err.println(r.getBinary("RequiredFixed10", 0).toStringUsingUTF8());
            r.add("RequiredFloat", rnd.nextFloat() + 1);
            // System.err.println(r.getFloat("RequiredFloat", 0));
            r.add("RequiredInt32", rnd.nextInt());
            // System.err.println(r.getInteger("RequiredInt32", 0));
            r.add("RequiredInt64", rnd.nextLong());
            // System.err.println(r.getLong("RequiredInt64", 0));
            r.add("RequiredInt96", NanoTime.fromBinary(Binary.fromConstantByteArray(randomBinary(rnd, 12))));
            // System.err.println(toString(r.getInt96("RequiredInt96", 0).getBytes()));

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
