package com.spicejet.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestBase {
	
    public static WebDriver driver;
    public static Properties prop;
    public static ExtentReports extent;
    public static WebDriverWait wait;
       

	public void propertyIni() throws IOException {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\resources\\com\\spicejet\\config\\config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public ExtentReports extentReportIni() {
		Date d=new Date();
        String fileName="SpiceJet_Test_"+d.toString().replace(":", "_").replace(" ", "_")+".html";
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\report\\"+fileName);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		return extent;
	}
		
	public void browserIni(ExtentTest test) {
		String browserName = prop.getProperty("browserName");
		test.log(Status.INFO, "Opening browser "+browserName );
		if(browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\com\\spicejet\\resources\\chromedriver.exe");
			driver = new ChromeDriver();			
		}else if(browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\com\\spicejet\\resources\\geckodriver.exe");
			driver = new FirefoxDriver();			
		}else if(browserName.equals("IE")) {
			System.setProperty("webdriver.IE.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\com\\spicejet\\resources\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();			
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver,120);
		test.log(Status.INFO, "Browser opened successfully "+ browserName);
	}
	
	 public void assertFailure(String message, ExtentTest test) throws IOException{
		 	test.log(Status.FAIL, message);
		 	takeScreenShot(message);
	        test.addScreenCaptureFromPath(System.getProperty("user.dir")+"//screenshots//"+message+".png");
	    }

	    public void assertSuccess(String message, ExtentTest test) throws IOException{
	        test.log(Status.PASS,message);
	        takeScreenShot(message);
	        test.addScreenCaptureFromPath(System.getProperty("user.dir")+"//screenshots//"+message+".png");
	    }

	    public void takeScreenShot(String message){
	        String screenshotFile=message+".png";
	        String filePath=System.getProperty("user.dir")+"//screenshots//"+screenshotFile;
	        // store screenshot in that file
	        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

	        try {
	        	FileHandler.copy(scrFile, new File(filePath));
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

}

