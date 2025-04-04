import java.io.*; // for all input output operations 
import java.text.ParseException; // for parsing date strings
import java.text.SimpleDateFormat; // for proper format of the dates
import java.time.LocalDate; // for handling current date automatically
import java.util.*; // for general data structures like ArrayList, for Scanner class
import java.util.stream.Collectors; // for Filtering

// Rather than saying expense tracker we call it a finance tracker 

public class Main {

    // text formatting for colored output for the terminal

    // \u001B is the escape sequence 
    // [0m is the reset code 
    // and rest are color codes 
    // 31 is for red, 32 is for green, 33 for yellow 

    public static final String RESET = "\u001B[0m"; // sets the color
    // in terminal to default
    public static final String RED = "\u001B[31m"; 
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    // this gets converted to bytecode by compiler
    // and as a string literal it goes to the JVM
    // and it is the responsible of the terminal to identify the color 

    // Comparable : We implement this interface because in future we want to compare two expense objects 

    // Serializable : Indicates that objects of this class can be converted to byte stream so that we can save it to a file

    static class Expense implements Comparable<Expense>, Serializable {
        private String description;
        private double amount;
        private String date;
        private String category;

        public Expense(String description, double amount, String category) {
            this.description = description;
            this.amount = amount;
            this.date = LocalDate.now().toString();
            this.category = category;
        }

        // LocalDate is a class from java.time package which tells us a date and LocalDate.now() tells us today's date

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }

        public String getCategory() {
            return category;
        }

        // CompareTo is a method of Comparable interface used to define the natural ordering of objects of a class 

        // It returns 0 if both objects are equal 
        // It returns 1 if this object is greater 
        // We have overriden the compareTo method here 

        @Override
        public int compareTo(Expense other) {
            return this.date.compareTo(other.date);
        }

        // Responsible for converting an Expense object to string format so that we can write it to a file
        public String toTextFormat() {
            return "description : " + description + "      |      " + "amount : " + amount + "      |      " + "date : " + date + "      |      " + "category : " + category;
        }

        // Responsible for converting string in file to an expense object
        public static Expense fromTextFormat(String textLine) {
            String[] parts = textLine.split("\\|");
            // we split the line based on pipe character and create an array of parts 
            // as the element at first index will be our value we trim it out 

            String description = parts[0].split(":")[1].trim();
            double amount = Double.parseDouble(parts[1].split(":")[1].trim());
            @SuppressWarnings("unused")
            String date = parts[2].split(":")[1].trim();
            String category = parts[3].split(":")[1].trim();
        
            return new Expense(description, amount, category);
        }        

