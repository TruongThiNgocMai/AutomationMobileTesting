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

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class Register extends ExtendReport {
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
	public void registerNewAccount() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		ExtentTest test = extent.createTest("RegisterTest", "Demo sample description");
		test.log(Status.INFO, "Test Register Started");
		@SuppressWarnings("resource")
		XSSFWorkbook AWorkbook = new XSSFWorkbook(); // Create blank workbook

		accountIcon = driver
				.findElementByXPath("//android.widget.FrameLayout[@content-desc='Profile, tab, 2 out of 2']");
		accountIcon.click();
		test.log(Status.PASS, "Click on account icon");
		// scroll and click on Log out button
		scrollAndClick("Log Out");
		test.log(Status.PASS, "Click on logout button");

		// Verify navigate to application
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Sign in')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Username')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Password')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'LOGIN')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Forgot your password?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'OR')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Login with Facebook')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'CREATE AN NEW ACCOUNT ?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]")).isDisplayed());
		test.log(Status.PASS, "Verify user navigate to Sign In page");

		// Click on SignUp button to create a new account
		signUpButton = driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]"));
		signUpButton.click();
		test.log(Status.PASS, "Click on sign in button");

		// Input data for full required fields
		fullNameInput = driver.findElement(By.xpath("//*[contains(@text,'Full Name')]"));
		fullNameInput.sendKeys(fullNameInputValue);
		test.log(Status.PASS, "Input fullname field");

		emailInput = driver.findElement(By.xpath("//*[contains(@text,'Email')]"));
		emailInput.sendKeys(emailInputValue);
		test.log(Status.PASS, "Input email field");

		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Phone')]"));
		phoneInput.sendKeys(phoneInputValue);
		test.log(Status.PASS, "Input phone field");

		userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
		userNameInput.sendKeys(userNameInputValue);
		test.log(Status.PASS, "Input username field");

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
		passwordInput.sendKeys(passwordInputValue);
		test.log(Status.PASS, "Input password field");

		checkbox = driver.findElement(By.xpath("//android.widget.CheckBox"));
		checkbox.click();
		test.log(Status.PASS, "Click on agree checbox");

		// Click on button
		createNewAccount = driver.findElement(By.xpath("//*[contains(@text,'CREATE NEW ACCOUNT')]"));
		createNewAccount.click();
		test.log(Status.PASS, "Click on create new account button");

		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		test.log(Status.PASS, "Click on alert");

		XSSFSheet spreadsheet = AWorkbook.createSheet(" Register Data");
		XSSFRow row;

		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1",
				new Object[] { "fullNameInput", "emailInput", "phoneInput", "userNameInput", "passwordInput" });

		empinfo.put("2", new Object[] { fullNameInputValue, emailInputValue, phoneInputValue, userNameInputValue,
				passwordInputValue });

		// Iterate over data and write to sheet
		Set<String> keyid = empinfo.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		FileOutputStream out = new FileOutputStream(new File("Register.xlsx"));
		AWorkbook.write(out);
		System.out.print("Write data into excel file is done!!!");
		out.close();
		test.log(Status.INFO, "Test Register Finished");
	}

	@AfterTest
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception ign) {
		}
	}
}
