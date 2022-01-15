package main

import "fmt"

// Vertex 一个结构体（struct）就是一组字段（field）
type Vertex struct {
	X int
	Y int
}

type Person struct {
	name string
	age  int
}

func main() {
	fmt.Println(Vertex{1, 2})

	v := Vertex{2, 3}
	//结构体字段使用点号来访问
	v.X = 11
	v.Y = 15
	fmt.Println(v)

	/*如果我们有一个指向结构体的指针 p，那么可以通过 (*p).X 来访问其字段 X。
	不过这么写太啰嗦了，所以语言也允许我们使用隐式间接引用，直接写 p.X 就可以*/
	p := &v   //p是一个指向v的指针
	p.X = 101 //其实相当于 (*p).X = 101
	(*p).Y = 21
	fmt.Println(v)

	fmt.Println("========")
	var (
		p1     = Person{"twx", 28}
		p2     = Person{name: "jsj"}          //age被隐式赋予
		p3     = Person{}                     //默认字段值-string默认是null
		pPoint = &Person{name: "tf", age: 32} //创建一个 *Person 类型的结构体（指针）
	)
	fmt.Println(p1, p2, p3, pPoint) //pPoint= &{tf 32}
}
