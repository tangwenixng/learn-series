package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {
	fmt.Println("start switch...")
	os := runtime.GOOS
	switch os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		fmt.Printf("%s.\n", os)
	}

	fmt.Println("When's Saturday?")
	today := time.Now().Weekday()
	fmt.Println("today is", today)
	switch time.Saturday {
	case today + 0:
		fmt.Println("Today.")
	case today + 2:
		fmt.Println("In two days.")
	default:
		fmt.Println("Too far away.")
	}

	//没有条件的 switch 同 switch true 一样。
	//这种形式能将一长串 if-then-else 写得更加清晰。
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Good morning!")
	case t.Hour() < 17:
		fmt.Println("Good afternoon.")
	default:
		fmt.Println("Good evening.")
	}
}
