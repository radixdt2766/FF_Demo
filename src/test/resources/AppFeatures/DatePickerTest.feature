Feature: Date Picker Functionality

  Background: 
    Given user is on the application page

  Scenario: Verify UI elements of the date picker
    Given I navigate to the date picker
    Then I should see all elements including relative mode, absolute mode button, and input field

  Scenario: Verify predefined relative ranges
    Given I open the date picker
    Then I should see predefined options like "Last 24 hour", "Last 7 days" etc

  Scenario: Verify custom input for relative mode
    Given I open the date picker
    When I enter range "2h", "10d", "4w" and check input is parsed correctly and applied

  Scenario: Verify transition to absolute mode
    Given I open the date picker
    When I click on Absolute Date button
    Then the calendar view should open

  Scenario: Verify clicking "Back" button navigates to relative date picker
    Given I open the date picker
  	When I click on Absolute Date button
    Then click on back button and verify relative date picker is displayed
