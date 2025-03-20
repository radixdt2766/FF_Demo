Feature: Order Main Hub Functionality Testing

  Background: 
    Given user is on the application page

  Scenario: Verify sorting on different columns
    Given I verify all the columns are present
    When I sort by Order ID and check the results
		Then I sort by Customer and check the results

  Scenario: Verify order state popover
    When I click on an order’s state field
    Then the popover should display payment, fulfillment, and shipment statuses

  Scenario: Verify clicking outside the order state popover
    Given the order state popover is open
    When I click outside the popover
    Then the popover should close automatically
