Feature: Task Management Functionality Testing

  Background:
    Given user is logged into the task management application

  @component @task-management @functional @crud @e2e
  Scenario: Create a new task
    When user clicks on "Add Task" button
    And user enters task title "Test Task"
    And user enters task description "This is a test task"
    And user selects due date as tomorrow
    And user clicks on "Save" button
    Then the task "Test Task" should be created successfully
    And task should appear in the task list

  @component @task-management @functional @crud @e2e
  Scenario: Edit an existing task
    Given there is an existing task "Test Task"
    When user clicks on edit button for "Test Task"
    And user updates task title to "Updated Test Task"
    And user clicks on "Save" button
    Then the task should be updated successfully
    And task title should show "Updated Test Task"

  @component @task-management @functional @crud @e2e
  Scenario: Delete a task
    Given there is an existing task "Test Task"
    When user clicks on delete button for "Test Task"
    And user confirms deletion
    Then the task should be deleted successfully
    And task "Test Task" should not appear in the task list

  @component @task-management @functional @search @e2e
  Scenario Outline: Search for tasks
    Given user has multiple tasks in the list
    When user enters "<searchTerm>" in the search field
    Then only tasks containing "<searchTerm>" should be displayed

    Examples:
      | searchTerm |
      | Test      |
      | Important |
      | Meeting   |

  @component @task-management @functional @filter @e2e
  Scenario: Filter tasks by status
    Given user has tasks with different statuses
    When user selects filter option "Completed"
    Then only completed tasks should be displayed
    When user selects filter option "Pending"
    Then only pending tasks should be displayed
    When user selects filter option "All"
    Then all tasks should be displayed

  @component @task-management @functional @priority @e2e
  Scenario: Set and update task priority
    Given there is an existing task "Test Task"
    When user sets task priority to "High"
    Then task priority should be updated to "High"
    When user changes task priority to "Medium"
    Then task priority should be updated to "Medium"
    And task should be reordered in the list according to priority

  @component @task-management @functional @category @e2e
  Scenario: Assign and manage task categories
    Given there is an existing task "Test Task"
    When user assigns category "Development" to the task
    Then task should be tagged with "Development" category
    When user adds another category "Urgent"
    Then task should have both categories assigned
    When user removes category "Development"
    Then task should only have "Urgent" category

  @component @task-management @functional @dependencies @e2e
  Scenario: Set up task dependencies
    Given there are two existing tasks "Task A" and "Task B"
    When user sets "Task B" as dependent on "Task A"
    Then dependency should be created successfully
    When user attempts to mark "Task B" as complete
    Then system should prevent completion until "Task A" is done
    When user marks "Task A" as complete
    Then "Task B" should become available for completion

  @component @task-management @functional @validation @e2e
  Scenario Outline: Validate task creation with invalid inputs
    When user attempts to create task with "<invalid_input>"
    Then appropriate error message should be displayed
    And task should not be created

    Examples:
      | invalid_input          | error_message                    |
      | empty_title           | "Task title cannot be empty"      |
      | extremely_long_title  | "Task title exceeds maximum length"|
      | special_characters    | "Invalid characters in title"     |
      | invalid_date_format   | "Invalid date format"             |
      | past_due_date        | "Due date cannot be in the past"   |

  @component @task-management @functional @bulk @e2e
  Scenario: Perform bulk task operations
    Given there are multiple tasks in the list
    When user selects multiple tasks using checkboxes
    And user clicks on "Bulk Actions" button
    And user selects "Mark as Complete"
    Then all selected tasks should be marked as complete
    When user selects multiple tasks again
    And user clicks on "Bulk Actions" button
    And user selects "Delete"
    And user confirms deletion
    Then all selected tasks should be deleted

  @component @task-management @functional @sort @e2e
  Scenario: Sort tasks by different criteria
    Given there are multiple tasks with different properties
    When user clicks on "Due Date" column header
    Then tasks should be sorted by due date
    When user clicks on "Priority" column header
    Then tasks should be sorted by priority
    When user clicks on "Title" column header
    Then tasks should be sorted alphabetically by title
    When user clicks on the same column header again
    Then tasks should be sorted in reverse order

  @component @task-management @functional @notification @e2e
  Scenario: Configure and receive task notifications
    Given there is an existing task "Test Task"
    When user enables email notifications for the task
    And user sets reminder for 1 hour before due date
    Then notification settings should be saved
    When task is approaching due date
    Then user should receive email notification
    And user should see in-app notification 