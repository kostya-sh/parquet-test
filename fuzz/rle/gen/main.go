package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	data := [][]byte{
		// Single RLE run: 1-bit per value, 10 x 0
		[]byte{0x14, 0x00},
		// Single RLE run: 20-bits per value, 300x1
		[]byte{0xD8, 0x04, 0x01, 0x00, 0x00},
		// 2 RLE runs: 1-bit per value, 10x0, 9x1
		[]byte{0x14, 0x00, 0x12, 0x01},
		// 1 bit-packed run: 3 bits per value, 0,1,2,3,4,5,6,7
		[]byte{0x03, 0x88, 0xC6, 0xFA},
		// RLE run, bit packed run, RLE run: 2 bits per 8x1, 0, 1, 2, 3, 1, 2, 1, 0, 10x2
		[]byte{0x10, 0x01, 0x03, 0xE4, 0x19, 0x14, 0x02},
		// 1 bit packed run
		[]byte{7, 136, 70, 68, 35, 162, 17, 209, 136, 104},
		// unpadded bit-packed encoding
		// from github.com/Parquet/parquet-compatibility/parquet-testdata/impala/1.1.1-NONE/nation.impala.parquet
		[]byte{9, 32, 136, 65, 138, 57, 40, 169, 197, 154, 123, 48, 202, 73, 171, 189, 24},
	}

	for i, d := range data {
		if err := ioutil.WriteFile(fmt.Sprintf("corpus/%d", i), d, 0644); err != nil {
			panic(err)
		}
	}
}
