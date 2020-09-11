package NailApp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class LoginUnsuccessfully extends ExtendReport{
	AndroidDriver<MobileElement> driver;

	MobileElement phoneInput;
	MobileElement passwordInput;
	MobileElement loginButton;
	MobileElement labelPhoneNumber;
	MobileElement labelPassword;
	
	String phoneInputValue = "0967258206";
	String passwordInputValue = "123456789";
	String wrongPhoneInputValue = "0964972042";
	String wrongPasswordInputValue = "123123dieu@";
	
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
	
	@Test
	public void test1_blankForAllRequiredField() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Blank For All Required Fields", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Blank For All Required Fields Start");
		
		// Click on register button
		List<MobileElement> textView = (List<MobileElement>) driver.findElements(By.className("android.widget.TextView"));
		textView.get(4).click();
		test.log(Status.PASS, "Click on login button");
				
		//Verify warning message is display
		labelPhoneNumber = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập số điện thoại của bạn')]"));
		test.log(Status.PASS, "Warning message is display");
		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu')]"));
		test.log(Status.PASS, "Warning message is display");
		Thread.sleep(3000);
	}
	
	@Test
	public void test2_inputPhoneNumber() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Phone Number Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Phone Number Field Start");
		
		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập số điện thoại...')]"));
		phoneInput.sendKeys(phoneInputValue + "\n");
		test.log(Status.PASS, "Input value for phone field");

		// Click on register button
		List<MobileElement> textView = (List<MobileElement>) driver.findElements(By.className("android.widget.TextView"));
		textView.get(4).click();
		test.log(Status.PASS, "Click on login button");
				
		//Verify warning message is display
		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Vui lòng nhập mật khẩu')]"));
		test.log(Status.PASS, "Warning message is display");
		Thread.sleep(3000);
	}
	
	@Test
	public void test3_inputWrongPassword() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("Input Password Field", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Input Password Field Start");
		
		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập mật khẩu...')]"));
		passwordInput.sendKeys(wrongPhoneInputValue + "\n");
		test.log(Status.PASS, "Input value for password field");

		// Click on register button
		List<MobileElement> textView = (List<MobileElement>) driver.findElements(By.className("android.widget.TextView"));
		textView.get(4).click();
		test.log(Status.PASS, "Click on login button");
		
		// Verify warning message is display
		labelPhoneNumber = driver.findElement(By.xpath("//*[contains(@text,'Số điện thoại hoặc mật khẩu không đúng')]"));
		test.log(Status.PASS, "Warning message is display");
		Thread.sleep(3000);
	}
	
//	@Test
//	public void test4_wrongAccount() throws InterruptedException, IOException {
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		ExtentTest test = extent.createTest("Input Phone Number Field", "Mobile Automation Testing");
//		test.log(Status.INFO, "Test Input Phone Number Field Start");
//		
//		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập số điện thoại...')]"));
//		phoneInput.sendKeys(wrongPhoneInputValue + "\n");
//		test.log(Status.PASS, "Input value for phone field");
//
//		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập mật khẩu...')]"));
//		passwordInput.sendKeys(wrongPasswordInputValue + "\n");
//		test.log(Status.PASS, "Input value for password field");
//
//		// Click on register button
//		List<MobileElement> textView = (List<MobileElement>) driver.findElements(By.className("android.widget.TextView"));
//		textView.get(4).click();
//		test.log(Status.PASS, "Click on login button");
//				
//		//Verify warning message is display
//		labelPassword = driver.findElement(By.xpath("//*[contains(@text,'Số điện thoại hoặc mật khẩu không đúng')]"));
//		test.log(Status.PASS, "Warning message is display");
//		Thread.sleep(3000);
//	}
}
