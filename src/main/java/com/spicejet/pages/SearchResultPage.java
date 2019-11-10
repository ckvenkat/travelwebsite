package com.spicejet.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.ExtentTest;
import com.spicejet.base.TestBase;

public class SearchResultPage extends TestBase {

	@FindBy(xpath = "//span[@class=\"button-continue-text\"]")
    public WebElement searchResultPageText;
	
	public void verifySearchResultPage(ExtentTest test) throws IOException, InterruptedException {
		wait.until(ExpectedConditions.textToBePresentInElement(searchResultPageText, "CONTINUE"));
		wait.until(ExpectedConditions.visibilityOf(searchResultPageText));
		String expectedString = driver.findElement(By.xpath("//span[@class=\"button-continue-text\"]")).getText();
		if(expectedString.equalsIgnoreCase("continue")) {
			assertSuccess("Search Result Successfull",test);
		}else {
			assertFailure("Search Result not found",test);
		}
	}
	
}
