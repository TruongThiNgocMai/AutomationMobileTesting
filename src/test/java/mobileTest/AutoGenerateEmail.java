package mobileTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By; 
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AutoGenerateEmail {

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
	
	String phoneInputValue = "0396987863";
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
	
	protected String getSaltString() {
		String SALTCHARS = "abcdefghijkkmnopqrstuvxyz1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	@Test
	private void DotheMailAction() throws InterruptedException {
		System.out.print("Test Register with auto generate element start!!!");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// Click on SignUp button to create a new account
		signUpButton = driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]"));
		signUpButton.click();

		// Input data for full required fields
		fullNameInput = driver.findElement(By.xpath("//*[contains(@text,'Full Name')]"));
		fullNameInput.sendKeys("Truong Thi " + getSaltString() + " Mai");

		emailInput = driver.findElement(By.xpath("//*[contains(@text,'Email')]"));
		emailInput.sendKeys("mai" + getSaltString() + "@gmail.com");

		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Phone')]"));
		phoneInput.sendKeys(phoneInputValue);

		userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
		userNameInput.sendKeys("mai" + getSaltString() + "mai");

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
		passwordInput.sendKeys(passwordInputValue);

		checkbox = driver.findElement(By.xpath("//android.widget.CheckBox"));
		checkbox.click();
		// Click on button
		createNewAccount = driver.findElement(By.xpath("//*[contains(@text,'CREATE NEW ACCOUNT')]"));
		createNewAccount.click();

		Thread.sleep(3000);
	}

	@AfterTest
	public void tearDown() {
//After the process the code closed the driver
		if (driver != null) {
			driver.quit();
		}
	}
}
