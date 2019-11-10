package com.spicejet.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.spicejet.base.TestBase;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends TestBase{
	
	@FindBy(xpath = "//input[@id=\"ctl00_mainContent_rbtnl_Trip_1\"]")
    public WebElement roundTripRadioButton;
	
	@FindBy(xpath= "//*[@id=\"marketCityPair_1\"]//span[@id=\"ctl00_mainContent_ddl_originStation1_CTXTaction\"]")
	public WebElement fromcityDropDown;
	
	@FindBy(xpath= "//div[@class=\"picker-second\"]//span[@id=\"view_fulldate_id_2\"]")
	public WebElement returnDateCalender;

    @FindBy(id = "divpaxinfo")
    public WebElement passengerDropdown;
    
    @FindBy(xpath="//div[@class=\"picker-second\"]//span[@id=\"view_fulldate_id_2\"]")
    public WebElement returnCalender;
    
    @FindBy(xpath = "//input[@type=\"submit\"]")
    public WebElement submit;
    
    public WebElement fromCity(String fromCityCode) {
    	String xpath = "//a[@value=\""+fromCityCode+"\"]";
    	return driver.findElement(By.xpath(xpath));
    }
    
    public WebElement toCity(String toCityCode) {    	
    	String xpath = toCityXpath(toCityCode);
    	return driver.findElement(By.xpath(xpath));
    }
    
    public String toCityXpath(String toCityCode) {
    	 String xpath = "(//a[@value=\""+toCityCode+"\"])[2]";
    	 return xpath;
    }
    
    public void searchFlights(ExtentTest test,String fromCity, String toCity,String departDate, String departMonth, String returnDate, String returnMonth, String adults, String child, String infants) throws InterruptedException{
    	
    	test.log(Status.INFO, "Starting Search Flight");
    	if(!roundTripRadioButton.isSelected()) {
    		roundTripRadioButton.click();
    	}
    	fromcityDropDown.click();
    	fromCity(fromCity).click();
    	test.log(Status.INFO, "From City "+fromCity);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(toCityXpath(toCity))));
    	toCity(toCity).click();
    	test.log(Status.INFO, "To City "+toCity);
    	
		calenderDatePick(departMonth, departDate);
		test.log(Status.INFO, "Departure Details "+departDate+" "+departMonth);
		wait.until(ExpectedConditions.visibilityOf(returnCalender));
		wait.until(ExpectedConditions.elementToBeClickable(returnCalender));
		Actions actions = new Actions(driver);
		actions.moveToElement(returnCalender).click().perform();
		
		calenderDatePick(returnMonth, returnDate);
		test.log(Status.INFO, "Arrival Details "+returnDate+" "+returnMonth);
		wait.until(ExpectedConditions.visibilityOf(passengerDropdown));
		wait.until(ExpectedConditions.elementToBeClickable(passengerDropdown));
		passengerDropdown.click();
		
		noOfPassengers(Integer.parseInt(adults),Integer.parseInt(child),Integer.parseInt(infants));
		test.log(Status.INFO, "Passenger Details Adults "+adults+" Child"+child+" Infants"+infants);
		wait.until(ExpectedConditions.elementToBeClickable(submit));
		submit.click();	
		
    }
    
    public void calenderDatePick(String month, String date) {
		boolean currentMonthStatus = false;
		while (!currentMonthStatus) {
			if (driver.findElement(By.cssSelector(".ui-datepicker-month")).getText().contains(month)) {
				pickDate(date);
				currentMonthStatus = true;
			} else {
				driver.findElement(By.cssSelector("[class='ui-icon ui-icon-circle-triangle-e']")).click();
			}
		}
	}
    
    public void pickDate(String date) {
		List<WebElement> dates = driver.findElements(By.xpath("//a[@class=\"ui-state-default\"]"));
		// Grab common attribute//Put into list and iterate
		int count = dates.size();
		for (int i = 0; i < count; i++) {
			String currentDate = driver.findElements(By.xpath("//a[@class=\"ui-state-default\"]")).get(i).getText();
			if (currentDate.equalsIgnoreCase(date)) {
				driver.findElements(By.xpath("//a[@class=\"ui-state-default\"]")).get(i).click();
				break;
			}
		}
	}
    
    public static void noOfPassengers(int adults, int child, int infants) throws InterruptedException {
    	if(adults>=0)
    		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//select[@id=\"ctl00_mainContent_ddl_Adult\"]//option[@value='"+adults+"']"))));
    		driver.findElement(By.xpath("//select[@id=\"ctl00_mainContent_ddl_Adult\"]//option[@value='"+adults+"']")).click();
    	if(child>=0)
    		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//select[@id=\"ctl00_mainContent_ddl_Child\"]//option[@value='"+child+"']"))));
    		driver.findElement(By.xpath("//select[@id=\"ctl00_mainContent_ddl_Child\"]//option[@value='"+child+"']")).click();
    	if(infants>=0)
    		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//select[@id=\"ctl00_mainContent_ddl_Infant\"]//option[@value='"+infants+"']"))));
    		driver.findElement(By.xpath("//select[@id=\"ctl00_mainContent_ddl_Infant\"]//option[@value='"+infants+"']")).click();
	}
    
}


