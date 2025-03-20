Feature: Date Picker Functionality Testing

  Background:
    Given user is on the application page
    And the date picker component is loaded

  @component @datepicker @ui @smoke @e2e 
  Scenario: Verify UI elements of the date picker
    Then user should see the relative mode button
    And user should see the absolute mode button
    And user should see the calendar component
    And user should see the input field
    And all elements should match the Figma design specifications

  @component @datepicker @functional @e2e
  Scenario: Verify predefined relative ranges
    When user clicks on the date picker
     Then user should see the following predefined "<ranges>":
       | Last 1 hour  |
       | Last 7 days  |
       | Last 30 days |
       | Last 3 months|
       | Last 1 year  |

  @component @datepicker @functional @e2e
  Scenario Outline: Verify custom input for relative mode
    When user enters "<input>" in the relative mode input field
    Then the input should be parsed correctly
    And the date range should be applied successfully

    Examples:
      | input |
      | 2h    |
      | 10d   |
      | 4w    |
      | 3m    |

  @component @datepicker @functional @validation @e2e
  Scenario Outline: Verify input parsing for invalid values
    When user enters "<invalid_input>" in the relative mode input field
    Then the system should reject the input
    And an appropriate error message should be displayed

    Examples:
      | invalid_input |
      | 99x          |
      | -3d          |
      | abc          |
      | 1abc         |
      | 3m45d        |
      | ?7w          |

  @component @datepicker @functional @navigation @e2e
  Scenario: Verify transition between relative and absolute modes
    When user clicks on "Absolute Date" button
    Then the calendar view should open
    When user clicks on "Back" button
    Then the view should return to relative mode

  @component @datepicker @functional @e2e
  Scenario: Verify date selection in absolute mode
    When user clicks on "Absolute Date" button
    And user selects a start date from the calendar
    And user selects an end date from the calendar
    Then the selected dates should be displayed in the input field
    And the date range should be applied correctly

  @component @datepicker @functional @e2e
  Scenario: Verify Apply button functionality
    When user selects a date range
    And user clicks on "Apply" button
    Then the selection should be confirmed
    And the date picker should close
    And the selected range should be displayed in the main view

  @component @datepicker @accessibility @e2e
  Scenario: Verify keyboard accessibility
    When user navigates to the date picker using Tab key
    Then the date picker should receive focus
    When user presses Enter key
    Then the date picker should open
    When user uses arrow keys to navigate dates
    Then the focus should move accordingly
    When user presses Escape key
    Then the date picker should close

  @component @datepicker @ui @responsive @e2e
  Scenario: Verify responsiveness on different screen sizes
    When user views the application on desktop resolution
    Then the date picker should display properly
    When user views the application on tablet resolution
    Then the date picker should adjust to screen size
    When user views the application on mobile resolution
    Then the date picker should be fully functional

  @component @datepicker @functional @state @e2e
  Scenario: Verify default state on initial load
    When the date picker is first opened
    Then it should show default values or placeholders
    And no date range should be pre-selected

  @component @datepicker @functional @behavior @e2e
  Scenario: Verify auto-close behavior
    When user opens the date picker
    And user clicks outside the popover
    Then the date picker should close automatically

  @component @datepicker @functional @validation @e2e
  Scenario: Verify clicking Apply without selection
    When user opens the date picker
    And user clicks on "Apply" button without selecting dates
    Then the date picker should either throw error or not apply any changes

  @component @datepicker @functional @validation @e2e
  Scenario: Verify maximum and minimum date range
    When user opens the date picker in absolute mode
    And user attempts to select dates outside allowed limits
    Then the system should prevent selection
    And appropriate validation message should be shown

  @component @datepicker @performance @stress @e2e
  Scenario: Verify performance with frequent selections
    When user opens the date picker
    And user changes date values multiple times in quick succession
    Then the component should update smoothly
    And no lag or performance issues should occur

  @component @datepicker @integration @order-hub @e2e
  Scenario: Verify integration with Order Main Hub
    When user navigates to the Order Main Hub page
    Then the date picker should be visible
    When user selects a date range
    Then the order table should update accordingly
    And the filter functionality should work correctly 