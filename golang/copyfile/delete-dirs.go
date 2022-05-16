package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"
	"time"
)

var now = time.Now().Format("2006-01-02")

func main() {
	var userSelect string
	fmt.Printf("Your input dir is %s, Continue Enter Y/y, Else N/n\n", os.Args[1])
	fmt.Scan(&userSelect)
	if strings.ToLower(userSelect) == "y" {
		log.Println("Begin scan and remove expired dirs...")
		//initDir's format should be "/var/lib/docker/volumes/mh_srs-dev/_data/Dev_stream/"
		initDir := os.Args[1]
		findDir(initDir, 0)
	}
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
				stat, err := os.Stat(nextDir)
				if err != nil {
					log.Fatal(err)
				}
				modTime := stat.ModTime().Format("2006-01-02")
				if modTime != now {
					log.Printf("删除目标文件夹%s\n", nextDir)
					os.RemoveAll(nextDir)
				} else {
					log.Printf("当天目标文件夹%s\n", nextDir)
				}
			} else {
				log.Printf("第%d层文件夹%s\n", level, nextDir)
				findDir(nextDir, level+1)
			}
		}
	}
}
