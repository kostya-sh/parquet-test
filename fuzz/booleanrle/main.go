package booleanrle

import "github.com/kostya-sh/parquet-go/parquet"

func Fuzz(data []byte) int {
	return parquet.FuzzBooleanRLE(data)
}