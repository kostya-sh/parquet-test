package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	data := [][]byte{
		[]byte{0x03, 0x05, 0x88, 0x46, 0xAD, 0x7E, 0xDB, 0x1E},
	}

	for i, d := range data {
		if err := ioutil.WriteFile(fmt.Sprintf("corpus/%d", i), d, 0644); err != nil {
			panic(err)
		}
	}
}
