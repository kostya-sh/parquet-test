package gen;

import java.io.File;

import models.Min;

import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;

public class MinGen {
    public static void main(String[] args) throws Exception {
        new File(args[0]).delete();
        Path p = new Path(args[0]);

        ParquetWriter<Min> parquetOut = AvroParquetWriter.<Min>builder(p)
            .withSchema(Min.getClassSchema())
            .build();
        parquetOut.write(new Min(true));
        parquetOut.close();
    }
}
