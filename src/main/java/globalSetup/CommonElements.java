package globalSetup;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonElements implements Base {
	private WebDriver driver;
	private WebElement element = null;
	private boolean elementVisible;

	protected WebDriverWait wait;
	private WebElement dropdown = null;
	private Select selectList = null;
	private String old_win = null;
	private String lastWinHandle;
	private static String CHARACTERS;
	private static int MAX_LENGTH;
	private static SecureRandom random;

	public CommonElements(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public By getelementbytype(String type, String access_name) {
		switch (type) {
			case "id":
				return By.id(access_name);
			case "name":
				return By.name(access_name);
			case "class":
				return By.className(access_name);
			case "xpath":
				return By.xpath(access_name);
			case "css":
				return By.cssSelector(access_name);
			case "linkText":
				return By.linkText(access_name);
			case "partialLinkText":
				return By.partialLinkText(access_name);
			case "tagName":
				return By.tagName(access_name);
			default:
				return null;
		}
	}

	/**
	 * Method to open link
	 *
	 * @param url : String : URL for navigation
	 */
	public void navigateTo(String url) {
		driver.get(url);
	}

	/**
	 * Reloads Page
	 */
	public void reloadPage(){
		driver.navigate().refresh();
	}

	/**
	 * Method to click on an element
	 *
	 * @param accessName : String : Locator value (assumes xpath as locator type)
	 */
	public void click(String accessName) {
		boolean clicked = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype("xpath", accessName)));
				if (element.isEnabled()) {
					element.click();
					clicked = true;
					break;
				} 
			} catch (ElementClickInterceptedException e) {
			} catch (Exception e) {
			}
			attempts++;
		}
		if (!clicked) {
			throw new RuntimeException("Failed to click element after 10 attempts");
		}
	}

	public void jsClick(String accessName) {
		boolean clicked = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype("xpath",accessName)));
				if (element.isEnabled()) {
					JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript("arguments[0].click();", element);
					clicked = true;
					break;
				} else {
					System.out.println("Element not enabled. Attempt " + (attempts + 1) + " failed.");
				}
			} catch (ElementClickInterceptedException e) {
				System.out.println("Click intercepted. Attempt " + (attempts + 1) + " failed.");
			} catch (TimeoutException e) {
				System.out.println("Timeout waiting for element. Attempt " + (attempts + 1) + " failed.");
			} catch (Exception e) {
				System.out.println("Other exception occurred. Attempt " + (attempts + 1) + " failed.");
			}
			attempts++;
		}
		if (!clicked) {
			throw new RuntimeException("Failed to click element using JavaScript after 10 attempts.");
		}
	}

	public void clickWebElement(WebElement element) {
		element.click();
	}

	public void typeWebElement(WebElement element, String text) {
		element.sendKeys(text);
	}

	/**
	 * Method to forcefully click on an element
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 */
	public void clickForcefully(String accessType, String accessName) {
		element = wait.until(ExpectedConditions.elementToBeClickable(getelementbytype(accessType, accessName)));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Method to Double click on an element
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 */
	public void doubleClick(String accessType, String accessValue) {
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessValue)));

		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().perform();
	}

	/**
	 * Method to get page title
	 *
	 * @return String
	 */
	public String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Method to enter text into text field
	 *
	 * @param text       : String : Text value to enter in field
	 * @param accessName : String : Locator value
	 */
	public void type(String accessName, String text) {
		wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype("xpath", accessName)));
		driver.findElement(getelementbytype("xpath", accessName)).sendKeys(text);
	}

	/**
	 * Method to clear text of text field
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 */
	public void clearText(String accessType, String accessName) {
		wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		driver.findElement(getelementbytype(accessType, accessName)).clear();
	}

	/**
	 * Method to resize browser
	 *
	 * @param width  : int : Width for browser resize
	 * @param height : int : Height for browser resize
	 */
	public void resizeBrowser(int width, int height) {
		driver.manage().window().setSize(new Dimension(width, height));
	}

	/**
	 * Method to hover on element
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 */
	public void hoverOverElement(String accessType, String accessName) {
		Actions action = new Actions(driver);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		action.moveToElement(element).perform();
	}

	/**
	 * Method to scroll page to particular element
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 */
	public void scrollToElement(String accessType, String accessName) {
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * Method to scroll page to top or end
	 *
	 * @param to : String : Scroll page to Top or End
	 * @throws Exception
	 */
	public void scrollPage(String to) throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		if (to.equals("end"))
			executor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		else if (to.equals("top"))
			executor.executeScript("window.scrollTo(0, 0);");
		else
			throw new Exception("Exception : Invalid Direction (only scroll \"top\" or \"end\")");
	}

	/** Method to switch to new window */
	public void switchToNewWindow() {
		old_win = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles())
			lastWinHandle = winHandle;
		driver.switchTo().window(lastWinHandle);
	}

	/** Method to switch to old window */
	public void switchToOldWindow() {
		driver.switchTo().window(old_win);
	}

	/**
	 * Method to switch to window by title
	 *
	 * @param windowTitle : String : Name of window title to switch
	 * @throws Exception
	 */
	public void switchToWindowByTitle(String windowTitle) throws Exception {
		// System.out.println("++"+windowTitle+"++");
		old_win = driver.getWindowHandle();
		boolean winFound = false;
		for (String winHandle : driver.getWindowHandles()) {
			String str = driver.switchTo().window(winHandle).getTitle();
			// System.out.println("**"+str+"**");
			if (str.equals(windowTitle)) {
				winFound = true;
				break;
			}
		}
		if (!winFound)
			throw new Exception("Window having title " + windowTitle + " not found");
	}

	/** Method to close new window */
	public void closeNewWindow() {
		driver.close();
	}

	/**
	 * Method to switch frame using web element frame
	 *
	 * @param accessType : String : Locator type (index, id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 */
	public void switchFrame(String accessType, String accessName) {
		if (accessType.equalsIgnoreCase("index"))
			driver.switchTo().frame(accessName);
		else {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
			driver.switchTo().frame(element);
		}
	}

	/** method to switch to default content */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Method to select option from dropdown list
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 *  @param optionBy  : String :select By selectByIndex,value or Text
	 * @param option     : String : Option to select
	 * @param accessName : String : Locator value
	 */
	public void selectOptionFromDropdown(String accessType, String optionBy, String option, String accessName) {
		dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		selectList = new Select(dropdown);

		if (optionBy.equals("selectByIndex"))
			selectList.selectByIndex(Integer.parseInt(option) - 1);
		else if (optionBy.equals("value"))
			selectList.selectByValue(option);
		else if (optionBy.equals("text"))
			selectList.selectByVisibleText(option);
	}

	/**
	 * Method to verify page title
	 *
	 * @param title    : String : expected title
	 * @param testCase : Boolean : test case [true or false]
	 */
	public void checkTitle(String title, boolean testCase) throws Exception {
		String pageTitle = getPageTitle();

		if (testCase) {
			if (!pageTitle.equals(title))
				throw new Exception("Page Title Not Matched, Actual Page Title : " + pageTitle);
		} else {
			if (pageTitle.equals(title))
				throw new Exception("Page Title Matched, Actual Page Title : " + pageTitle);
		}
	}

	/**
	 * Method to verify partial page title
	 *
	 * @param partialTitle : String : partial title string
	 * @param testCase     : Boolean : test case [true or false]
	 */
	public void checkPartialTitle(String partialTitle, boolean testCase) throws Exception {
		String pageTitle = getPageTitle();
		if (testCase) {
			if (!pageTitle.contains(partialTitle))
				throw new Exception("Partial Page Title Not Present, Actual Page Title : " + pageTitle);
		} else {
			if (pageTitle.contains(partialTitle))
				throw new Exception("Partial Page Title Present, Actual Page Title : " + pageTitle);
		}
	}

	/**
	 * Method to get element text
	 * @param accessName : String : Locator value
	 * @return String
	 */
	public String getElementText(String accessName) {
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype("xpath", accessName)));
		return element.getText();
	}
	
	/**
	 * Method to get element text
	 * @param accessName : WebElement : Locator value
	 * @return String
	 */
	public String getElementTextWE(WebElement accessName) {
		element = accessName;
		return element.getText();
	}

	public String AssertThatValueDisplayOrNot(String accessName) {

		WebElement dropdown = DriverUtil.getDriver().findElement(By.xpath(accessName));
		String dropdownValue = dropdown.getAttribute("value");
		if (dropdownValue == null || dropdownValue.isEmpty()) {
			System.out.println("Field value is null.");
		} else {
			System.out.println("Field value is not null. Current value: " + dropdownValue);
		}
		return dropdownValue;
	}

	/**
	 * Method to check element text
	 *
	 * @param actualValue : String : Expected element text
	 * @param accessName  : String : Locator value
	 * @param testCase    : Boolean : test case [true or false]
	 */
	public void checkElementText(String actualValue, String accessName, boolean testCase) throws Exception {
		String elementText = getElementText(accessName);
		// System.out.println("Element text - " + elementText);

		if (testCase) {
			if (!elementText.trim().equalsIgnoreCase(actualValue)) {
				System.out.println("Actual: "+elementText+" Expected: "+actualValue);
				throw new Exception("Text Not Matched");
			}
		} else {
			if (elementText.trim().equalsIgnoreCase(actualValue)) {
				throw new Exception("Text Matched");
			}
		}
	}

	/**
	 * Method to check partial element text
	 *
	 * @param actualValue : String : Expected element text
	 * @param accessName  : String : Locator value
	 * @param testCase    : Boolean : test case [true or false]
	 */
	public void checkElementPartialText(String actualValue, String accessName, boolean testCase) throws Exception {
		String elementText = getElementText(accessName);

		if (testCase) {
			if (!elementText.contains(actualValue))
				throw new Exception("Text Not Matched");
		} else {
			if (elementText.contains(actualValue))
				throw new Exception("Text Matched");
		}
	}

	/**
	 * Method to return element status - enabled?
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @return Boolean
	 */
	public boolean isElementEnabled(String accessType, String accessName) {
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		return element.isEnabled();
	}

	/**
	 * Element enabled checking
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @param testCase   : Boolean : test case [true or false]
	 */
	public void checkElementEnable(String accessType, String accessName, boolean testCase) throws Exception {
		boolean result = isElementEnabled(accessType, accessName);
		if (testCase) {
			if (!result)
				throw new Exception("Element Not Enabled");
		} else {
			if (result)
				throw new Exception("Element Enabled");
		}
	}

	/**
	 * method to get attribute value
	 *
	 * @param accessName    : String : Locator value
	 * @param attributeName : String : attribute name
	 * @return String
	 */
	public String getElementAttribute(String accessName, String attributeName) {
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype("xpath", accessName)));
		return element.getAttribute(attributeName);
	}

	/**
	 * method to check attribute value
	 *
	 * @param attributeName  : String : attribute name
	 * @param attributeValue : String : attribute value
	 * @param accessName     : String : Locator value
	 * @param testCase       : Boolean : test case [true or false]
	 */
	public void checkElementAttribute(String attributeName, String attributeValue, String accessName, boolean testCase) throws Exception {
		String attrVal = getElementAttribute(accessName, attributeName);
		if (testCase) {
			if (!attrVal.trim().equalsIgnoreCase(attributeValue)) {
				System.out.println("Expected value was - " + attributeValue + "while actual is - " + attrVal);
				throw new Exception("Attribute Value Not Matched");
			}
		} else {
			if (attrVal.trim().equalsIgnoreCase(attributeValue)) {
				System.out.println("Expected value was - " + attributeValue + "while actual is - " + attrVal);
				throw new Exception("Attribute Value Matched");
			}
		}
	}

	/**
	 * method to get element status - displayed?
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @return Boolean
	 */
	public boolean isElementDisplayed(String accessType, String accessName) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(getelementbytype(accessType, accessName)));
		return element.isDisplayed();
	}

	public boolean InvisibilityElementDisplayed(String accessType, String accessName) {
		elementVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(getelementbytype(accessType, accessName)));
		return elementVisible;
	}

	public boolean isElementDisplay(String accessType, String accessName, int duration) {
		try {
			By byEle = getelementbytype(accessType, accessName);
			WebDriverWait wait = new WebDriverWait(DriverUtil.getDriver(), Duration.ofSeconds(duration));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(byEle));
			return element.isDisplayed();
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * method to check element presence
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @param testCase   : Boolean : test case [true or false]
	 */
	public void checkElementPresence(String accessType, String accessName, boolean testCase, String elementName) throws Exception {
		if (testCase) {
			if (!isElementDisplayed(accessType, accessName)) {
				throw new Exception(elementName+" Not Present");
			}
		} else {
			try {
				if (isElementDisplayed(accessType, accessName)) {
					throw new Exception(elementName + " is Present"); // since it is negative test and we found element
				}
			} catch (Exception e) {
				if (e.getMessage().equals("Present")) {// only raise if it present
					throw new Exception(elementName + " is Present");
				}
			}
		}
	}

	public boolean isCheckboxChecked(String accessName) {
		WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype("xpath", accessName)));
		return checkbox.isSelected();
	}

	/**
	 * method to assert radio button selected/unselected
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 * @return
	 */
	public boolean isRadioButtonSelected(String accessType, String accessName, boolean shouldBeSelected) throws Exception {
		WebElement radioButton = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		if ((!radioButton.isSelected()) && shouldBeSelected)
			throw new Exception("Radio Button not selected");
		else if (radioButton.isSelected() && !shouldBeSelected)
			throw new Exception("Radio Button is selected");
		return radioButton.isSelected();
	}

	// method to assert option from radio button group is selected/unselected
	public void isOptionFromRadioButtonGroupSelected(String accessType, String by, String option, String accessName, boolean shouldBeSelected) throws Exception {
		List<WebElement> radioButtonGroup = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getelementbytype(accessType, accessName)));

		for (WebElement rb : radioButtonGroup) {
			if (by.equals("value")) {
				if (rb.getAttribute("value").equals(option)) {
					if ((!rb.isSelected()) && shouldBeSelected)
						throw new Exception("Radio Button not selected");
					else if (rb.isSelected() && !shouldBeSelected)
						throw new Exception("Radio Button is selected");
				}
			} else if (rb.getText().equals(option)) {
				if ((!rb.isSelected()) && shouldBeSelected)
					throw new Exception("Radio Button not selected");
				else if (rb.isSelected() && !shouldBeSelected)
					throw new Exception("Radio Button is selected");
			}
		}
	}

	/**
	 * method to get javascript pop-up alert text
	 *
	 * @return String
	 */
	public String getAlertText() {
		return driver.switchTo().alert().getText();
	}

	/**
	 * method to check javascript pop-up alert text
	 *
	 * @param text : String : Text to verify in Alert
	 */
	public void checkAlertText(String text) throws Exception {
		if (!getAlertText().equals(text))
			throw new Exception("Text on alert pop up not matched");
	}

	/**
	 * Method to check if password is hidden in the password input field
	 *
	 * @param accessType : String : Locator type (id, name, class, xpath, css)
	 * @param accessName : String : Locator value
	 */

	public boolean verifyPasswordVisibility(String accessType, String accessName) {

		wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		WebElement password = driver.findElement(getelementbytype(accessType, accessName));

		String inputtype = password.getAttribute("type");

		if (inputtype.equals("password")) ;
		{
			return true;
		}
	}

	/**
	 * Method to verify if the particular option is Selected from Dropdown
	 *
	 * @param accessType       : String : Locator type (id, name, class, xpath, css)
	 * @param by               : String : Select element from dropdown by text or value
	 * @param option           : String : Element to select from dropdown
	 * @param accessName       : String : Locator value
	 * @param shouldBeSelected : Boolean : test case [true or false]
	 */
	public void isOptionFromDropdownSelected(String accessType, String by, String option, String accessName, boolean shouldBeSelected) throws Exception {
		Select selectList = null;
		WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		selectList = new Select(dropdown);

		String actualValue = "";
		if (by.equals("text"))
			actualValue = selectList.getFirstSelectedOption().getText();
		else
			actualValue = selectList.getFirstSelectedOption().getAttribute("value");

		if ((!actualValue.equals(option)) && (shouldBeSelected))
			throw new Exception("Option Not Selected From Dropwdown");
		else if ((actualValue.equals(option)) && (!shouldBeSelected))
			throw new Exception("Option Selected From Dropwdown");
	}

	// converting string to integer and removing extra symbols
	public float toInt(String string) {
		String s = string.toString();
		s = s.replaceAll(":", "").replaceAll("\\$", "").replaceAll(" ", "").replaceAll("₹", "")
				.replaceAll("[a-zA-Z]", "").replaceAll("\\)", "").replaceAll("\\(", "");
		float d = Float.valueOf(s); // Decimal
		// int i = (int)d; //No Decimal

		return d;
	}

	/**
	 * Method to wait
	 *
	 * @param time   : String : Time to wait
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public void sleep(String time) throws NumberFormatException, InterruptedException {
		// sleep method takes parameter in milliseconds
		Thread.sleep(Integer.parseInt(time) * 1000);
	}

	/**
	 * Method to Explicitly wait for element to be displayed
	 *
	 * @param accessName : String : Locator value
	 * @param duration   : String : Time to wait for element to be displayed
	 */
	public void waitForElementToDisplay(String accessName, int duration) {
		By byEle = getelementbytype("xpath", accessName);
		WebDriverWait wait = (new WebDriverWait(driver, Duration.ofSeconds(duration)));
		wait.until(ExpectedConditions.presenceOfElementLocated(byEle));
		wait.until(ExpectedConditions.visibilityOfElementLocated(byEle));
	}

	public void waitForElementToInvisible(String accessName, int duration) {
		By byEle = getelementbytype("xpath", accessName);
		WebDriverWait wait = (new WebDriverWait(driver, Duration.ofSeconds(duration)));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(byEle));
	}

	/**
	 * Method to Explicitly wait for element to be enabled=click
	 *
	 * @param accessName : String : Locator value
	 * @param duration   : String : Time to wait for element to be clickable
	 */
	public void waitForElementToClickable(String accessName, int duration) {
		By byEle = getelementbytype("xpath", accessName);
		WebDriverWait wait = (new WebDriverWait(driver, Duration.ofSeconds(duration)));
		wait.until(ExpectedConditions.elementToBeClickable(byEle));
	}

	public void waitForFieldHasAnyValueDisplayed(String accessName, int duration) {
		WebDriverWait wait = (new WebDriverWait(driver, Duration.ofSeconds(duration)));
		wait.until((driver) -> {
			WebElement element = driver.findElement(By.xpath(accessName));
			String text = element.getText();
			return text != null && !text.isEmpty();
		});
	}

	public void waitForURLRedirect(String URLText, int duration) {
		WebDriverWait wait = (new WebDriverWait(driver, Duration.ofSeconds(duration)));
		wait.until(ExpectedConditions.urlContains(URLText));
	}

	public static List<String> getSearchResultHeadersName(String accessName, String useFor) {
		List<WebElement> SearchResultHeader = DriverUtil.getDriver().findElements(By.xpath(accessName));
		List<String> SearchHeaders = new ArrayList<>();
		for (WebElement names : SearchResultHeader) {
			if (useFor.contains("Excel")) {
				SearchHeaders.add(names.getText().replaceAll(" ", "")
						.replace("KeyRelationshipHolder(orintroducedby)", "IntroducedBy")
						.replace("DD/KYCStatus", "KYC").toLowerCase());
				SearchHeaders.removeIf(text -> text.equalsIgnoreCase("Action"));
			} else if (useFor.contains("Grid")) {
				SearchHeaders.add(names.getText());
				SearchHeaders.removeIf(text -> text.contains("Company Name"));
				SearchHeaders.removeIf(text -> text.contains("Has purchased from"));
				SearchHeaders.removeIf(text -> text.contains("Action"));
			}
		}
		System.out.println("Search Result Header: " + SearchHeaders + " ===== " + SearchHeaders.size());
		return SearchHeaders;
	}

	public String getElementFromList(String key, String value) {

		List<WebElement> list = DriverUtil.getDriver().findElements(By.xpath(value));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().equalsIgnoreCase(key)) {
				key = String.format("%s[%d]", value, i + 1);
				break;
			}
		}
		return key;
	}

	public String getTextFromSearchList(String key, String value, String extraPath) {
		waitForElementToDisplay(value, 30);
		List<WebElement> list = DriverUtil.getDriver().findElements(By.xpath(value));

		for (int i = 0; i < list.size(); i++) {
			String formattedXpath = String.format(value + "[%d]" + extraPath, i + 1);
			WebElement element = DriverUtil.getDriver().findElement(By.xpath(formattedXpath));
			if (element.getText().contains(key)) {
				return formattedXpath;
			}
		}
		return null;
	}

	public void FileUploadUsingRobot(String filePath) throws AWTException {
		Robot robot = new Robot();
		StringSelection selection = new StringSelection(filePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, null);
		robot.setAutoDelay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static String generateRandomText(int length) {
		CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		MAX_LENGTH = 2000;
		random = new SecureRandom();
		if (length < 1 || length > MAX_LENGTH) {
			throw new IllegalArgumentException("Length must be between 1 and " + MAX_LENGTH);
		}
		StringBuilder stringBuilder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			stringBuilder.append(CHARACTERS.charAt(index));
			if ((i + 1) % 6 == 0 && i + 1 < length) {
				stringBuilder.append(" ");
			}
		}
		return stringBuilder.toString();
	}

	public static String generateRandomNumber(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("Length must be greater than 0");
		}
		Random random = new Random();
		StringBuilder randomNumber = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int digit = random.nextInt(10);
			randomNumber.append(digit);
		}
		return randomNumber.toString();
	}

	public void required_field_validation_message(String expectedMessage, String field) throws Exception {
		WebElement validationMessage;

		if (field.equalsIgnoreCase("email") || field.equalsIgnoreCase("company")) {
			validationMessage = driver.findElement(By.xpath("//input[@formcontrolname='" + field
					+ "']/following-sibling::small[contains(@class, 'text-danger')]"));

		} else if (field.equalsIgnoreCase("description")) {
			validationMessage = driver.findElement(By.xpath("//textarea[@formcontrolname='" + field
					+ "']/following-sibling::small[contains(@class, 'text-danger')]"));

		} else {
			throw new IllegalArgumentException("Field not supported: " + field);
		}

		// Check the text of the validation message
		String actualMessage = validationMessage.getText();

		if (!actualMessage.trim().equalsIgnoreCase(expectedMessage)) {
			throw new Exception("Validation not matched");
		}

	}

	/**
	 * Method to compare the Current and Expected URL
	 *
	 * @param expectedURL : String : Expected URL
	 */
	public void checkURL(String expectedURL) throws Exception {
		String currentUrl = driver.getCurrentUrl();

		if (!currentUrl.equalsIgnoreCase(expectedURL)) {
			throw new Exception(
					"URL did not match, Expected url is - " + expectedURL + " while current url is - " + currentUrl);
		}
	}

	/**
	 * Method to compare the Current and Expected URL
	 *
	 * @param accessValue : String : path of date field
	 * @param date        : Strign : in the format YYYY-MM-DD
	 */

	// public void datePickerJS(String accessValue, String date) {
	//
	// WebElement datePicker =
	// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accessValue)));
	//
	// // Desired date in the format YYYY-MM-DD
	// String dateValue = date; // Example date
	//
	// JavascriptExecutor js = (JavascriptExecutor) driver;
	// js.executeScript("arguments[0].value = arguments[1];", datePicker,
	// dateValue);
	//
	// // Optionally verify if the date is set correctly
	// String selectedDate = datePicker.getAttribute("value");
	// System.out.println("Selected date: " + selectedDate);
	// }
	public void datePickerJS(String accessValue, String date) {
		WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accessValue)));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = arguments[1];", datePicker, date);

		// Optionally verify if the date is set correctly
		String selectedDate = datePicker.getAttribute("value");
		System.out.println("Selected date: " + selectedDate);
	}

	public List<WebElement> findAllElements(String accessValue) {
		List<WebElement> allElements;
		allElements = driver.findElements(By.xpath(accessValue));
		return allElements;
	}

	/**
	 * Method to download file and get its path
	 *
	 * @param downloadDirectoryPath    : String : directory where file is downloaded
	 * @param downloadButtonAccessName : Strign : Locator value of download button
	 */
	public String downloadFile(String downloadDirectoryPath, String downloadButtonAccessName) throws Exception {
		String downloadDir = downloadDirectoryPath;

		// clicking the download button
		waitForElementToClickable(downloadButtonAccessName, 30);
		click(downloadButtonAccessName);
		Thread.sleep(5000);

		File downloadedFile = null;
		for (int i = 0; i < 10; i++) { // Retry for up to 10 times
			downloadedFile = getLatestFileFromDir(downloadDir);
			if (downloadedFile != null && downloadedFile.getName().endsWith(".xlsx")) {
				break;
			}
			try {
				Thread.sleep(1000); // Wait for 1 second before retrying
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (downloadedFile != null) {
			System.out.println("Downloaded file: " + downloadedFile.getName());
			// System.out.println("File path: " + downloadedFile.getAbsolutePath());
			return downloadedFile.getName();  //.getAbsolutePath();
		} else {
			System.out.println("No file downloaded.");
			return "No file downloaded.";
		}

	}

	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	public File getLatestFileFromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		// Sort files by last modified time in descending order (latest file first)
		Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

		return files[0]; // Return the latest file
	}

	public void verifyErrorMessage(String accessValue, String expectedMessage) {

		String actualMessage = getElementText(accessValue);
		// System.out.println("Actual message: " + actualMessage);

		// Verify the error message
		assert actualMessage.equals(expectedMessage)
				: "Error message verification failed. Expected: " + expectedMessage + ", but got: " + actualMessage;

	}

	public void stringValidation(String expectedValue, String actualValue, String exceptionMessage) throws Exception {
		if (!expectedValue.trim().equalsIgnoreCase(actualValue)) {
			throw new Exception(exceptionMessage);
		}
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void action(Keys enter) {
		Actions actions = new Actions(driver);
		
		actions.sendKeys(enter).perform();
	}
	
	public void actionsMouseClick() {
		Actions actions = new Actions(driver);
		actions.click();
	}

	public List<String> IsCheckboxSelected() {
		List<WebElement> ListOfCheckbox = DriverUtil.getDriver()
				.findElements(By.xpath("//label/input[@type='checkbox']"));
		List<String> gridtext = new ArrayList<>();
		for (int i = 1; i < ListOfCheckbox.size(); i++) {
			WebElement checkbox = ListOfCheckbox.get(i);
			if (checkbox.isSelected()) {
				WebElement checkboxText = checkbox.findElement(By.xpath("following-sibling::span"));
				String trueName = checkboxText.getText();
				gridtext.add(trueName);
				System.out.println(gridtext.size());
			}
		}
		return gridtext;
	}

	public String repeatNumber(int numberOfDigits, String digit) {
		// Repeat the digit the specified number of times
		return digit.repeat(Math.max(0, numberOfDigits));
	}

	public void unhide() {
		WebElement hiddenElement = driver.findElement(By.id("recaptcha-token"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('type', 'text');", hiddenElement);
	}

	public void openInNewTab(String accessName) {
		WebElement element = driver.findElement(By.xpath(accessName));
		String url = element.getAttribute("href");
		if (url != null && !url.isEmpty()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open(arguments[0], '_blank');", url);
		} else {
			System.out.println("Element does not have a valid 'href' attribute.");
		}
	}

	public WebElement getWebElement(String accessType, String accessName) {
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getelementbytype(accessType, accessName)));
		return element;
	}
	
	public void typeJS(String accessName,String string) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement tinyUrlField = driver.findElement(By.xpath(accessName));
		js.executeScript("arguments[0].innerHTML = '"+string+"';", tinyUrlField);
	}
}
