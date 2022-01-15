package main

import (
	"fmt"
)

//成员变量有默认值
var c, python, java bool

//变量初始化
var p, q int = 1, 2

func main() {
	var i int
	fmt.Println(i, c, python, java)
	//初始化值已存在，可以省略类型
	var x, y = true, "tangwx"
	fmt.Println(p, q, x, y)
	
	// := 可在类型明确的地方代替 var 声明;
	// := 结构不能在函数外使用
	k := 3
	fmt.Println(k)
}
