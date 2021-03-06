package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	data := [][]byte{
		// -2147483648, 2147483647, 0, -100, 234
		[]byte{
			0x80, 0x01, 0x08, 0x05, 0xFF, 0xFF, 0xFF, 0xFF, 0x0F, 0xFD,
			0xFF, 0xFF, 0xFF, 0x0F, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0xFE, 0xFF, 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00,
			0x9B, 0xFF, 0xFF, 0x7F, 0x4D, 0x01, 0x00, 0x80, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		},

		// 200003, 200001, 200002, 200003, 200002, 200001, 200000
		[]byte{
			0x80, 0x01, 0x08, 0x07, 0x86, 0xB5, 0x18, 0x03, 0x02, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7C, 0x05, 0x00, 0x00,
		},

		// 1 ... 20
		[]byte{
			0x80, 0x01, 0x08, 0x14, 0x02, 0x02, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00,
		},

		[]byte{
			0x80, 0x01, 0x08, 0xC8, 0x01, 0xF4, 0x01, 0x41, 0x07, 0x07,
			0x06, 0x07, 0x06, 0x06, 0x07, 0x06, 0x95, 0x93, 0xEB, 0xF4,
			0x90, 0x91, 0x2A, 0x95, 0x1D, 0x60, 0x67, 0x08, 0x66, 0x30,
			0x26, 0xD5, 0x63, 0x42, 0x04, 0x1D, 0x52, 0x1C, 0x4E, 0xC9,
			0x42, 0x92, 0x61, 0x3E, 0x24, 0x48, 0xCE, 0xA2, 0xC3, 0x8E,
			0x87, 0xC8, 0x47, 0x37, 0x80, 0x7E, 0xA3, 0x58, 0xAA, 0x53,
			0x62, 0xA4, 0x30, 0x3B, 0xC3, 0xEE, 0x11, 0x39, 0x61, 0x8C,
			0x62, 0x68, 0x19, 0xEB, 0x67, 0x9B, 0x0E, 0x8B, 0x66, 0x2C,
			0x46, 0x22, 0xE1, 0x7F, 0xD4, 0x9B, 0xF5, 0x4A, 0x2E, 0x2B,
			0xC8, 0xB0, 0x57, 0xB4, 0xA3, 0xCC, 0x0C, 0xB3, 0xAA, 0x8C,
			0x28, 0x21, 0x4D, 0x71, 0x32, 0x4A, 0xFC, 0x12, 0x5F, 0x9B,
			0xF1, 0x01, 0xFB, 0xA1, 0x0F, 0x0B, 0xA7, 0x53, 0x07, 0xD2,
			0x45, 0x07, 0x07, 0x06, 0x06, 0x06, 0x06, 0x07, 0x06, 0xA6,
			0xD1, 0x25, 0x43, 0x1B, 0x8C, 0x62, 0x24, 0x0E, 0x47, 0xF8,
			0x71, 0xC0, 0x6A, 0x11, 0x4A, 0x4A, 0x75, 0x9A, 0xA8, 0x6A,
			0x96, 0x55, 0x46, 0x22, 0x0C, 0x8D, 0x2C, 0x60, 0x58, 0x1F,
			0x23, 0xAF, 0xA1, 0x32, 0x08, 0x46, 0x75, 0x76, 0x79, 0x3A,
			0x2A, 0x9A, 0x03, 0xDD, 0xF5, 0x80, 0xFE, 0xD0, 0xD4, 0xA6,
			0x7B, 0xE3, 0x7D, 0x6D, 0xB7, 0xD2, 0x99, 0x0E, 0x8B, 0x66,
			0x2C, 0x46, 0x22,
		},
	}

	for i, d := range data {
		if err := ioutil.WriteFile(fmt.Sprintf("corpus/%d", i), d, 0644); err != nil {
			panic(err)
		}
	}
}
