package NailApp;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
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

public class RegisterSuccessfully extends ExtendReport {
	AndroidDriver<MobileElement> driver;

	MobileElement registerLink;
	MobileElement registerButton;
	MobileElement usernameInput;
	MobileElement emailInput;
	MobileElement phoneInput;
	MobileElement passwordInput;
	MobileElement confirmPassword;
	MobileElement compareUsername;

	String userNameInputValue = "MaiMai";
	String emailInputValue;
	String phoneInputValue;
	String passwordInputValue = "maimai99";
	String confirmPasswordValue = "maimai99";

	@BeforeTest
	public void setup() {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "Pixel XL API 25");
		cap.setCapability("version", "7.1.1");
		cap.setCapability("appWaitActivity", "SplashActivity, SplashActivity,OtherActivity, *, *.SplashActivity");
		cap.setCapability("app", "C:\\Users\\Administrator\\AppData\\Local\\Android\\Sdk\\platform-tools\\app-release1.apk");

		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void scroll(String visibleText) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ visibleText + "\").instance(0))");
	}
	
	protected String getRandomString() {
		String SALTCHARS = "1234567890";
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
	public void registerNewAccountSuccessfully() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		ExtentTest test = extent.createTest("Register Successfully", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Register Started");

		registerLink = driver.findElement(By.xpath("//*[contains(@text,'Đăng kí')]"));
		registerLink.click();
		test.log(Status.PASS, "Click on register link");

		// Verify navigate to Register screen
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'The Nail')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Đăng kí')]")).isDisplayed());
		test.log(Status.PASS, "Verify user navigate to Regiser page successfully");

		scroll("Đã có tài khoản");
		// Input data for full required fields
		usernameInput = driver.findElement(By.xpath("//*[contains(@text,'Tên đăng nhập...')]"));
		usernameInput.sendKeys(userNameInputValue + "\n");
		test.log(Status.PASS, "Input value for username field");

		emailInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập email...')]"));
		emailInputValue = "mai" + getRandomString() + "@gmail.com";
		emailInput.sendKeys( emailInputValue + "\n");
		test.log(Status.PASS, "Input value for email field");

		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập số điện thoại...')]"));
		phoneInputValue = "0396987" + getRandomString();
		phoneInput.sendKeys(phoneInputValue + "\n");
		test.log(Status.PASS, "Input value for phone field");

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập mật khẩu...')]"));
		passwordInput.sendKeys(passwordInputValue + "\n");
		test.log(Status.PASS, "Input value for password field");

		confirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Xác nhận mật khẩu...')]"));
		confirmPassword.sendKeys(confirmPasswordValue + "\n");
		test.log(Status.PASS, "Input value for confirm password field");

		// Click on register button
		registerButton = driver.findElement(By.xpath("//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']"));
		registerButton.click();
		test.log(Status.PASS, "Click on register button");
		
		//Verify user navigate to confirm page
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'The Nail')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Announcement')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Xin chào')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Vui lòng xác nhận tài khoản tại email vừa đăng kí.')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Đăng nhập ngay')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,usernameInput)]")).isDisplayed());
		test.log(Status.PASS, "Verify user navigate to confirm page successfully");
		
		@SuppressWarnings("resource")
		XSSFWorkbook AWorkbook = new XSSFWorkbook(); // Create blank workbook
		
		XSSFSheet spreadsheet = AWorkbook.createSheet("DataForRegister");
		XSSFRow row;

		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1",
				new Object[] { "USERNAME", "EMAIL", "PHONE_NUMBER", "PASSWORD", "CONFIRM_PASSWORD" });

		empinfo.put("2", new Object[] { userNameInputValue, emailInputValue, phoneInputValue, passwordInputValue, confirmPasswordValue });

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

		FileOutputStream out = new FileOutputStream(new File("DataForRegister.xlsx"));
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
