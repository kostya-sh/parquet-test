package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	data := [][]byte{
		// float32(math.Inf(-1)), float32(math.Inf(+1)), float32(0.0), float32(1.0), float32(-1.0)x
		[]byte{
			0x00, 0x00, 0x80, 0xFF,
			0x00, 0x00, 0x80, 0x7F,
			0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x80, 0x3F,
			0x00, 0x00, 0x80, 0xBF,
		},
	}

	for i, d := range data {
		if err := ioutil.WriteFile(fmt.Sprintf("corpus/%d", i), d, 0644); err != nil {
			panic(err)
		}
	}
}
