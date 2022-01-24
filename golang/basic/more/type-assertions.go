package main

//类型断言 https://tour.go-zh.org/methods/15
import "fmt"

func main() {
	var i interface{} = "hello"

	s := i.(string)
	fmt.Println(s)

	s, ok := i.(string)
	fmt.Println(s, ok)
	//
	f, ok := i.(float64)
	fmt.Println(f, ok)
	//
	f = i.(float64) // 报错(panic)
	fmt.Println(f)
}
