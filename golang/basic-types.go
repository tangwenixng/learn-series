package main

import "fmt"
import "math/cmplx"

//Go的基本类型
//bool
//
//string
//
//int  int8  int16  int32  int64
//uint uint8 uint16 uint32 uint64 uintptr
//
//byte // uint8 的别名
//
//rune // int32 的别名
//// 表示一个 Unicode 码点
//
//float32 float64
//
//complex64 complex128

///////////////////////////////////////////

//变量声明可以“分组”成一个语法块。
var (
	ToBe   bool       = false
	MaxInt uint64     = 1<<64 - 1
	z      complex128 = cmplx.Sqrt(-5 + 12i) //复数
)

func main() {
	//%T表示类型
	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)     //Type: bool Value: false
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt) //Type: uint64 Value: 18446744073709551615
	fmt.Printf("Type: %T Value: %v\n", z, z)           //Type: complex128 Value: (2+3i)
}
