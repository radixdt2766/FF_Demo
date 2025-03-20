# Task Management System Test Automation

This project contains automated tests for a Task Management System using Cucumber, Selenium, and Java.

## Project Structure

```
src/
├── main/
│   └── java/
│       └── globalSetup/
│           ├── Base.java
│           ├── CommonElements.java
│           ├── DriverUtil.java
│           └── ExcelUtility.java
└── test/
    ├── java/
    │   └── stepdefinitions/
    │       ├── TaskManagementTestActions.java
    │       ├── DatePickerTestActions.java
    │       └── ApplicationHooks.java
    └── resources/
        └── AppFeatures/
            ├── TaskManagementTest.feature
            └── DatePickerTest.feature
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Chrome browser (for Selenium tests)

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
```

2. Navigate to the project directory:
```bash
cd FF_Project
```

3. Install dependencies:
```bash
mvn clean install
```

## Running Tests

To run all tests:
```bash
mvn test
```

To run specific feature files:
```bash
mvn test -Dcucumber.features="src/test/resources/AppFeatures/TaskManagementTest.feature"
```

## Test Categories

The tests are organized into the following categories:

1. Task Management Tests
   - CRUD operations
   - Search functionality
   - Filtering
   - Priority management
   - Category management
   - Dependencies
   - Bulk operations
   - Sorting
   - Notifications

2. Date Picker Tests
   - UI elements
   - Relative/absolute mode
   - Predefined ranges
   - Custom input
   - Validation
   - Accessibility
   - Responsive design

## Tags

The tests use the following tags for organization:

- @component - Component-specific tests
- @task-management - Task management functionality
- @datepicker - Date picker functionality
- @functional - Functional tests
- @ui - UI tests
- @e2e - End-to-end tests
- @crud - Create, Read, Update, Delete operations
- @search - Search functionality
- @filter - Filter functionality
- @priority - Priority management
- @category - Category management
- @dependencies - Task dependencies
- @validation - Input validation
- @bulk - Bulk operations
- @sort - Sorting functionality
- @notification - Notification system
- @accessibility - Accessibility tests
- @responsive - Responsive design tests

## Contributing

1. Create a new branch for your feature
2. Make your changes
3. Run the tests
4. Submit a pull request
