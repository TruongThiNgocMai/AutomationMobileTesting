package mobileTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

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
	MobileElement accountIcon;
	MobileElement continueButton;

	String phoneInputValue = "0396987863";
	String passwordInputValue = "maimai99";
	String fullNameInputValue;
	String emailInputValue;
	String userNameInputValue;

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

	protected String getRandomString() {
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
	private void DotheMailAction() throws InterruptedException, FileNotFoundException, IOException {
		@SuppressWarnings("resource")
		XSSFWorkbook AWorkbook = new XSSFWorkbook(); // Create blank workbook

		System.out.println("Test Register with auto generate element start!!!");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		accountIcon = driver
//				.findElementByXPath("//android.widget.FrameLayout[@content-desc='Profile, tab, 2 out of 2']");
//		accountIcon.click();
//
//		// scroll and click on Log out button
//		scrollAndClick("Log Out");

		for (int i = 1; i < 3; i++) {
			XSSFSheet spreadsheet = AWorkbook.createSheet(" DataTest " + i);
			XSSFRow row;

			// Click on SignUp button to create a new account
			signUpButton = driver.findElement(By.xpath("//*[contains(@text,'Sign Up Now')]"));
			signUpButton.click();

			// Input data for full required fields
			fullNameInput = driver.findElement(By.xpath("//*[contains(@text,'Full Name')]"));
			fullNameInputValue = "Truong Thi " + getRandomString() + " Mai";
			fullNameInput.sendKeys(fullNameInputValue);

			emailInput = driver.findElement(By.xpath("//*[contains(@text,'Email')]"));
			emailInputValue = "mai" + getRandomString() + "@gmail.com";
			emailInput.sendKeys(emailInputValue);

			phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Phone')]"));
			phoneInput.sendKeys(phoneInputValue);

			userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
			userNameInputValue = "mai" + getRandomString() + "mai";
			userNameInput.sendKeys(userNameInputValue);

			passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
			passwordInput.sendKeys(passwordInputValue);

			checkbox = driver.findElement(By.xpath("//android.widget.CheckBox"));
			checkbox.click();

			// Click on button
			createNewAccount = driver.findElement(By.xpath("//*[contains(@text,'CREATE NEW ACCOUNT')]"));
			createNewAccount.click();

			Thread.sleep(5000);

			continueButton = driver.findElement(By.xpath("//*[contains(@text,'CONTINUE NOW')]"));

			continueButton.click();
			accountIcon = driver
					.findElementByXPath("//android.widget.FrameLayout[@content-desc='Profile, tab, 2 out of 2']");
			accountIcon.click();
			// scroll and click on Log out button
			scrollAndClick("Log Out");

			Thread.sleep(5000);

			Map<String, Object[]> dataInfor = new TreeMap<String, Object[]>();
			dataInfor.put("1",
					new Object[] { "ExecuteID", "FULLNAME", "EMAIL", "PHONE_NUMBER", "USERNAME", "PASSWORD" });
			for (int j = 0; j < i; j++) {
				// j + 1: start with id = 1
				dataInfor.put("2", new Object[] { j + 1 + "", fullNameInputValue, emailInputValue, phoneInputValue,
						userNameInputValue, passwordInputValue });
//			dataInfor.put("3", new Object[] { i + 2 + "", fullNameInputValue, emailInputValue, phoneInputValue, userNameInputValue,
//					passwordInputValue });
			}
			// Iterate over data and write to sheet
			Set<String> keyid = dataInfor.keySet();
			int rowid = 0;
			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = dataInfor.get(key);
				int cellid = 0;
				for (Object obj : objectArr) {
					Cell cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}

		}

		FileOutputStream out = new FileOutputStream(new File("AllData.xlsx"));
		AWorkbook.write(out);
		System.out.print("Write data into excel file is done!!!");
		out.close();
	}

	@AfterTest
	public void tearDown() {
//After the process the code closed the driver
		if (driver != null) {
			driver.quit();
		}
	}
}
