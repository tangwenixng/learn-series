package main

import "fmt"

func main() {
	var s []int
	printSlice(s) //len=0 cap=0 []

	// 添加一个空切片
	//append 的结果是一个包含原切片所有元素加上新添加元素的切片
	s = append(s, 0)
	printSlice(s) //len=1 cap=1 [0]

	// 这个切片会按需增长
	s = append(s, 1)
	printSlice(s) //len=2 cap=2 [0 1]

	// 可以一次性添加多个元素
	s = append(s, 2, 3, 4)
	printSlice(s) //len=5 cap=6 [0 1 2 3 4]
	//todo 这里的cap为什么是6？
}

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}
