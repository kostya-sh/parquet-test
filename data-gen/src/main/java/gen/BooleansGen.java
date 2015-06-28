package gen;

import static parquet.schema.Type.Repetition.OPTIONAL;
import static parquet.schema.Type.Repetition.REPEATED;
import static parquet.schema.Type.Repetition.REQUIRED;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import parquet.example.data.Group;
import parquet.example.data.simple.SimpleGroup;
import parquet.hadoop.ParquetWriter;
import parquet.hadoop.example.GroupWriteSupport;
import parquet.schema.MessageType;
import parquet.schema.PrimitiveType;
import parquet.schema.PrimitiveType.PrimitiveTypeName;

public class BooleansGen {
    public static void main(String[] args) throws Exception {
        new File(args[0]).delete();
        Path p = new Path(args[0]);

        MessageType schema =
                new MessageType("Booleans",
                    new PrimitiveType(REQUIRED, PrimitiveTypeName.BOOLEAN, "Required"),
                    new PrimitiveType(OPTIONAL, PrimitiveTypeName.BOOLEAN, "Optional"),
                    new PrimitiveType(REPEATED, PrimitiveTypeName.BOOLEAN, "Repeated"));
        // from minimal to full
        SimpleGroup r1 = new SimpleGroup(schema);
        r1.add("Required", true);

        SimpleGroup r2 = new SimpleGroup(schema);
        r2.add("Required", true);
        r2.add("Optional", true);

        SimpleGroup r3 = new SimpleGroup(schema);
        r3.add("Required", false);
        r3.add("Optional", true);
        r3.add("Repeated", true);

        SimpleGroup r4 = new SimpleGroup(schema);
        r4.add("Required", true);
        r4.add("Optional", false);
        r4.append("Repeated", true)
          .append("Repeated", true)
          .append("Repeated", false);


        GroupWriteSupport ws = new GroupWriteSupport();

        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(schema, conf);
        ParquetWriter<Group> w = new ParquetWriter<Group>(p, conf, ws);
        w.write(r1);
        w.write(r2);
        w.write(r3);
        w.write(r4);
        w.close();
    }
}
