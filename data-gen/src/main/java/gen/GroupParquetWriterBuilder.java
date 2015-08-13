package gen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.api.WriteSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;

class GroupParquetWriterBuilder extends ParquetWriter.Builder<Group, GroupParquetWriterBuilder> {
    protected GroupParquetWriterBuilder(Path file) {
        super(file);
    }

    @Override
    protected GroupParquetWriterBuilder self() {
        return this;
    }

    @Override
    protected WriteSupport<Group> getWriteSupport(Configuration conf) {
        return new GroupWriteSupport();
    }
}
