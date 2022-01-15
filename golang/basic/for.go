package main

import (
	"fmt"
	"math"
)

func main() {
	sum := 0
	//没有小括号， 大括号 { } 则是必须的。
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println(sum)

	//"while循环"
	count := 1
	for count < 10 {
		fmt.Println("count=", count)
		count += 1
	}
	//无限循环
	/*for {
	}*/

	//if 无需小括号,但是花括号是必须的
	input := "abc"
	if input != "abc" {
		fmt.Println("is not abc")
	} else {
		fmt.Println("is abc")
	}

	fmt.Println(
		pow(3, 2, 10),
		pow(3, 3, 20),
	)
}

func pow(x, n, lim float64) float64 {
	//if 语句可以在条件表达式前执行一个简单的语句
	//该语句声明的变量作用域仅在 if 之内
	if v := math.Pow(x, n); v < lim {
		return v
	}
	//fmt.Println("check V is",v) v在这里是无效的
	return lim
}
