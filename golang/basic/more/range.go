package main

import "fmt"

var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}

func main() {
	//range 形式可遍历【切片或映射】
	//第一个值为当前元素的下标，第二个值为该下标所对应元素的一份副本
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}

	nums := make([]int, 10)
	//只需要索引，不需要值
	for i := range nums {
		nums[i] = 1 << uint(i) //相当于 2**i
	}
	//使用_忽略索引
	for _, num := range nums {
		fmt.Printf("%d\n", num)
	}
}
