package main

import (
	"fmt"
	"math"
)

//声明接口
type Abser interface {
	//这是接口里的方法
	Abs() float64
}

func main() {
	var a Abser
	f := MyFloat(-math.Sqrt2)
	v := Vertex{3, 4}

	a = f  // a MyFloat 实现了 Abser
	a = &v // a *Vertex 实现了 Abser

	// 下面一行，v 是一个 Vertex（而不是 *Vertex）
	// 所以没有实现 Abser。
	//a = v

	fmt.Println(a.Abs()) //实际调用Vertex的abs

	a = f
	fmt.Println(a.Abs()) //实际调用MyFloat的abs
}

type MyFloat float64

//MyFloat实现了Abs()方法，也就是说它是接口Abser的子类
func (f MyFloat) Abs() float64 {
	if f < 0 {
		return float64(-f)
	}
	return float64(f)
}

type Vertex struct {
	X, Y float64
}

// Abs Vertex实现了Abs()方法，也就是说它是接口Abser的子类
func (v *Vertex) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}
