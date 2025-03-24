package stepdefinitions;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;

import globalSetup.Base;
import globalSetup.CommonElements;
import globalSetup.DriverUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatePickerTestActions implements Base {
	CommonElements com = new CommonElements(DriverUtil.getDriver());

	@Given("user is on the application page")
	public void user_is_on_the_application_page() throws IOException {
		String url = prop.getProp("url");
		com.navigateTo(url);

		try {
			com.waitForElementToDisplay(dp.pageTitle, 10);
		} catch (TimeoutException e) {
			com.reloadPage();
			try {
				com.waitForElementToDisplay(dp.pageTitle, 10);
			} catch (TimeoutException e1) {
				throw new TimeoutException("The page did not load successfully");
			}
		}
	}

	@Given("I navigate to the date picker")
	public void i_navigate_to_the_date_picker() throws Exception {
	    if(!com.isElementDisplayed("xpath",dp.filterTimeRangeBtn)){
	    	throw new Exception("The date picker button is not displayed");
	    }else {
	    	com.click(dp.filterTimeRangeBtn);
	    }
	}

	@Then("I should see all elements including relative mode, absolute mode button, and input field")
	public void i_should_see_all_elements_including_relative_mode_absolute_mode_button_calendar_and_input_field() throws Exception {
		com.checkElementPresence("xpath", dp.filterTimeRangePopup, true, "Date Picker Popup");
		com.checkElementPresence("xpath", dp.inputField, true, "Relative Range input field");
		com.checkElementPresence("xpath", dp.absoluteDateBtn, true, "Absolute Date button");
	}

	@Given("I open the date picker")
	public void i_open_the_date_picker() throws Exception {
		i_navigate_to_the_date_picker();
	}

	@Then("I should see predefined options like {string}, {string} etc")
	public void i_should_see_predefined_options_like(String string, String string2) throws Exception {
		com.checkElementPresence("xpath", dp.relative24HourBtn, true, "Last 24 hour pre defined option");
		com.checkElementPresence("xpath", dp.relative7DaysBtn, true, "Last 7 days pre defined option");
	}

	@When("I enter range {string}, {string}, {string} and check input is parsed correctly and applied")
	public void i_enter_range(String string, String string2, String string3) throws Exception {
		String[] inputValues = {string, string2, string3};
		String[] searchedRange = {"2h", "10d", "4w"};
		
		for(int i=0; i <inputValues.length; i++) {
			com.clearText("xpath",dp.inputField);
			com.type(dp.inputField,inputValues[i]);
			com.action(Keys.ENTER);
			
			Thread.sleep(1000);
			String parsedRange = "//h1[text()='Orders']//following-sibling::div//button//span[contains(text(),'"+searchedRange[i]+"')]";
			
			try {
				com.waitForElementToDisplay(parsedRange,5);
			}catch(TimeoutException e) {
				throw new TimeoutException("Input "+inputValues[i]+" was not parsed correctly to "+searchedRange[i]);
			}
			
			i_navigate_to_the_date_picker();
		}
	}

	@When("I click on Absolute Date button")
	public void i_click_on_absolute_date() {
		com.waitForElementToDisplay(dp.absoluteDateBtn, 5);
		com.click(dp.absoluteDateBtn);
	}

	@Then("the calendar view should open")
	public void the_calendar_view_should_open() {
		try {
			com.waitForElementToDisplay(dp.absoluteDateCalendar, 5);
		} catch (TimeoutException e) {
			throw new TimeoutException("The calendar view did not open");
		}
	}

	@Then("click on back button and verify relative date picker is displayed")
	public void the_date_picker_should_either_close_or_show_a_validation_message() {
		com.click(dp.backBtn);
		
		try {
			com.waitForElementToDisplay(dp.inputField, 5);
		} catch (TimeoutException e) {
			throw new TimeoutException("The Absolute date picker did not navigate back to relative date picker");
		}
    }
}
