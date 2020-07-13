package mobileTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.junit.Assert;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SignIn extends ExtendReport {
	AndroidDriver<MobileElement> driver;

	MobileElement userNameInput;
	MobileElement passwordInput;
	MobileElement loginButton;

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

	@SuppressWarnings("resource")
	public String ReadCellData(int rowIndex, int columnIndex) throws IOException {
		String value = null;
		Workbook wb = null;
		// Đọc excel file
		FileInputStream inputStream = new FileInputStream(new File("AllData.xlsx"));
		wb = new XSSFWorkbook(inputStream);

		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(rowIndex);
		Cell cell = row.getCell(columnIndex);
		value = cell.getStringCellValue();
		return value;
	}

	@Test
	public void SignInAccount() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ExtentTest test = extent.createTest("RegisterTest", "Demo sample description");
		test.log(Status.INFO, "Test Sign In with get data from excel file started");
		SignIn signIn = new SignIn();
		//get data of userName field
		String userNameValue = signIn.ReadCellData(1, 4);
		//get data of password field
		String passwordValue = signIn.ReadCellData(1, 5);
		test.log(Status.PASS, "Get data from excel file");

		userNameInput = driver.findElement(By.xpath("//*[contains(@text,'Username')]"));
		userNameInput.sendKeys(userNameValue);
		test.log(Status.PASS, "Input valid username");

		passwordInput = driver.findElement(By.xpath("//*[contains(@text,'Password')]"));
		passwordInput.sendKeys(passwordValue);
		test.log(Status.PASS, "Input valid password");

		loginButton = driver.findElement(By.xpath("//*[contains(@text,'LOGIN')]"));
		loginButton.click();
		test.log(Status.PASS, "Click on login button");

		// verify login successfully
		Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@text,'RECOMMENDED STAY FOR YOU')]")).getText(),
				"RECOMMENDED STAY FOR YOU");
		Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@text,'POPULAR DESTINATION')]")).getText(),
				"POPULAR DESTINATION");
		Assert.assertEquals(driver.findElement(By.xpath("//*[contains(@text,'HOME STAY AROUND THE WORLD')]")).getText(),
				"HOME STAY AROUND THE WORLD");
		test.log(Status.PASS, "Verify login sccessfully");
	}

	@After
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception ign) {
		}
	}
}
