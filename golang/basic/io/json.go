package main

import (
	"encoding/json"
	"fmt"
	"log"
	"os"
)

type Address struct {
	Type    string
	City    string
	Country string
}

type VCard struct {
	FirstName string
	LastName  string
	Addresses []*Address
	Remark    string
}

func main() {
	pa := &Address{"private", "Aartselaar", "Belgium"}
	wa := &Address{"work", "Boom", "Belgium"}
	vc := VCard{"Jan", "Kersschot", []*Address{pa, wa}, "none"}
	//序列化json
	js, err := json.Marshal(vc)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("JSON format: %s", js)

	//将vc对象序列化到文件中
	file, _ := os.OpenFile("./basic/io/vcard.json", os.O_CREATE|os.O_WRONLY, 0666)
	defer file.Close()
	enc := json.NewEncoder(file)
	err = enc.Encode(vc)
	if err != nil {
		log.Fatal(err)
	}
}
