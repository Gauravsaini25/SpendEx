# SpendEx - Finance Tracker

## Description
SpendEx is a simple Java-based Finance Tracker that allows users to manage their expenses efficiently. It provides functionalities to add, view, sort, filter, and save expenses to a file. Additionally, users can set savings targets and track their progress.

## Features
- Add Expenses: Users can input expenses with a description, amount, and category.
- View All Expenses: Display expenses in a tabular format.
- Sort Expenses: Sort expenses by date or amount.
- Filter Expenses: View expenses within a specific date range.
- Set Savings Target: Define a savings goal and track progress.
- View Savings Progress: Compare expenses with the savings goal.
- Save Expenses to File: Persist expense records to a file.
- Load Expenses from File: Retrieve saved expense records.

## Technologies Used
- Java (JDK 8+): Core programming language.
- Java I/O: Used for file handling.
- Collections & Streams: Used for sorting, filtering, and processing expenses efficiently.
- ANSI Escape Codes: Used for colored console output.

## Flowchart
Below is the flowchart representing the functionality of SpendEx:

![SpendEx Flowchart](file-NJ8NPL83ko7tg1LYZB6wZ1)

## How to Run
### Prerequisites
Ensure that you have Java installed on your system.

### Compilation
sh
javac Main.java


### Execution
sh
java Main


## Usage
1. Run the program and choose an option from the menu.
2. Add expenses by providing a description, amount, and category.
3. View, sort, and filter expenses as needed.
4. Set a savings goal and track your progress.
5. Save expenses to a file and reload them later.
6. Exit the program when done.

## Example

Finance Tracker Menu:
1. Add Expense
2. View All Expenses
3. Sort Expenses
4. Filter Expenses by Date Range
5. Set Savings Target
6. View Savings Progress
7. Save Expenses to File
8. Load Expenses from File
9. Exit
Enter your choice: 1

Enter expense description: Coffee
Enter expense amount: 5.50
Enter expense category: Food
Expense added successfully!


## File Format for Saving and Loading
Expenses are saved in the following text format:

description : Coffee      |      amount : 5.50      |      date : 2025-04-04      |      category : Food

Each expense is stored in a structured manner and can be reloaded into the program.

## Potential Enhancements
- Implement a GUI using JavaFX or Swing.
- Add expense categories with predefined options.
- Support exporting to CSV format.
- Implement authentication for multi-user support.

## Contributors
This project is developed by multiple contributors. Special thanks to everyone involved in building SpendEx!

### Contributors List:
- Gaurav Saini - MIS 112315066
- Bhoomi Gundecha - MIS 112315067
- Nishit Jain - MIS 112315075
- Jayesh Kawale - MIS 112315084
