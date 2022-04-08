package main

import (
	"github.com/hajimehoshi/oto"
	"github.com/tosone/minimp3"
	"io/ioutil"
	"log"
	"time"
)

func main() {
	var err error

	var file []byte
	if file, err = ioutil.ReadFile("/Users/twx/Downloads/1.mp3"); err != nil {
		log.Fatal(err)
	}

	var dec *minimp3.Decoder
	var data []byte
	if dec, data, err = minimp3.DecodeFull(file); err != nil {
		log.Fatal(err)
	}

	var context *oto.Context
	if context, err = oto.NewContext(dec.SampleRate, dec.Channels, 2, 1024); err != nil {
		log.Fatal(err)
	}

	var player = context.NewPlayer()
	player.Write(data)

	<-time.After(time.Second)
	log.Println("after end...")

	dec.Close()
	if err = player.Close(); err != nil {
		log.Fatal(err)
	}
}
