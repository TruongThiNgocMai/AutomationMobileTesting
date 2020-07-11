package mobileTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class summaryTest {
	AndroidDriver<MobileElement> driver;

	MobileElement signUpButton;
	MobileElement loginButton;
	MobileElement signInButton;
	MobileElement fullNameInput;
	MobileElement emailInput;
	MobileElement phoneInput;
	MobileElement userNameInput;
	MobileElement passwordInput;
	MobileElement checkbox;
	MobileElement createNewAccount;
	MobileElement accountIcon;

	String fullNameInputValue = "Truong Thi Ngoc Mai";
	String emailInputValue = "maitruongthingoc1234@gmail.com";
	String phoneInputValue = "0396987863";
	String userNameInputValue = "MaiMai";
	String passwordInputValue = "maimai99";

	@BeforeTest
	public void setup() {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "sdk_gphone_x86_arm");
		cap.setCapability("version", "11");
		cap.setCapability("appWaitActivity", "SplashActivity, SplashActivity,OtherActivity, *, *.SplashActivity");
		cap.setCapability("app", "E://java-2019-03//eclipse-workspace//app-release-v1.apk");

		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void scrollAndClick(String visibleText) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ visibleText + "\").instance(0))")
				.click();
	}

	@Test
	public void TestAllScenarios() throws InterruptedException {
		System.out.println("Test Register A New Account Started");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Verify navigate to app
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Sign in')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Username')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Password')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'LOGIN')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Forgot your password?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'OR')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Login with Facebook')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'CREATE AN NEW ACCOUNT ?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]")).isDisplayed());

		// Click on SignUp button to create a new account
		signUpButton = driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]"));
		signUpButton.click();

		// Input data for full required fields
		fullNameInput = driver.findElement(By.xpath("//*[contains(@text,'Full Name')]"));
		fullNameInput.sendKeys(fullNameInputValue);

		emailInput = driver.findElement(By.xpath("//*[contains(@text,'Email')]"));
		emailInput.sendKeys(emailInputValue);

		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Phone')]"));
		phoneInput.sendKeys(phoneInputValue);

		userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
		userNameInput.sendKeys(userNameInputValue);

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
		passwordInput.sendKeys(passwordInputValue);

		checkbox = driver.findElement(By.xpath("//android.widget.CheckBox"));
		checkbox.click();

		// Click on CREATE NEW ACCOUNT button
		createNewAccount = driver.findElement(By.xpath("//*[contains(@text,'CREATE NEW ACCOUNT')]"));
		createNewAccount.click();

		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		alert.accept();

		Thread.sleep(3000);
		System.out.println("Test SignIn Started");

		// Click SignIn button if you already have an account
		signInButton = driver.findElement(By.xpath("//*[contains(@text,'Sign in')]"));
		signInButton.click();

		userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
		userNameInput.sendKeys(userNameInputValue);

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
		passwordInput.sendKeys(passwordInputValue);

		loginButton = driver.findElement(By.xpath("//*[contains(@text,'LOGIN')]"));
		loginButton.click();

		// verify login successfully
		Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@text,'RECOMMENDED STAY FOR YOU')]")).getText(),
				"RECOMMENDED STAY FOR YOU");
		Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@text,'POPULAR DESTINATION')]")).getText(),
				"POPULAR DESTINATION");
		Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@text,'HOME STAY AROUND THE WORLD')]")).getText(),
				"HOME STAY AROUND THE WORLD");

		Thread.sleep(3000);
		System.out.println("Test Logout Started");

		accountIcon = driver
				.findElementByXPath("//android.widget.FrameLayout[@content-desc='Profile, tab, 2 out of 2']");
		accountIcon.click();

		// scroll and cick on Log out button
		scrollAndClick("Log Out");

		// Verify logout successfully
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Sign in')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Username')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Password')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'LOGIN')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Forgot your password?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'OR')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Login with Facebook')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'CREATE AN NEW ACCOUNT ?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]")).isDisplayed());

	}

	@AfterTest
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception ign) {
		}
	}
}