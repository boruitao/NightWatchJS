# NightWatchJS

Login tests implemented using Java 8 and Maven 3 with intelliJ Ultimate IDE for [this website](http://testing-ground.scraping.pro/login)

## Before running the tests

### Add dependencies

Under File -> Project Structure..., add all the jar files in Cucumber_Correct_JAR and selenium-java-3.141.59 
as external dependencies. 

### Get the chrome driver

The Chrome Web Driver (version 72) chosen to test all cases can be downloaded from [here](  https://sites.google.com/a/chromium.org/chromedriver/downloads)

### Change file path names

In StepDefinitions.java:
* change **PATH_TO_CHROME_DRIVER** to be the absolute path of the chrome driver that is downloaded on the computer.
* change **PATH_TO_IMG_DIR** to be the absolute path of the **screenshot** folder which is already included in the project repo.

### Config run/debug

Tests are run with the following configurations: 

* Main class: cucumber.api.cli.Main
* Glue: com.borui.cucumber
* Program arguments:  --plugin org.jetbrains.plugins.cucumber.java.run.CucumberJvmSMFormatter --monochrome

## Run the tests

* Execute **image_login.feature** by right-clicking on the file and select "run Feature:image_login"
* Execute **text_login.feature** by right-clicking on the file and select "run Feature:text_login"
