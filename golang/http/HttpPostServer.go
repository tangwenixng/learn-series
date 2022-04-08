package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
)

type AirportAlarm struct {
	CameraName string
	JwCode     string
	AudioUrl   string
	LampColor  int
}

func (asAlarm *AirportAlarm) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	for h, v := range r.Header {
		fmt.Println(h, ":", v)
	}
	var apa AirportAlarm
	err := json.NewDecoder(r.Body).Decode(&apa)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	fmt.Fprintf(w, "cameraName: %s", apa.CameraName)
}

func main() {
	http.Handle("/as", &AirportAlarm{})
	err := http.ListenAndServe(":12345", nil)
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}
}
