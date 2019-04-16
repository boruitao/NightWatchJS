package com.borui.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

public class StepDefinitions {

    private final String PATH_TO_CHROME_DRIVER = "/Users/toukashiwaakira/workspaces/driver/chromedriver";
    private final String PATH_TO_IMG_DIR = "/Users/toukashiwaakira/workspaces/NightWatchJS/screenshot";
    private final String LOGIN_URL = "http://testing-ground.scraping.pro/login";
    private final String VALID_USERNAME = "admin";
    private final String VALID_PW = "12345";
    private final String INVALID_USERNAME = "borui";
    private final String INVALID_PW = "666666";
    // Variables
    private WebDriver driver;

    @Given("^I am on the \"login\" page with the username and password unspecified$")
    public void givenUserOnLoginPage() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(LOGIN_URL);
    }

    @And("^I have a screenshot of the successful login page$")
    public void takeScreenShotOfSuccessfulLoginPage() throws Throwable {
        Assert.assertTrue(new File(PATH_TO_IMG_DIR + "/success.png").exists());
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

    @Then("^I should be redirected to a page that is identical to the screenshot I have$")
    public void redirectedToWelcomePageAndTakeScreenShot() throws Throwable {
        System.out.println("Waiting for the GO BACK button to be displaced...");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='login']")));
        System.out.print("GO BACK button appears!\n");

        takeScreenshot("/test1.png");
        BufferedImage originalScreenshot = ImageIO.read(new File(PATH_TO_IMG_DIR + "/success.png"));
        BufferedImage currentScreenshot = ImageIO.read(new File(PATH_TO_IMG_DIR + "/test1.png"));
        Assert.assertTrue(compareImages(originalScreenshot, currentScreenshot));
        deleteFile(new File(PATH_TO_IMG_DIR + "/test1.png"));
    }

    @Then("^I should be redirected to a page that is not identical to the screenshot I have$")
    public void redirectedToErrorPageAndTakeScreenShot() throws Throwable {
        System.out.println("Waiting for the GO BACK button to be displaced...");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='login']")));
        System.out.print("GO BACK button appears!\n");

        takeScreenshot("/test2.png");
        BufferedImage originalScreenshot = ImageIO.read(new File(PATH_TO_IMG_DIR + "/success.png"));
        BufferedImage currentScreenshot = ImageIO.read(new File(PATH_TO_IMG_DIR + "/test2.png"));
        Assert.assertFalse(compareImages(originalScreenshot, currentScreenshot));
        deleteFile(new File(PATH_TO_IMG_DIR + "/test2.png"));
    }

    private boolean compareImages(BufferedImage originalScreenshot, BufferedImage currentScreenshot) throws IOException {
        ByteArrayOutputStream original_byteOutput = new ByteArrayOutputStream();
        ByteArrayOutputStream current_byteOutput = new ByteArrayOutputStream();
        ImageIO.write(originalScreenshot, "png", original_byteOutput);
        original_byteOutput.flush();
        byte[] imageInByteOriginal = original_byteOutput.toByteArray();
        original_byteOutput.close();
        ImageIO.write(currentScreenshot, "png", current_byteOutput);
        current_byteOutput.flush();
        byte[] imageInByteCurrent = current_byteOutput.toByteArray();
        current_byteOutput.close();
        return Arrays.equals(imageInByteOriginal, imageInByteCurrent);
    }

    private void takeScreenshot(String name) {
        System.out.println("Preparing to take screenshot...");
        driver.manage().window().maximize();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 250)");
        System.out.println("Taking to take screenshot...");
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(PATH_TO_IMG_DIR + name));
            System.out.println("Screenshot taken and copied");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }
}
