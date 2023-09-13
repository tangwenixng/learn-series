package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"
	"time"
)

var saveDir string

func main() {
	saveDir = os.Args[2]
	fmt.Printf("视频片段将被保存至%s\n", saveDir)

	timeTickerChan := time.Tick(time.Minute * 2)
	for {
		fmt.Println(time.Now().Format("2006-01-02 15:04:05"))
		scanDir(os.Args[1])
		//使用 <-timeTickerChan从时间间隔通道接收一个消息，该操作会阻塞程序，直到时间间隔到达
		<-timeTickerChan
	}
}

func scanDir(path string) {
	dirs, err := ioutil.ReadDir(path)
	if err != nil {
		log.Fatal(err)
	}

	for idx, file := range dirs {
		if file.IsDir() {
			childPath := filepath.Join(path, file.Name())
			fmt.Printf("扫描文件夹%s\n", childPath)
			scanDir(childPath)
		} else {
			fileName := file.Name()
			if suffix := fileName[strings.LastIndex(fileName, "."):]; suffix == ".mp4" {
				if idx != (len(dirs) - 1) {
					streamName := path[(strings.LastIndex(path, "/") + 1):]
					mp4Path := filepath.Join(path, fileName)

					savePath := filepath.Join(saveDir, streamName, fileName)
					//fmt.Println(idx, mp4Path)

					if !isExist(savePath) {
						//创建文件夹
						os.MkdirAll(filepath.Dir(savePath), os.ModePerm)
						//拷贝文件
						copyFile(mp4Path, savePath)
					}
				}
			}

		}
	}
}

func isExist(path string) bool {
	_, err := os.Stat(path)
	if err != nil {
		if os.IsNotExist(err) {
			return false
		}
	}
	return true
}

func copyFile(src, des string) {
	source, err := os.Open(src)
	if err != nil {
		log.Fatal(err)
	}

	destination, err := os.Create(des)
	if err != nil {
		log.Fatal(err)
	}

	_, err = io.Copy(destination, source)
	if err != nil {
		log.Fatal(err)
	}

	log.Printf("成功拷贝文件:%v", des)

	// 将文件内容flush到硬盘中
	err = destination.Sync()
	if err != nil {
		log.Fatal(err)
	}
}
