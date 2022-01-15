package main

import (
	"fmt"
	"math"
)

//数值常量
const (
	// Big 将 1 左移 100 位来创建一个非常大的数字
	// 即这个数的二进制是 1 后面跟着 100 个 0
	Big = 1 << 100
	// Small 再往右移 99 位，即 Small = 1 << 1，或者说 Small = 2
	Small = Big >> 99
)

//类型转换
func main() {
	var x, y int = 3, 4
	//float64类型
	//表达式 T(v) 将值 v 转换为类型 T
	//float64()是内建函数-可以不用导入
	f := math.Sqrt(float64(x*x + y*y))
	//var z uint 无符号整形
	z := uint(f)
	fmt.Println(x, y, f, z)

	//注意: 常量不能用 := 语法声明
	const SUFFIX = ".mp4"
	fmt.Println("h265", SUFFIX)

	fmt.Println(needInt(Small))
	fmt.Println(needFloat(Small))
	fmt.Println(needFloat(Big))
}

func needInt(x int) int { return x*10 + 1 }
func needFloat(x float64) float64 {
	return x * 0.1
}
