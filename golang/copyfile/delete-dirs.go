package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
)

func main() {
	initDir := os.Args[1]
	findDir(initDir, 0)
}

func findDir(path string, level int) {
	dirs, err := ioutil.ReadDir(path)
	if err != nil {
		log.Fatal(err)
	}
	for _, file := range dirs {
		if file.IsDir() {
			//继续遍历下一层文件夹
			nextDir := filepath.Join(path, file.Name())
			if level == 1 {
				fmt.Printf("删除目标文件夹%s\n", nextDir)
				os.RemoveAll(nextDir)
			} else {
				fmt.Printf("第%d层文件夹%s\n", level, nextDir)
				findDir(nextDir, level+1)
			}
		}
	}
}
