package com.borui.cucumber;

import cucumber.api.CucumberOptions;

@CucumberOptions(features = "src/test/resources",
        glue = {"com.borui.cucumber"},
        monochrome = true,
        plugin = { "pretty"}
)
public class CucumberRunner {
}