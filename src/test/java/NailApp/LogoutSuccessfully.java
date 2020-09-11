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

public class LogoutSuccessfully extends ExtendReport {
	AndroidDriver<MobileElement> driver;

	MobileElement phoneInput;
	MobileElement passwordInput;
	MobileElement loginButton;
	MobileElement labelHomePage;
	MobileElement iconHomePage;
	MobileElement iconNotification;
	MobileElement iconOrderHistory;
	MobileElement burgerIcon;
	MobileElement logoutIcon;
	MobileElement logoutButton;
	MobileElement notificateLabel;
	MobileElement notificateMessage;
	MobileElement confirmLabel;
	
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
	public void TestLogoutSuccessfully() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		ExtentTest test = extent.createTest("Logout Successfully", "Mobile Automation Testing");
		test.log(Status.INFO, "Test Log out Started");

		phoneInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập số điện thoại...')]"));
		phoneInput.sendKeys(phoneInputValue + "\n");
		test.log(Status.PASS, "Input value for phone field");

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Nhập mật khẩu...')]"));
		passwordInput.sendKeys(passwordInputValue + "\n");
		test.log(Status.PASS, "Input value for password field");
		
		// Click on register button
		List<MobileElement> textView = (List<MobileElement>) driver.findElements(By.className("android.widget.TextView"));
		textView.get(4).click();
		test.log(Status.PASS, "Click on register button");
		
		burgerIcon = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[35,119][1297,225]']"));
		burgerIcon.click();
		test.log(Status.PASS, "Click on burger icon");
		
		logoutIcon = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[853,2227][1175,2382]']"));
		logoutIcon.click();
		test.log(Status.PASS, "Click on logout icon");
		
		logoutButton = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[825,1404][1105,1470]']"));
		logoutButton.click();
		test.log(Status.PASS, "Click on logout button");
		
		// Verify user logout successfully 
		notificateLabel = driver.findElement(By.id("android:id/alertTitle"));
		notificateMessage = driver.findElement(By.id("android:id/message"));
		confirmLabel = driver.findElement(By.id("android:id/button1"));
		test.log(Status.PASS, "Verify user logout successfully");
        
	}
}