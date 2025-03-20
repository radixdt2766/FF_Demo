package stepdefinitions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;

import globalSetup.Base;
import globalSetup.CommonElements;
import globalSetup.DriverUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderMainHubTestActions implements Base {
	CommonElements com = new CommonElements(DriverUtil.getDriver());

	@Given("I verify all the columns are present")
	public void Iverifyallthecolumnsarepresent() throws Exception {
		com.checkElementPresence("xpath", oh.orderIDColumn, true, "Order ID Column");
		com.checkElementPresence("xpath", oh.customerColumn, true, "Customer Column");
		com.checkElementPresence("xpath", oh.datesColumn, true, "Dates Column");
		com.checkElementPresence("xpath", oh.tagsColumn, true, "Tags Column");
		com.checkElementPresence("xpath", oh.statusColumn, true, "Status Column");
		com.checkElementPresence("xpath", oh.totalColumn, true, "Total Column");
		com.checkElementPresence("xpath", oh.actionsColumn, true, "Actions Column");
		
		com.click(dp.filterTimeRangeBtn);
		com.clearText("xpath",dp.inputField);
		com.type(dp.inputField,"90d");
		com.action(Keys.ENTER);
	}

	@When("I sort by Order ID and check the results")
	public void i_click_on_column_headers_such_as_or() throws Exception {
		Thread.sleep(500);
		int getFirstRowOrderId = Integer.valueOf(com.getElementText("//tbody//tr[1]//td[2]//div//span[2]").replaceAll("\\D+", ""));
		int getSecondRowOrderId = Integer.valueOf(com.getElementText("//tbody//tr[2]//td[2]//div//span[2]").replaceAll("\\D+", ""));

		if (getFirstRowOrderId > getSecondRowOrderId) {
			com.click(oh.sortOrderIDBtn);
			Thread.sleep(1000);
			int getFirstRowOrderIdSortedDesc = Integer
					.valueOf(com.getElementText("//tbody//tr[1]//td[2]//div//span[2]").replaceAll("\\D+", ""));
			int getSecondRowOrderIdSortedDesc = Integer
					.valueOf(com.getElementText("//tbody//tr[2]//td[2]//div//span[2]").replaceAll("\\D+", ""));

			if (getFirstRowOrderIdSortedDesc > getSecondRowOrderIdSortedDesc) {
				throw new Exception("Order ID shorting is not properly functional");
			}

		} else {
			com.click(oh.sortOrderIDBtn);
			Thread.sleep(1000);
			int getFirstRowOrderIdSortedAsc = Integer
					.valueOf(com.getElementText("//tbody//tr[1]//td[2]//div//span[2]").replaceAll("\\D+", ""));
			int getSecondRowOrderIdSortedAsc = Integer
					.valueOf(com.getElementText("//tbody//tr[2]//td[2]//div//span[2]").replaceAll("\\D+", ""));

			if (getSecondRowOrderIdSortedAsc > getFirstRowOrderIdSortedAsc) {
				throw new Exception("Order ID shorting is not properly functional");
			}
		}
	}

	@When("I sort by Customer and check the results")
	public void i_click_on_column_Customer() throws Exception {
		String getFirstRowCustomerName = com.getElementText("//tbody//tr[1]//td[3]//div//span[1]").toLowerCase();
		String getSecondRowCustomerName = com.getElementText("//tbody//tr[2]//td[3]//div//span[1]").toLowerCase();

		int firstRowCustomer = getFirstRowCustomerName.charAt(0) - 'a' + 1;
		int secondRowCustomer = getSecondRowCustomerName.charAt(0) - 'a' + 1;

		if (firstRowCustomer > secondRowCustomer) {
			com.click(oh.sortCustomer);
			Thread.sleep(1000);
			String getFirstRowCustomerNameDesc = com.getElementText("//tbody//tr[1]//td[3]//div//span[1]").toLowerCase();
			String getSecondRowCustomerNameDesc = com.getElementText("//tbody//tr[2]//td[3]//div//span[1]").toLowerCase();

			int firstRowCustomerDesc = getFirstRowCustomerNameDesc.charAt(0) - 'a' + 1;
			int secondRowCustomerDesc = getSecondRowCustomerNameDesc.charAt(0) - 'a' + 1;

			if (firstRowCustomerDesc > secondRowCustomerDesc) {
				throw new Exception("Customer shorting is not properly functional");
			}
		} else {
			com.click(oh.sortCustomer);
			Thread.sleep(1000);
			String getFirstRowCustomerNameAsc = com.getElementText("//tbody//tr[1]//td[3]//div//span[1]").toLowerCase();
			String getSecondRowCustomerNameAsc = com.getElementText("//tbody//tr[2]//td[3]//div//span[1]").toLowerCase();

			int firstRowCustomerAsc = getFirstRowCustomerNameAsc.charAt(0) - 'a' + 1;
			int secondRowCustomerAsc = getSecondRowCustomerNameAsc.charAt(0) - 'a' + 1;

			if (firstRowCustomerAsc < secondRowCustomerAsc) {
				throw new Exception("Customer shorting is not properly functional");
			}
		}
	}

	@When("I click on an order’s state field")
	public void i_click_on_an_order_s_state_field() {
		com.click(oh.firstRowPopover);
	}

	@Then("the popover should display payment, fulfillment, and shipment statuses")
	public void the_popover_should_display_payment_fulfillment_and_shipment_statuses() throws Exception {
		try {
			com.waitForElementToDisplay(oh.popoverTitle, 3);
		} catch (TimeoutException e) {
			throw new Exception("Popover is not opened");
		}

		com.checkElementPresence("xpath", oh.popoverPayment, true, "Payment Status in popover");
		com.checkElementPresence("xpath", oh.popoverFulfillment, true, "Fulfillment Status in popover");
		com.checkElementPresence("xpath", oh.popoverShipment, true, "Shipment Status in popover");
	}

	@Given("the order state popover is open")
	public void the_order_state_popover_is_open() throws Exception {
		com.click(oh.firstRowPopover);

		try {
			com.waitForElementToDisplay(oh.popoverTitle, 3);
		} catch (TimeoutException e) {
			throw new Exception("Popover is not opened");
		}
	}

	@When("I click outside the popover")
	public void i_click_outside_the_popover() throws InterruptedException {
		com.click(oh.filterBtn);
		Thread.sleep(500);
	}

	@Then("the popover should close automatically")
	public void the_popover_should_close_automatically() throws Exception {
		try {
			com.waitForElementToDisplay(oh.popoverTitle, 3);
			throw new Exception("Popover is not closed");
		} catch (TimeoutException e) {

		}
	}
}
