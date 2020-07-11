package mobileTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.junit.Assert;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class signIn {
	AndroidDriver<MobileElement> driver;

	MobileElement userNameInput;
	MobileElement passwordInput;
	MobileElement loginButton;

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

	@Test
	public void SignInAccount() throws InterruptedException {
		System.out.println("Test SignIn Started");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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
	}

	@After
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception ign) {
		}
	}
}