        public void displayDetails(){
            System.out.println("Description : " + this.description);
            System.out.println("Amount : "+ this.amount);
            System.out.println("Date : "+ this.date);
            System.out.println("Category : "+ this.category);
        }
    }

    static class ExpenseTracker {
        private ArrayList<Expense> expenses; // We have created an arrayList because it is dynamic in nature and we can store more Expenses
        private double savingsTarget;   // To help the user remain on track with his savings 

        public ExpenseTracker() {
            expenses = new ArrayList<>();
            savingsTarget = 0.0;
        }

        // This method creates an Expense object and adds it to the List of our expenses 
        public void addExpense(String description, double amount, String category) { 
            Expense expense = new Expense(description, amount, category);
            expenses.add(expense);
            System.out.println("Expense added successfully!");
        }

        // This method is used to maintain the readablility of the data by constructing a table form

        public void printTableHeader() {
            System.out.println("|---------------------|------------|--------------|-----------------|");
            System.out.println("| Description         | Amount     |    Date      | Category        |");
            System.out.println("|---------------------|------------|--------------|-----------------|");
        }

        // The next implementation of our features is view expenses 
        // If the list of our Expenses is Empty it would not print anything 
        // Else it provides proper presentation, the - represents that we are left aligning the details 
        // Like for description, we assign 19 characters if there are less than 19 characters rest have an empty space 

        // so we iterate over our ArrayList using the forEach loop

        public void viewAllExpenses() {
            if (expenses.isEmpty()) {
                System.out.println("No expenses recorded yet.");
            } else {
                printTableHeader();
                expenses.forEach(expense -> {
                    System.out.printf("| %-19s | $%-10.1f | %-12s | %-15s |\n", expense.getDescription(), expense.getAmount(), expense.getDate(), expense.getCategory());
                });
                System.out.println("|---------------------|------------|--------------|-----------------|");
            }
        }

        // We sort the expenses by Date with the help of Collections class from java.Util
        // Collections.sort() sorts objects according to the natural method provided by the Comparable class 
        // as we have defined our compareTo function we sort the expenses according to date 
        // Collection.sort() relies on the compareTo function

        public void sortExpensesByDate() {
            Collections.sort(expenses);
            System.out.println("Expenses sorted by date.");
        }

        // Comparator interface allows us to define a custom comparison between 2 objects 
        // We call the method comparingDouble to compare the amount 
        // We are applying the concept of method overloading here kyuki comparingInt, comparingFloat bhi hota hai 

        public void sortExpensesByAmount() {
            expenses.sort(Comparator.comparingDouble(Expense::getAmount));
            System.out.println("Expenses sorted by amount.");
        }

        // Simple Date Format is a class used to parse dates, and we give it the pattern we want to set
        public void filterExpensesByDateRange(String startDate, String endDate) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Now sdf the object of Simple Date Format can parse over the string character by character and return it into a Date format

            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            List<Expense> filtered = expenses.stream() // converts expenses into stream of bytes so we can parse over it
            // .filter filters out expenses from list which aren't in the required time period 
            // While doing so, it may happen that the Date format is incorrect so to handle that we have the Parse Exception
            // this filter method returns true or false 

                    .filter(e -> {
                        try {
                            Date expenseDate = sdf.parse(e.getDate());
                            return !expenseDate.before(start) && !expenseDate.after(end);
                        } catch (ParseException ex) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList()); 
                    

            if (filtered.isEmpty()) {
                System.out.println("No expenses found between " + startDate + " and " + endDate);
            } else {
                printTableHeader();
                filtered.forEach(expense -> {
                    System.out.printf("| %-19s | $%-10.2f | %-12s | %-15s |\n", expense.getDescription(), expense.getAmount(), expense.getDate(), expense.getCategory());
                });
                System.out.println("|---------------------|------------|--------------|-----------------|");
            }
        }

        public double calculateTotalExpenses() {
            return expenses.stream().mapToDouble(Expense::getAmount).sum();

            // each expense now mapped to the double value that is the amount in the expense 
            // and finally we get the sum for total expenditure
        }

        public void setSavingsTarget(double target) {
            this.savingsTarget = target;
            System.out.println("Savings target set to: $" + target);
        }

        public void viewSavingsProgress() {
            double totalExpenses = calculateTotalExpenses();
            double savings = savingsTarget - totalExpenses;
            if (savings >= 0) {
                System.out.println(CYAN + "You are on track! Savings: $" + savings);
            } else {
                System.out.println(CYAN + "You have exceeded your target by: $" + (-savings));
            }
        }
        
        // In order to save list of expenses to a file to load them later or 
        // to use for future, we have created a function called as saveExpensesToFile()

        // Which incorpoates concepts of error handling and file handling in java 
        // We made use of the buffered writer class to write to the file because it is more efficient 
        public void saveExpensesToFile(String filename) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (Expense expense : expenses) {
                    writer.write(expense.toTextFormat());
                    writer.newLine();
                }
                System.out.println("Expenses saved to file: " + filename);
            } catch (IOException e) {
                System.out.println("Error saving expenses to file: " + e.getMessage());
            }
        }
        
        public void loadExpensesFromFile(String filename) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                expenses.clear(); // ensures that no old data is present 
                while ((line = reader.readLine()) != null) {
                    Expense expense = Expense.fromTextFormat(line);
                    expense.displayDetails();
                    expenses.add(expense);
                }
                System.out.println("Expenses loaded from file: " + filename);
            } catch (IOException e) {
                System.out.println("Error loading expenses from file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();
        int choice;

        do {
            System.out.println(GREEN + "\nExpense Tracker Menu:");
            System.out.println(RESET + "1. Add Expense");
            System.out.println(RESET + "2. View All Expenses");
            System.out.println(RESET + "3. Sort Expenses");
            System.out.println(RESET + "4. Filter Expenses by Date Range");
            System.out.println(RESET + "5. Set Savings Target");
            System.out.println(RESET + "6. View Savings Progress");
            System.out.println(RESET + "7. Save Expenses to File");
            System.out.println(RESET + "8. Load Expenses from File");
            System.out.println(RESET + "9. Exit");
            System.out.print(GREEN + "Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print(BLUE + "Enter expense description: ");
                    String description = scanner.nextLine();
                    System.out.print(BLUE + "Enter expense amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print(BLUE + "Enter expense category: ");
                    String category = scanner.nextLine();
                    tracker.addExpense(description, amount, category);
                }
                case 2 -> tracker.viewAllExpenses();
                case 3 -> {
                    System.out.println(YELLOW + "Sort expenses By : ");
                    System.out.println(YELLOW + "1. Date");
                    System.out.println(YELLOW + "2. Amount");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice1) {
                        case 1 -> tracker.sortExpensesByDate();
                        case 2 -> tracker.sortExpensesByAmount();
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                }
                case 4 -> {
                    System.out.print(YELLOW + "Enter start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print(YELLOW + "Enter end date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    try {
                        tracker.filterExpensesByDateRange(startDate, endDate);
                    } catch (ParseException e) {
                        System.out.println(RED + "Invalid date format. Please use YYYY-MM-DD.");
                    }
                }
                case 5 -> {
                    System.out.print(CYAN + "Enter savings target: ");
                    double target = scanner.nextDouble();
                    tracker.setSavingsTarget(target);
                }
                case 6 -> tracker.viewSavingsProgress();
                case 7 -> {
                    System.out.print(GREEN + "Enter filename to save expenses: ");
                    String saveFilename = scanner.nextLine();
                    tracker.saveExpensesToFile(saveFilename);
                }
                case 8 -> {
                    System.out.print(GREEN + "Enter filename to load expenses: ");
                    String loadFilename = scanner.nextLine();
                    tracker.loadExpensesFromFile(loadFilename);
                }
                default -> System.out.println(RED + "Invalid choice. Please try again.");
            }
        } while (choice != 9);

        scanner.close();
    }
}
