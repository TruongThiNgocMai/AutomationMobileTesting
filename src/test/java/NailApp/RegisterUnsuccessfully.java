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

public class RegisterUnsuccessfully extends ExtendReport {
	AndroidDriver<MobileElement> driver;

	MobileElement registerLink;
	MobileElement registerButton;
	MobileElement usernameInput;
	MobileElement emailInput;
	MobileElement phoneInput;
	MobileElement passwordInput;
	MobileElement confirmPassword;
	MobileElement labelUsername;
	MobileElement labelEmail;
	MobileElement labelPhone;
	MobileElement labelPassword;
	MobileElement labelConfirmPassword;
	
	String userNameInputValue = "MaiMai";
	String emailInputValue;
	String phoneInputValue;
	String passwordInputValue = "maimai99";
	String confirmPasswordValue = "maimai99";
	String wrongEmailInputValue = "mai.com";
	String wrongPhoneInputValue = "ngocmai";
	String wrongPasswordInputValue = "mai123";
	String wrongConfirmPasswordValue = "maimai";

	//Setup device and environment of Appium
	@BeforeTest
	public void setup () {
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

	//Scroll Method
	public void scroll (String visibleText) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ visibleText + "\").instance(0))");
	}
	
	// Verify navigate to Register screen
	public void navigateToRegisterScreen () {
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'The Nail')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Đăng kí')]")).isDisplayed());
	}
	
	//Click Method
    public void clickElement (MobileElement mobileElement, String xpathExpression) {
    	mobileElement = driver.findElement(By.xpath(xpathExpression));
    	mobileElement.click();
    }
    
    //Write Text Method
    public void writeText(MobileElement mobileElement, String xpathExpression, String valueInput) {
    	mobileElement = driver.findElement(By.xpath(xpathExpression));
    	mobileElement.sendKeys(valueInput + "\n");
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
	public void test1_blankForAllRequiredField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Blank For All Required Fields", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Blank For All Required Fields Start");
		
		clickElement(registerLink, "//*[contains(@text,'Đăng kí')]");
		test.log(Status.PASS, "Click on register link");

		navigateToRegisterScreen();
		test.log(Status.PASS, "Verify user navigate to Regiser page successfully");

		scroll("Đã có tài khoản");
		
		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
		test.log(Status.PASS, "Click on register button");
		
		//Verify warning message is display
		labelUsername = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập tên của bạn')]"));
		test.log(Status.PASS, "Warning message for user is display");
		labelEmail = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập email của bạn')]"));
		test.log(Status.PASS, "Warning message for email is display");
		labelPhone = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập số điện thoại của bạn')]"));
		test.log(Status.PASS, "Warning message for phone is display");
		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu')]"));
		test.log(Status.PASS, "Warning message for password is display");
		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu xác nhận')]"));
		test.log(Status.PASS, "Warning message for confirm password is display");
		Thread.sleep(3000);
	}
	
	@Test
	public void test2_InputUserNameField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Username Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Username Field Start");
		
		writeText(usernameInput, "//*[contains(@text,'Tên đăng nhập...')]", userNameInputValue);
		test.log(Status.PASS, "Input value for username");
		
		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
		test.log(Status.PASS, "Click on register button");
		
		//Verify warning message is display 
		labelEmail = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập email của bạn')]"));
		test.log(Status.PASS, "Warning message for email is display");
		labelPhone = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập số điện thoại của bạn')]"));
		test.log(Status.PASS, "Warning message for phone is display");
		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu')]"));
		test.log(Status.PASS, "Warning message for password is display");
		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu xác nhận')]"));
		test.log(Status.PASS, "Warning message for confirm password is display");
		Thread.sleep(3000);
	}
	
//	@Test
//	public void test3_InputWrongFormatForEmailField() throws InterruptedException, IOException {
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		ExtentTest test = extent.createTest("Input Wrong Format For Email Field", "Mobile Automation Testing");
//		test.log(Status.INFO, "Test Input Wrong Format For Email Field Start");
//		
//		writeText(emailInput, "//*[contains(@text,'Nhập email...')]", wrongEmailInputValue);
//		test.log(Status.PASS, "Input value for email");
//		
//		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
//		test.log(Status.PASS, "Click on register button");
//		
//		//Verify warning message is display 
//		labelEmail = driver.findElement(By.xpath("//*[contains(@text,'Email không hợp lệ!')]"));
//		test.log(Status.PASS, "Warning message is display");
//		Thread.sleep(3000);
//	}
//	
	@Test
	public void test3_InputEmailField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Email Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Email Field Start");
		
		emailInputValue = "mai" + getRandomString() + "@gmail.com";
		
		writeText(emailInput, "//*[contains(@text,'Nhập email...')]", emailInputValue);
		test.log(Status.PASS, "Input value for email");
		
		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
		test.log(Status.PASS, "Click on register button");
		
		//Verify warning message is display 
		labelPhone = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập số điện thoại của bạn')]"));
		test.log(Status.PASS, "Warning message for phone is display");
		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu')]"));
		test.log(Status.PASS, "Warning message for password is display");
		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu xác nhận')]"));
		test.log(Status.PASS, "Warning message for confirm password is display");
		Thread.sleep(3000);
	}
	
