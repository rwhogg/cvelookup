#!/usr/bin/env yaegi

package main

import (
    "fmt"
    "net/http"
    "io/ioutil"
    "os"
)

func main() {
    URL_BASE := "https://services.nvd.nist.gov/rest/json/cve/1.0/"
    cveId := "CVE-" + os.Args[1]
	response, _ := http.Get(URL_BASE + cveId)
    defer response.Body.Close()
    bytes, _ := ioutil.ReadAll(response.Body)
    body := string(bytes)
    fmt.Println(body)
}

