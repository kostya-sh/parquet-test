#!/bin/bash

BASEDIR=`dirname $0`
INPUTDIR=$BASEDIR/input
OUTPUTDIR=$BASEDIR/output
PARQUETEUR=parqueteur

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
    if [ ! -f $expected_out ] ; then
        echo "ERROR: file $expected_out with the expected output doesn't exist"
        return 2
    fi

    local actual_out=`mktemp`.out

    $PARQUETEUR $cmd $options $input 1>$actual_out
    local e=$?
    if [ $e -ne 0 ] ; then
        echo "FAIL: non-zero return code $e"
        return 3
    fi

    diff -u $actual_out $expected_out
    e=$?
    if [ $e -ne 0 ] ; then
        echo "FAIL"
        return $e
    else
        echo "PASS"
        return 0
    fi
}

run_test Min1 meta -json
run_test Min1 schema

run_test DremelPaperExample meta -json
run_test DremelPaperExample schema

run_test AllTypes1000 dump -c=RequiredBoolean
