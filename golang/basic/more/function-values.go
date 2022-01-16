package main

import (
	"fmt"
	"math"
)

//函数的参数可以是另一个函数
func compute(fn func(float64, float64) float64) float64 {
	return fn(3, 4)
}

//返回值是一个函数，即func(int) int
func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}

func main() {
	hypot := func(x, y float64) float64 {
		return math.Sqrt(x*x + y*y)
	}
	fmt.Println(hypot(5, 12))

	fmt.Println(compute(hypot))
	fmt.Println(compute(math.Pow))

	//TODO 不是很能理解这里的闭包
	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(
			pos(i),
			neg(-2*i),
		)
	}
}
