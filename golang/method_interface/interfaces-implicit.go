package main

import "fmt"

type I interface {
	M()
}

type T struct {
	S string
}

// 此方法表示类型 T 实现了接口 I，但我们无需显式声明此事。
//解释一下: 此处的M方法是属于结构体T的。相当于隐性实现了接口I
func (t T) M() {
	fmt.Println(t.S)
}

func main() {
	//i的类型是接口I, 实际指向类型T
	var i I = T{"hello"}
	i.M()
}
