all: harness/input/AllTypes1000.parquet \
	harness/input/AllTypesDict.parquet harness/input/AllTypesDict_GZIP.parquet harness/input/DremelPaperExample.parquet harness/input/Min1.parquet

harness/input/AllTypes1000.parquet: data-gen/src/main/java/gen/AllTypesMultiPageGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesMultiPageGen -Dexec.args=../harness/input/AllTypes1000.parquet

harness/input/AllTypesDict.parquet: data-gen/src/main/java/gen/AllTypesDict.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesDict -Dexec.args=../harness/input/AllTypesDict.parquet

harness/input/AllTypesDict_GZIP.parquet: data-gen/src/main/java/gen/AllTypesDict.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.AllTypesDict "-Dexec.args=../harness/input/AllTypesDict_GZIP.parquet GZIP"

harness/input/DremelPaperExample.parquet: data-gen/src/main/java/gen/DremelPaperExampleGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.DremelPaperExampleGen -Dexec.args="../harness/input/DremelPaperExample.parquet"

harness/input/Min1.parquet: data-gen/src/main/java/gen/MinGen.java
	cd data-gen; mvn exec:java -Dexec.mainClass=gen.MinGen -Dexec.args="../harness/input/Min1.parquet"


