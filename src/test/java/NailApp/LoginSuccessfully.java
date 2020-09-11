package NailApp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class LoginSuccessfully extends ExtendReport {
	AndroidDriver<MobileElement> driver;

	MobileElement phoneInput;
	MobileElement passwordInput;
	MobileElement loginButton;
	MobileElement labelHomePage;
	MobileElement iconHomePage;
	MobileElement iconNotification;
	MobileElement iconOrderHistory;
	
	String phoneInputValue = "0967258206";
	String passwordInputValue = "123456789";
	
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
	public void registerNewAccountSuccessfully() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		ExtentTest test = extent.createTest("Login Successfully", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Login Started");

		// Verify navigate to Login screen
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'The Nail')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Đăng nhập')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Nhập số điện thoại...')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Nhập mật khẩu...')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Đăng kí')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Quên mật khẩu?')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Đăng kí với')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'or')]")).isDisplayed());
		test.log(Status.PASS, "Verify user navigate to Login page successfully");
		
		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập số điện thoại...')]"));
		phoneInput.sendKeys(phoneInputValue + "\n");
		test.log(Status.PASS, "Input value for phone field");

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập mật khẩu...')]"));
		passwordInput.sendKeys(passwordInputValue + "\n");
		test.log(Status.PASS, "Input value for password field");
		
		// Click on register button
		List<MobileElement> textView = (List<MobileElement>) driver.findElements(By.className("android.widget.TextView"));
		textView.get(4).click();
		test.log(Status.PASS, "Click on Login button");
        
		//Verify navigate to homepage 
		labelHomePage = driver.findElement(By.xpath("//*[contains(@content-desc,'Trang chủ')]"));
		iconHomePage = driver.findElement(By.xpath("//*[contains(@content-desc,'selected, Trang chủ, tab, 1 out of 3')]"));
		iconNotification = driver.findElement(By.xpath("//*[contains(@content-desc,'Thông báo, tab, 2 out of 3')]"));
		iconOrderHistory = driver.findElement(By.xpath("//*[contains(@content-desc,'Lịch sử GD, tab, 3 out of 3')]"));
		test.log(Status.PASS, "Verify navigate to homepage");
	}
}
