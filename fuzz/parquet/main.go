package parquet

import (
	"bytes"
	"math/rand"
	"time"

	"github.com/kostya-sh/parquet-go/parquet"
)

func init() {
	rand.Seed(time.Now().Unix())
}

func readColumn(f *parquet.File, col parquet.Column, batch int) error {
	values := make([]interface{}, batch, batch)
	dLevels := make([]uint16, batch, batch)
	rLevels := make([]uint16, batch, batch)
	for rg, _ := range f.MetaData.RowGroups {
		cr, err := f.NewReader(col, rg)
		if err != nil {
			return err
		}
		for err != parquet.EndOfChunk {
			_, err = cr.Read(values, dLevels, rLevels)
			if err != nil && err != parquet.EndOfChunk {
				return err
			}
		}
	}
	return nil
}

func Fuzz(data []byte) int {
	f, err := parquet.FileFromReader(bytes.NewReader(data))
	if err != nil {
		return 0
	}

	r := 0
	for _, col := range f.Schema.Columns() {
		err = readColumn(f, col, rand.Intn(1000))
		if err != nil {
			r = 1
		}
	}
	return r
}
