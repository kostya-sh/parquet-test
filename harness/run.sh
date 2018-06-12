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
run_test Min1 schema

run_test DremelPaperExample meta -json
run_test DremelPaperExample schema

run_test AllTypes1000 dump -c=RequiredBoolean
run_test AllTypes1000 dump -c=RequiredBinary
run_test AllTypes1000 dump -c=RequiredFixed10
run_test AllTypes1000 dump -c=RequiredFloat
run_test AllTypes1000 dump -c=RequiredDouble
run_test AllTypes1000 dump -c=RequiredInt32
run_test AllTypes1000 dump -c=RequiredInt64
run_test AllTypes1000 dump -c=RequiredInt96

run_test DremelPaperExample dump -c=DocId -levels
run_test DremelPaperExample dump -c=Links.Backward -levels
run_test DremelPaperExample dump -c=Links.Forward -levels
run_test DremelPaperExample dump -c=Name.Language.Code -levels
run_test DremelPaperExample dump -c=Name.Language.Country -levels
run_test DremelPaperExample dump -c=Name.Url -levels

run_test AllTypes1000 pages -c=RequiredBoolean -json
run_test AllTypes1000 pages -c=RequiredBinary -json
run_test DremelPaperExample pages -c=Name.Language.Code -json
