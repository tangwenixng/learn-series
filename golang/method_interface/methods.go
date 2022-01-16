package main

import (
	"fmt"
	"math"
)

type Vertex struct {
	X, Y float64
}

//方法：一类带特殊的[接收者]参数的函数。
//[接收者]位于 func 关键字和方法名之间

//方法只是个带接收者参数的函数。
//所以在go中，方法和函数是不同的概念;但是方法也可以说是函数

// Abs : Go没有类
//该方法独属于结构体Vertex的方法
func (v Vertex) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

// MyFloat 是一个float64的类型
type MyFloat float64

//可以为非结构体类型声明方法
//接收者的类型定义和方法声明必须在同一包内；不能为内建类型声明方法
func (f MyFloat) Abs() float64 {
	if f < 0 {
		return float64(-f)
	}
	return float64(f)
}

func main() {
	v := Vertex{3, 4}
	fmt.Println(v.Abs())

	f := MyFloat(-math.Sqrt2)
	fmt.Println(f.Abs())
}
