all: \
    harness/input/AllTypes1000.parquet \
	harness/input/AllTypesDict.parquet \
	harness/input/AllTypesDict_GZIP.parquet \
	harness/input/AllTypesDict_V2_GZIP.parquet \
	harness/input/DremelPaperExample.parquet \
    harness/input/Min1.parquet

harness/input/AllTypes1000.parquet: data-gen/src/main/java/gen/AllTypesMultiPageGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesMultiPageGen -Dexec.args=../harness/input/AllTypes1000.parquet

harness/input/AllTypes1000_V2.parquet: data-gen/src/main/java/gen/AllTypesMultiPageGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesMultiPageGen "-Dexec.args=../harness/input/AllTypes1000_V2.parquet UNCOMPRESSED PARQUET_2_0"

harness/input/AllTypesDict.parquet: data-gen/src/main/java/gen/AllTypesDict.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesDict -Dexec.args=../harness/input/AllTypesDict.parquet

harness/input/AllTypesDict_GZIP.parquet: data-gen/src/main/java/gen/AllTypesDict.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesDict "-Dexec.args=../harness/input/AllTypesDict_GZIP.parquet GZIP"

harness/input/AllTypesDict_V2.parquet: data-gen/src/main/java/gen/AllTypesDict.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesDict "-Dexec.args=../harness/input/AllTypesDict_V2.parquet UNCOMPRESSED PARQUET_2_0"

harness/input/AllTypesDict_V2_GZIP.parquet: data-gen/src/main/java/gen/AllTypesDict.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesDict "-Dexec.args=../harness/input/AllTypesDict_V2_GZIP.parquet GZIP PARQUET_2_0"

harness/input/DremelPaperExample.parquet: data-gen/src/main/java/gen/DremelPaperExampleGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.DremelPaperExampleGen -Dexec.args="../harness/input/DremelPaperExample.parquet"

harness/input/Min1.parquet: data-gen/src/main/java/gen/MinGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.MinGen -Dexec.args="../harness/input/Min1.parquet"

# tests in parquet-go

ByteArrays:
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.ByteArraysGen -Dexec.args="../../parquet-go/parquet/testdata/ByteArrays.parquet UNCOMPRESSED PARQUET_1_0"

ByteArrays_GZIP:
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.ByteArraysGen -Dexec.args="../../parquet-go/parquet/testdata/ByteArrays_GZIP.parquet GZIP PARQUET_1_0"

ByteArrays_V2:
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.ByteArraysGen -Dexec.args="../../parquet-go/parquet/testdata/ByteArrays_V2.parquet UNCOMPRESSED PARQUET_2_0"

ByteArrays_V2_GZIP:
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.ByteArraysGen -Dexec.args="../../parquet-go/parquet/testdata/ByteArrays_V2_GZIP.parquet GZIP PARQUET_2_0"

ByteArrays_V2_SNAPPY:
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.ByteArraysGen -Dexec.args="../../parquet-go/parquet/testdata/ByteArrays_V2_SNAPPY.parquet SNAPPY PARQUET_2_0"
