package mobileTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class logout {
	AndroidDriver<MobileElement> driver;

	MobileElement userNameInput;
	MobileElement passwordInput;
	MobileElement loginButton;
	MobileElement accountIcon;

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

	public MobileElement scrollToAnElementByText(AndroidDriver<MobileElement> driver, String text) {
		return driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector())" + ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
	}

	public void scrollAndClick(String visibleText) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ visibleText + "\").instance(0))")
				.click();
	}

	@Test
	public void LogoutAccount() throws InterruptedException {
		System.out.println("Test Logout Started");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
//		userNameInput.sendKeys(userNameInputValue);
//
//		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
//		passwordInput.sendKeys(passwordInputValue);
//
//		loginButton = driver.findElement(By.xpath("//*[contains(@text,'LOGIN')]"));
//		loginButton.click();

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
}
