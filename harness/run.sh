#!/bin/bash

BASEDIR=`dirname $0`
INPUTDIR=$BASEDIR/input
OUTPUTDIR=$BASEDIR/output
PARQUETEUR=parqueteur
if [ "$1" == "create" ] ; then
    CREATE=yes
fi

# run_test <file name from input dir> <parqueteur command> <command options>
function run_test {
    local file=$1
    local cmd=$2
    shift
    shift
    local options=$@

    echo $cmd $options $file

    local input=$INPUTDIR/$file.parquet
    if [ ! -f $input ] ; then
        echo "ERROR: input file $input doesn't exist"
        return 1
    fi

    local opts=`echo $options | tr -d '[[:space:]]'`
    local expected_out=$OUTPUTDIR/$cmd/$file$opts.out
    if [ ! "$CREATE" == "yes" -a ! -f $expected_out ] ; then
        echo "ERROR: file $expected_out with the expected output doesn't exist"
        return 2
    fi

    local actual_out
    if [ "$CREATE" == "yes" ] ; then
        actual_out=$expected_out
        mkdir -p `dirname $actual_out`
    else
        actual_out=`mktemp -t parquet-test`.out
    fi

    $PARQUETEUR $cmd $options $input 1>$actual_out
    local e=$?
    if [ $e -ne 0 ] ; then
        echo "FAIL: non-zero return code $e"
        return 3
    fi

    if [ ! "$CREATE" == "yes" ] ; then
        diff -u $actual_out $expected_out
        e=$?
        if [ $e -ne 0 ] ; then
            echo "FAIL"
            return $e
        else
            echo "PASS"
            return 0
        fi
    fi
}

run_test Min1 meta -json
run_test DremelPaperExample meta -json
run_test AllTypes1000 meta -json
run_test AllTypes1000_V2 meta -json
run_test AllTypesDict meta -json
run_test AllTypesDict_V2_GZIP meta -json


run_test Min1 schema
run_test DremelPaperExample schema
run_test AllTypes1000 schema
run_test AllTypesDict schema


run_test AllTypes1000 dump -c=RequiredBoolean
run_test AllTypes1000 dump -c=RequiredBinary
run_test AllTypes1000 dump -c=RequiredFixed10
run_test AllTypes1000 dump -c=RequiredFloat
run_test AllTypes1000 dump -c=RequiredDouble
run_test AllTypes1000 dump -c=RequiredInt32
run_test AllTypes1000 dump -c=RequiredInt64
run_test AllTypes1000 dump -c=RequiredInt96

run_test AllTypes1000_V2 dump -c=RequiredBoolean
run_test AllTypes1000_V2 dump -c=RequiredBinary
run_test AllTypes1000_V2 dump -c=RequiredFixed10
run_test AllTypes1000_V2 dump -c=RequiredFloat
run_test AllTypes1000_V2 dump -c=RequiredDouble
run_test AllTypes1000_V2 dump -c=RequiredInt32
#run_test AllTypes1000_V2 dump -c=RequiredInt64
run_test AllTypes1000_V2 dump -c=RequiredInt96

run_test DremelPaperExample dump -c=DocId -levels
run_test DremelPaperExample dump -c=Links.Backward -levels
run_test DremelPaperExample dump -c=Links.Forward -levels
run_test DremelPaperExample dump -c=Name.Language.Code -levels
run_test DremelPaperExample dump -c=Name.Language.Country -levels
run_test DremelPaperExample dump -c=Name.Url -levels

run_test AllTypesDict dump -c=ByteArray
run_test AllTypesDict dump -c=Float
run_test AllTypesDict dump -c=Double
run_test AllTypesDict dump -c=Int32
run_test AllTypesDict dump -c=Int64
run_test AllTypesDict dump -c=Int96


run_test AllTypes1000 pages -c=RequiredBoolean -json
run_test AllTypes1000 pages -c=RequiredBinary -json

run_test DremelPaperExample pages -c=Name.Language.Code -json

run_test AllTypesDict pages -c=ByteArray -json

run_test AllTypesDict_V2_GZIP pages -c=ByteArray -json

run_test AllTypesDict_V2 pages -c=ByteArray -json


run_test AllTypesDict csv
run_test AllTypesDict_GZIP csv
run_test AllTypesDict_V2 csv
run_test AllTypesDict_V2_GZIP csv

run_test ext/parquet-compatibility/1.1.1-NONE_nation.impala csv
run_test ext/parquet-compatibility/1.1.1-GZIP_nation.impala csv
run_test ext/parquet-compatibility/1.1.1-SNAPPY_nation.impala csv
run_test ext/parquet-compatibility/1.1.1-NONE_customer.impala csv
run_test ext/parquet-compatibility/1.1.1-GZIP_customer.impala csv
run_test ext/parquet-compatibility/1.1.1-SNAPPY_customer.impala csv

run_test ext/kylo/userdata1 csv
run_test ext/kylo/userdata2 csv
run_test ext/kylo/userdata3 csv
run_test ext/kylo/userdata4 csv
run_test ext/kylo/userdata5 csv

run_test ext/sqlite-parquet-vtable/99-rows-1 csv
run_test ext/sqlite-parquet-vtable/99-rows-10 csv
run_test ext/sqlite-parquet-vtable/99-rows-99 csv
run_test ext/sqlite-parquet-vtable/99-rows-nulls-1 csv
run_test ext/sqlite-parquet-vtable/99-rows-nulls-10 csv
run_test ext/sqlite-parquet-vtable/99-rows-nulls-99 csv
#run_test ext/sqlite-parquet-vtable/codecs/brotli csv
run_test ext/sqlite-parquet-vtable/codecs/gzip csv
#run_test ext/sqlite-parquet-vtable/codecs/snappy csv
run_test ext/sqlite-parquet-vtable/unsupported-decimal-10-0- csv
run_test ext/sqlite-parquet-vtable/unsupported-null csv
run_test ext/sqlite-parquet-vtable/unsupported-uint16 csv
run_test ext/sqlite-parquet-vtable/unsupported-uint64 csv
run_test ext/sqlite-parquet-vtable/unsupported-uint8 csv
