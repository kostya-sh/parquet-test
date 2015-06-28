package gen;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import parquet.example.Paper;
import parquet.example.data.Group;
import parquet.hadoop.ParquetWriter;
import parquet.hadoop.example.GroupWriteSupport;

public class DremelPaperExampleGen {
    public static void main(String[] args) throws Exception {
        new File(args[0]).delete();
        Path p = new Path(args[0]);

        GroupWriteSupport ws = new GroupWriteSupport();

        Configuration conf = new Configuration();
        GroupWriteSupport.setSchema(Paper.schema, conf);
        ParquetWriter<Group> w = new ParquetWriter<Group>(p, conf, ws);
        w.write(Paper.r1);
        w.write(Paper.r2);
        w.close();
    }
}
