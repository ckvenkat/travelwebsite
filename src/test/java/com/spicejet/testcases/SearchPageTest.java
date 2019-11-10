package com.spicejet.testcases;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.spicejet.base.TestBase;
import com.spicejet.pages.HomePage;
import com.spicejet.pages.LaunchPage;
import com.spicejet.pages.SearchResultPage;
import com.spicejet.utils.ReadData;

public class SearchPageTest extends TestBase {
	
	ExtentTest test; 
	ExtentReports extent;
	
	@Test(dataProvider="getTestData")
	public void lunchPageTest(String fromCity, String toCity,String departureDate, String departureMonth, String returnDate, String returnMonth, String adults, String child, String infants) throws IOException, InterruptedException {
		extent = extentReportIni();
		test = extent.createTest("Open Spice Jet Page");
		LaunchPage launchPage = new LaunchPage();
		PageFactory.initElements(driver, launchPage);
		HomePage homepage = launchPage.goToHomePage(test);
		PageFactory.initElements(driver, homepage);
		homepage.searchFlights(test,fromCity,toCity,departureDate,departureMonth,returnDate,returnMonth,adults,child,infants);
		SearchResultPage searchResultPage = new SearchResultPage();
		PageFactory.initElements(driver, searchResultPage);
		searchResultPage.verifySearchResultPage(test);
	}
	
	@AfterTest
	public void flushReport() {
		extent.flush();
		driver.close();
	}
	
	@DataProvider
	public Object[][] getTestData() throws IOException{
		ReadData data = new ReadData();
		return data.readVariant();
	}
}
