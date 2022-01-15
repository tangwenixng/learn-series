package main

import "fmt"

//长度：切片的长度就是它所包含的元素个数。
//容量：切片的容量是从它的第一个元素开始数，到其底层数组元素末尾的个数
func main() {
	s := []int{2, 3, 5, 7, 11, 13}
	printSlice(s) //len=6 cap=6 [2 3 5 7 11 13]

	// 截取切片使其长度为 0
	s = s[:0]
	printSlice(s) //len=0 cap=6 []

	// 拓展其长度
	s = s[:4]
	printSlice(s) //len=4 cap=6 [2 3 5 7]

	// 舍弃前两个值
	//这里不能理解为什么cap变4了？
	//看这篇文章的分析==> https://www.cnblogs.com/OctoptusLian/p/9205326.html
	//容量=4 是【末指针-开始指针】即 【6-2】=4
	s = s[2:]
	printSlice(s) //len=2 cap=4 [5 7]

	//切片的零值是 nil。
	//nil 切片的长度和容量为 0 且没有底层数组。
	var s1 []int
	fmt.Println(s1, len(s1), cap(s1))
	if s1 == nil {
		fmt.Println("nil!")
	}

}

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}
