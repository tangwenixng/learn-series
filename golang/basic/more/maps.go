package main

import "fmt"

//Vertex 声明结构体
type Vertex struct {
	Lat, Long float64
}

//声明映射 key:字符串 val:Vertex类型
//映射的零值为 nil
var m map[string]Vertex

//声明有初始值的map
var n = map[string]Vertex{
	"guilin":   {102.1, 43.12},
	"shenzhen": {111.1, 23.1},
}

func main() {
	//也可以使用make函数生成map
	m = make(map[string]Vertex)
	m["jiangsu"] = Vertex{101.1, 45.21}
	fmt.Println(m)

	fmt.Println(n) //map[guilin:{102.1 43.12} shenzhen:{111.1 23.1}]

	j := make(map[string]int)
	j["aa"] = 1
	fmt.Println("value", j["aa"])

	j["aa"] = 2
	fmt.Println("value", j["aa"])

	delete(j, "aa")
	//删除之后aa=0
	fmt.Println("value", j["aa"])
	//通过双赋值检测某个键是否存在(常用)
	v, ok := j["aa"]
	fmt.Println("The value:", v, "Present?", ok)
}
