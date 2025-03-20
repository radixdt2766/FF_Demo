Feature: Regression Testing for Task Management Application

  Background:
    Given user is logged into the task management application
    And test data is properly set up

  @regression @performance
  Scenario: Verify performance with large number of tasks
    Given user has 100 tasks in the system
    When user loads the task list
    Then page should load within 3 seconds
    And scrolling should be smooth
    And task filtering should work efficiently

  @regression @data-persistence
  Scenario: Verify data persistence across sessions
    Given user has created multiple tasks
    When user logs out and logs back in
    Then all previously created tasks should be visible
    And task status should remain unchanged
    And task details should be preserved

  @regression @error-handling
  Scenario Outline: Verify error handling for invalid inputs
    When user attempts to create task with "<invalid_input>"
    Then appropriate error message should be displayed
    And system should prevent task creation

    Examples:
      | invalid_input          |
      | empty_title           |
      | extremely_long_title  |
      | special_characters    |
      | invalid_date_format   |

  @regression @concurrent-access
  Scenario: Verify concurrent task modifications
    Given two users are accessing the same task list
    When user1 modifies a task
    And user2 attempts to modify the same task simultaneously
    Then appropriate conflict resolution message should be shown
    And data integrity should be maintained

  @regression @integration
  Scenario: Verify integration with other system components
    When user creates a task with notification settings
    Then notification system should be triggered
    And task should be properly synchronized with calendar
    And task statistics should be updated
    
  @regression @backup-restore
  Scenario: Verify backup and restore functionality
    Given user has existing tasks
    When system performs automatic backup
    And system needs to restore from backup
    Then all tasks should be restored correctly
    And task relationships should be maintained 