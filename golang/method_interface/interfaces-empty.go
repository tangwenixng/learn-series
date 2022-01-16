package main

import "fmt"

func main() {
	//指定了零个方法的接口值被称为 *空接口：*
	//空接口可保存任何类型的值
	var i interface{}
	describe(i)

	i = 42
	describe(i)

	i = "hello"
	describe(i)
}

func describe(i interface{}) {
	fmt.Printf("(%v, %T)\n", i, i)
}
