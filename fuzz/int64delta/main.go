package int64delta

import "github.com/kostya-sh/parquet-go/parquet"

func Fuzz(data []byte) int {
	return parquet.FuzzInt64DeltaBinaryPacked(data)
}
