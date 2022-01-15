package main

import "fmt"

func main() {
	//注意 有点奇怪哦!
	var a [2]string
	a[0] = "Hello"
	a[1] = "World"
	fmt.Println(a[0], a[1])
	fmt.Println(a)

	//对数组赋初始值
	b := [5]int{1, 2, 3, 4, 5}
	fmt.Println(b)

	primes := [6]int{2, 3, 5, 7, 11, 13}
	//切片
	s := primes[1:4] //闭开==> [)
	fmt.Println(s)   //3 5 7

	names := [4]string{
		"John",
		"Paul",
		"George",
		"Ringo",
	}
	fmt.Println(names)

	a1 := names[0:2]
	b1 := names[1:3]
	fmt.Println(a1, b1) //[John Paul] [Paul George]

	b1[0] = "XXX" //切片只是一个"视图"，修改切片，会影响原数组内容
	fmt.Println(a1, b1)
}
