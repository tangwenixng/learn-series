package main

import "fmt"

//切片文法类似于没有长度的数组文法。
func main() {
	q := []int{2, 3, 5, 7, 11, 13}
	fmt.Println(q) //[2 3 5 7 11 13]

	r := []bool{true, false, true, true, false, true}
	fmt.Println(r) //[true false true true false true]

	s0 := []struct {
		i int
		b bool
	}{
		{2, true},
		{3, false},
		{5, true},
		{7, true},
		{11, false},
		{13, true}, //注意:结尾这个逗号是必须的
	}
	fmt.Println(s0)

	fmt.Println("=============")
	s := []int{2, 3, 5, 7, 11, 13}

	s = s[1:4]
	fmt.Println(s) //[3 5 7]

	s = s[:2]      //基于上面的输出
	fmt.Println(s) //[3 5]

	s = s[1:]      //基于上面的输出
	fmt.Println(s) //[5]
}
