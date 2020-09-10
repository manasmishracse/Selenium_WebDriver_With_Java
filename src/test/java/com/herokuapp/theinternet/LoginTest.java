package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class LoginTest {

	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	private void setup() {
		// Creating Driver and initializing the path
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		// Maximize browser
		driver.manage().window().maximize();
	}

	
	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void PositiveLoginTest() {

		System.out.println("Positive Login Test is started!!");

		// opening the login page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);

		System.out.println("Page is Opened!!!");

		// Enter Username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

		// Enter Password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		// Click Login Button
		WebElement login = driver.findElement(By.tagName("button"));
		login.click();

		// Verification of data

		// new url
		String ExpURL = "http://the-internet.herokuapp.com/secure";
		String ActURL = driver.getCurrentUrl();
		Assert.assertEquals(ExpURL, ActURL, "The actual URL is not same with expected URL");

		// Logout button is visible
		WebElement logout = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logout.isDisplayed(), "Logout Button is not visible");

		// Succesfull message
		WebElement Successmsg = driver.findElement(By.cssSelector("div#flash"));
		String ExpeMsg = "You logged into a secure area!";
		String ActMsg = Successmsg.getText();
		System.out.println("the actual message is " + ActMsg);
		// Assert.assertEquals(ExpeMsg, ActMsg, "Provided message is not present");
		Assert.assertTrue(ActMsg.contains(ExpeMsg), "Provided message is not preenst");
	}

		@Parameters({ "username", "password", "expectedmessage" })
		@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
		public void negativeLoginTest(String username, String password, String expectedmessage) {

			System.out.println(
					"Starting Negative LoginTest with username as: " + username + " and password as: " + password);

			// opening the login page
			String url = "http://the-internet.herokuapp.com/login";
			driver.get(url);

			System.out.println("Home Page is Opened!!!");

			// Enter Username
			WebElement usernameElement = driver.findElement(By.id("username"));
			usernameElement.sendKeys(username);

			sleep(1000);

			// Enter Password
			WebElement passwordElement = driver.findElement(By.name("password"));
			passwordElement.sendKeys(password);

			sleep(1000);

			// Click Login Button
			WebElement login = driver.findElement(By.tagName("button"));
			login.click();

			// Verification of data
			// Error Message
			WebElement Page = driver.findElement(By.xpath("//div[@id='flash']"));
			String ActuMsg = Page.getText();
			// Assert.assertEquals(ActuMsg, ExpeMsg, "Error message for Incorrect Username
			// is not present");
			Assert.assertTrue(ActuMsg.contains(expectedmessage), "Error message is not present");

			sleep(1000);

		}

		private void sleep(long m) {
			try {
				Thread.sleep(m);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch blockx`
				e.printStackTrace();
			}
		}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close Browser
		driver.quit();

	}

}
