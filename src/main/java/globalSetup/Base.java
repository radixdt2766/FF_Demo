package globalSetup;

import org.openqa.selenium.interactions.Actions;

import locators.DatePickerTestLocators;
import locators.OrderMainHubTestLocators;
import locators.RegressionTestLocators;

public interface Base {
	//global setup classes
	GlobalUtils glObj = new GlobalUtils();
	GetProperties prop = new GetProperties();
	ExcelUtility xl = new ExcelUtility();
	Actions actions = new Actions(DriverUtil.getDriver());
	
	//test locators classes
	OrderMainHubTestLocators oh = new OrderMainHubTestLocators();
	DatePickerTestLocators dp = new DatePickerTestLocators();
}
