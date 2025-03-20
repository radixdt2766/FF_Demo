Feature: UI Testing for Task Management Application

  Background: 
    Given user is on the task management application

  @ui @smoke
  Scenario: Verify all UI elements are present on the main page
    Then user should see the "Add Task" button
    And user should see the task list container
    And user should see the task filter options
    And user should see the search task input field

  @ui @layout
  Scenario: Verify responsive design on different screen sizes
    When user views the application on desktop resolution
    Then all elements should be properly aligned
    When user views the application on tablet resolution
    Then all elements should adjust according to screen size
    When user views the application on mobile resolution
    Then all elements should stack vertically

  @ui @style
  Scenario: Verify styling and theme consistency
    Then all buttons should have consistent styling
    And all text should use the correct font family
    And color scheme should match the design specifications
    And spacing between elements should be consistent

  @ui @accessibility
  Scenario: Verify accessibility features
    Then all images should have alt text
    And all form fields should have proper labels
    And color contrast should meet WCAG standards
    And keyboard navigation should work properly 