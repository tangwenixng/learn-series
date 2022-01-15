package main

import "fmt"

func main() {
	i := 42

	//& 操作符会生成一个指向其操作数的指针。
	p := &i
	//* 操作符表示指针指向的底层值
	fmt.Println("p指向的值", *p, "p内存值", p) // 通过指针读取 i 的值

	*p = 21 //通过指针设置 i 的值
	fmt.Println("修改后的i值", i, "p内存值", p)

	j := 2701
	p = &j
	*p = *p / 37 // *p取值除37后，再给p设值
	fmt.Println("修改后的j值", j, "p内存值", p)
}
