package globalSetup;

import org.openqa.selenium.interactions.Actions;

import locators.FunctionalTestLocators;
import locators.RegressionTestLocators;
import locators.UITestLocators;

public interface Base {
	//global setup classes
	GlobalUtils glObj = new GlobalUtils();
	GetProperties prop = new GetProperties();
	ExcelUtility xl = new ExcelUtility();
	Actions actions = new Actions(DriverUtil.getDriver());
	
	//locators classes
	FunctionalTestLocators fl = new FunctionalTestLocators();
	RegressionTestLocators rl = new RegressionTestLocators();
	UITestLocators ul = new UITestLocators();
}