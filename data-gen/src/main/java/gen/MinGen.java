package gen;

import java.io.File;

import models.Min;

import org.apache.hadoop.fs.Path;

import parquet.avro.AvroParquetWriter;

public class MinGen {
    public static void main(String[] args) throws Exception {
        new File(args[0]).delete();
        Path p = new Path(args[0]);

        AvroParquetWriter<Min> parquetOut = new AvroParquetWriter<Min>(p, Min.getClassSchema());
        parquetOut.write(new Min(true));
        parquetOut.close();
    }
}
