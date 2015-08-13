package gen;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.Paper;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;

public class DremelPaperExampleGen {
    public static void main(String[] args) throws Exception {
        new File(args[0]).delete();
        Path p = new Path(args[0]);

        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(Paper.schema, conf);
        ParquetWriter<Group> w = new GroupParquetWriterBuilder(p).withConf(conf).build();
        w.write(Paper.r1);
        w.write(Paper.r2);
        w.close();
    }
}
