package com.borui.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;

public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/toukashiwaakira/workspaces/driver/chromedriver";
    private final String LOGIN_URL = "http://testing-ground.scraping.pro/login";

    private final String VALID_USERNAME = "admin";
    private final String VALID_PW = "12345";

    private final String INVALID_USERNAME = "borui";
    private final String INVALID_PW = "666666";

    @Given("^I am on the \"login\" page with the username and password unspecified$")
    public void givenUserOnLoginPage() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(LOGIN_URL);
    }

    @When("^I enter the username and password$")
    public void whenUserEnterUsernamePw() throws Throwable {
        System.out.println("Attempting to specify the username.. ");
        WebElement usernameField = driver.findElement(By.id("usr"));
        usernameField.sendKeys(VALID_USERNAME);
        System.out.println("username specified!");

        System.out.println("Attempting to specify the password.. ");
        WebElement pwField = driver.findElement(By.id("pwd"));
        pwField.sendKeys(VALID_PW);
        System.out.println("password specified!");
    }

    @When("^I enter invalid username and password$")
    public void whenUserEnterInvalidUsernamePw() throws Throwable {
        System.out.println("Attempting to specify the username.. ");
        WebElement usernameField = driver.findElement(By.id("usr"));
        usernameField.sendKeys(INVALID_USERNAME);
        System.out.println("username specified!");

        System.out.println("Attempting to specify the password.. ");
        WebElement pwField = driver.findElement(By.id("pwd"));
        pwField.sendKeys(INVALID_PW);
        System.out.println("password specified!");
    }

    @And("^I press \"Login\"$")
    public void andUserPressLogin() throws Throwable {
        System.out.println("Attempting to find the Login button.. ");
        WebElement loginButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit' and @value='Login']")));

        System.out.println("Login button found! ");
        loginButton.click();
    }

    @Then("^I should be redirected to a page with the \"WELCOME\" message displayed$")
    public void redirectedToWelcomePage() throws Throwable {
        System.out.println("Waiting for the GO BACK button to be displaced...");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='login']")));
        System.out.print("GO BACK button appears!\n");

        Assert.assertTrue(driver.findElements(By.xpath("//h3[@class='success']")).size() > 0);
    }

    @Then("^I should be redirected to a page with the \"ACCESS DENIED!\" message displayed$")
    public void redirectedToErrorPage() throws Throwable {
        System.out.println("Waiting for the GO BACK button to be displaced...");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='login']")));
        System.out.print("GO BACK button appears!\n");

        Assert.assertTrue(driver.findElements(By.xpath("//h3[@class='error']")).size() > 0);
    }

    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }
}
