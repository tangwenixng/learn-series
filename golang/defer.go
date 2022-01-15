package main

import "fmt"

func main() {
	//推迟调用的函数其参数会立即求值
	//但直到外层函数返回前该函数都不会被调用。(意思是main函数最后一句结束后defer才会被调用)
	defer fmt.Println("World!")

	fmt.Println("Hello")
	fmt.Println("111")
	fmt.Println("222")

	fmt.Println("=============")
	for i := 0; i < 10; i++ {
		defer fmt.Println(i) //输出顺序 9 8 7 .. 1 0
	}
	fmt.Println("done")
}
