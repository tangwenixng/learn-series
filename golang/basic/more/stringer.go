package main

import "fmt"

//Stringer接口  https://tour.go-zh.org/methods/17

type Person struct {
	Name string
	Age  int
}

//应该类似于java的toString方法
func (p Person) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

func main() {
	a := Person{"twx", 24}
	b := Person{"tangfei", 11}
	fmt.Println(a, b)
}
