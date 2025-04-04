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
        // Yaha humne compareTo method ko override kiya hai 

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