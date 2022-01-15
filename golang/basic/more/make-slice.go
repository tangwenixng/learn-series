package main

import "fmt"

func main() {
	//切片可以用内建函数 make 来创建，这是创建动态数组的方式
	a := make([]int, 5)
	printSlice("a", a) //a len=5 cap=5 [0 0 0 0 0]
	//指定它的容量，需向 make 传入第三个参数
	b := make([]int, 0, 5)
	printSlice("b", b) //b len=0 cap=5 []

	c := b[:2]
	printSlice("c", c) //c len=2 cap=5 [0 0]
	//cap=5是由于5-0=5
	
	d := c[2:5]
	printSlice("d", d) //d len=3 cap=3 [0 0 0]
	//cap=3是由于 5-2=3
}

func printSlice(s string, x []int) {
	fmt.Printf("%s len=%d cap=%d %v\n",
		s, len(x), cap(x), x)
}
