package com.spicejet.pages;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.spicejet.base.TestBase;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;

public class LaunchPage extends TestBase {

	public HomePage goToHomePage (ExtentTest test) throws IOException{
		propertyIni();
		browserIni(test);
        test.log(Status.INFO, "Opening the url - "+prop.getProperty("url"));
        driver.get(prop.getProperty("url"));
        if(  driver.getTitle().equals("SpiceJet - Flight Booking for Domestic and International, Cheap Air Tickets")) {
        	assertSuccess("URL Opened Succesfully", test);
        }else {
        	assertFailure("Loading URL Failed",test);
        }
        HomePage homePage = new HomePage();
        PageFactory.initElements(driver, homePage);
        return homePage;
    }
}
