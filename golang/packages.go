package main

import (
	"fmt"
	"math"
	"math/rand"
)

func main() {
	fmt.Println("My favorite number is", rand.Intn(10))
	//格式化输出：去哪查看占位符参数
	fmt.Printf("Now you have %g problems.\n", math.Sqrt(7))
	//在 Go 中，如果一个名字以大写字母开头，那么它就是已导出的;比如math下的Pi
	fmt.Println(math.Pi)
	fmt.Println(add(1, 2))
	fmt.Println(add0(12, 2))
	//函数是可以返回多个值的
	a, b := swap("ab", "bc")
	fmt.Println(a, b)

	fmt.Println(split(19))

}

//注意类型在变量名之后
//go语法声明的文章链接：https://blog.go-zh.org/gos-declaration-syntax
func add(x int, y int) int {
	return x + y
}

//当连续两个或多个函数的已命名形参类型相同时，除最后一个类型以外，其它都可以省略
func add0(x, y int) int {
	return x + y
}

//多值返回
//函数可以返回任意数量的返回值。
func swap(x, y string) (string, string) {
	return y, x
}

//没有参数的 return 语句返回已命名的返回值
//该函数返回x,y
func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}
