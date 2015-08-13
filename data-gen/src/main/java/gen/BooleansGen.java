package gen;

import static org.apache.parquet.schema.Type.Repetition.OPTIONAL;
import static org.apache.parquet.schema.Type.Repetition.REPEATED;
import static org.apache.parquet.schema.Type.Repetition.REQUIRED;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.PrimitiveType;
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName;

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


        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(schema, conf);
        ParquetWriter<Group> w = new GroupParquetWriterBuilder(p).withConf(conf).build();
        w.write(r1);
        w.write(r2);
        w.write(r3);
        w.write(r4);
        w.close();
    }
}