//	@Test
//	public void test5_InputWrongPhoneNumberField() throws InterruptedException, IOException {
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		ExtentTest test = extent.createTest("Input Phone Number Field With Text Format", "Mobile Automation Testing");
//		test.log(Status.INFO, "Test Input Phone Number Field With Text Format Start");
//		
//		writeText(phoneInput, "//*[contains(@text,'Nhập số điện thoại...')]", wrongPhoneInputValue);
//		test.log(Status.PASS, "Input value for phone number");
//		
//		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
//		test.log(Status.PASS, "Click on register button");
//		
//		//Verify warning message is display 
//		labelPhone = driver.findElement(By.xpath("//*[contains(@text,'Số điện thoại không hợp lệ!')]"));
//		test.log(Status.PASS, "Warning message is display");
//		Thread.sleep(3000);
//	}
	
	@Test
	public void test4_InputPhoneNumberField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Phone Number Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Phone Number Field Start");
		
		phoneInputValue = "0396987" + getRandomString();
		
		writeText(phoneInput, "//*[contains(@text,'Nhập số điện thoại...')]", phoneInputValue);
		test.log(Status.PASS, "Input value for phone number");
		
		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
		test.log(Status.PASS, "Click on register button");
		
		//Verify warning message is display 
		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu')]"));
		test.log(Status.PASS, "Warning message for password is display");
		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu xác nhận')]"));
		test.log(Status.PASS, "Warning message for confirm password is display");
		Thread.sleep(3000);
	}
	
//	@Test
//	public void test7_InputPasswordLessThan8Characters() throws InterruptedException, IOException {
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		ExtentTest test = extent.createTest("Input Password Field With Less Than 8 Characters", "Mobile Automation Testing");
//		test.log(Status.INFO, "Test Input Password Field With Less Than 8 Characters Start");
//		
//		writeText(passwordInput, "//*[contains(@text,'Nhập mật khẩu...')]", wrongPasswordInputValue);
//		test.log(Status.PASS, "Input value for password");
//		
//		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
//		test.log(Status.PASS, "Click on register button");
//
//		//Verify warning message is display 
//		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Password không hợp lệ!')]"));
//		test.log(Status.PASS, "Warning message is display");
//		Thread.sleep(3000);
//	}
//	
	@Test
	public void test5_InputPasswordField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Password Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Password Field Start");
		
		writeText(passwordInput, "//*[contains(@text,'Nhập mật khẩu...')]", passwordInputValue);
		test.log(Status.PASS, "Input value for password");
		
		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
		test.log(Status.PASS, "Click on register button");

		//Verify warning message is display 
		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu xác nhận')]"));
		test.log(Status.PASS, "Warning message for confirm password is display");
		Thread.sleep(3000);
	}
	
//	@Test
//	public void test9_InputPasswordAndPasswordConfirmNotMatch() throws InterruptedException, IOException {
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		ExtentTest test = extent.createTest("Input Confirm Password Field", "Mobile Automation Testing");
//		test.log(Status.INFO, "Test Input Confirm Password Field Start");
//		
//		writeText(confirmPassword, "//*[contains(@text,'Xác nhận mật khẩu...')]", wrongConfirmPasswordValue);
//		test.log(Status.PASS, "Input value for confirm password");
//	
//		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
//		test.log(Status.PASS, "Click on register button");
//		
//		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Password không khớp!')]"));
//		test.log(Status.PASS, "Warning message is display");
//		Thread.sleep(3000);
//	}
	
	@Test
	public void test6_InputWrongConfirmPasswordField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Confirm Password Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Confirm Password Field Start");
		
		writeText(confirmPassword, "//*[contains(@text,'Xác nhận mật khẩu...')]", wrongConfirmPasswordValue);
		test.log(Status.PASS, "Input value for confirm password");
	
		clickElement(registerButton, "//android.view.ViewGroup[@bounds='[105,1917][1335,2078]']");
		test.log(Status.PASS, "Click on register button");
		
		//Verify user navigate to confirm page
		labelConfirmPassword = driver.findElement(By.xpath("//*[contains(@text,'Mật khẩu không trùng khớp')]"));
		test.log(Status.PASS, "Warning message for confirm password is display");
		Thread.sleep(3000);
	}
	
	@AfterTest
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception ign) {
		}
	}
}
