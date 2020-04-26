package cvelookup

import groovy.json.JsonSlurper

class App
{
    static void main(String[] cliArgs)
    {
        def cli = new CliBuilder(usage: "cvelookup <CVE ID>")
        cli.help("Print this message")
        def options = cli.parse(cliArgs)
        def args = options.arguments() 
        if(args == null || args[0] == null)
        {
            cli.usage()
            System.exit(0)
        }

        def cveId = args[0]
        def cveBase = "https://services.nvd.nist.gov/rest/json/cve/1.0/"
        def url = new URL(cveBase + cveId)
        def results = url.text
        def jsonSlurper = new JsonSlurper()
        def cveData = jsonSlurper.parseText(results)["result"]["CVE_Items"][0]["cve"]

        def description = cveData["description"]["description_data"][0]["value"]
        def cwe = cveData["problemtype"]["problemtype_data"][0]["description"]["value"][0]
        def references = cveData["references"]["reference_data"]

        println(cveId)
        println("CWE: " + cwe)
        println(description)
        println("References:")
        for(def reference: references)
        {
            println("\t" + reference["url"])
        }
    }
}
