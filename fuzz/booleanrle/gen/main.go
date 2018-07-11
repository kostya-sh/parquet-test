package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	data := [][]byte{
		// true, false, true, false, true
		[]byte{0x02, 0x00, 0x00, 0x00, 0x03, 0x15},

		// true, true, true, true, true, true, true, true
		[]byte{0x02, 0x00, 0x00, 0x00, 0x10, 0x01},
	}

	for i, d := range data {
		if err := ioutil.WriteFile(fmt.Sprintf("corpus/%d", i), d, 0644); err != nil {
			panic(err)
		}
	}
}
