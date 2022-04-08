package main

import (
	"fmt"
	"io"
	"log"
	"net/http"
)

type Counter struct {
	n int
}

func (c *Counter) ServeHTTP(rw http.ResponseWriter, request *http.Request) {
	c.n++
	fmt.Fprintf(rw, "counter=%d\n", c.n)
}

func HelloServer(rw http.ResponseWriter, request *http.Request) {
	io.WriteString(rw, "Hello,Go Server!")
}

func main() {
	http.HandleFunc("/hello", HelloServer)
	http.Handle("/counter", &Counter{})
	err := http.ListenAndServe(":12345", nil)
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}
}
