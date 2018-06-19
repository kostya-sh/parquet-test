package gen;

import static org.apache.parquet.schema.OriginalType.UTF8;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.apache.parquet.schema.Type.Repetition.OPTIONAL;
import static org.apache.parquet.schema.Type.Repetition.REPEATED;
import static org.apache.parquet.schema.Type.Repetition.REQUIRED;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.ParquetProperties.WriterVersion;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.PrimitiveType;

public class ByteArraysGen {
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

        MessageType schema =
                new MessageType("ByteArrays",
                    new PrimitiveType(REQUIRED, BINARY, "Required", UTF8),
                    new PrimitiveType(OPTIONAL, BINARY, "Optional", UTF8),
                    new PrimitiveType(REPEATED, BINARY, "Repeated", UTF8),
                    new PrimitiveType(OPTIONAL, BINARY, "Dict", UTF8));
        // from minimal to full
        SimpleGroup r1 = new SimpleGroup(schema);
        r1.add("Required", "r1");
        r1.add("Dict", "parquet");

        SimpleGroup r2 = new SimpleGroup(schema);
        r2.add("Required", "r2");
        r2.add("Optional", "o2");
        r2.add("Dict", "go");

        SimpleGroup r3 = new SimpleGroup(schema);
        r3.add("Required", "r3");
        r3.add("Optional", "o3");
        r3.add("Repeated", "p3_1");
        r3.add("Dict", "parquet");
        
        SimpleGroup r4 = new SimpleGroup(schema);
        r4.add("Required", "r4");
        r4.add("Optional", "o4");
        r4.append("Repeated", "p4_1")
          .append("Repeated", "p4_2")
          .append("Repeated", "p4_3");
        r4.add("Dict", "go");
        
        // and minimal again
        SimpleGroup r5 = new SimpleGroup(schema);
        r5.add("Required", "r5");
        r5.add("Dict", "parquet");
        
        // and full again
        SimpleGroup r6 = new SimpleGroup(schema);
        r6.add("Required", "r6");
        r6.add("Optional", "o6");
        r6.add("Repeated", "p6_1");
        r6.add("Dict", "go");
        
        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(schema, conf);
        ParquetWriter<Group> w = new GroupParquetWriterBuilder(p)
        		.withConf(conf)
        		.withWriterVersion(version)
        		.withCompressionCodec(codec)
        		.build();
        w.write(r1);
        w.write(r2);
        w.write(r3);
        w.write(r4);
        w.write(r5);
        w.write(r6);
        w.close();
    }
}
