import java.util.*;

// Represents a customer
class Customer {
    private String name;
    private String id;
    private BankAccount account;

    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
        this.account = new BankAccount(this);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public BankAccount getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + ", Balance: $" + account.getBalance();
    }
}

// Represents a bank account
class BankAccount {
    private Customer owner;
    private double balance;

    public BankAccount(Customer owner) {
        this.owner = owner;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("✅ Deposited $" + amount + " successfully.");
        } else {
            System.out.println("❌ Deposit amount must be greater than 0.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Withdrawal amount must be greater than 0.");
        } else if (amount > balance) {
            System.out.println("❌ Insufficient funds! Your balance is $" + balance);
        } else {
            balance -= amount;
            System.out.println("✅ Withdrew $" + amount + " successfully.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

// Main banking system
public class Main {
    private static Map<String, Customer> customers = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n🏦 Simple Banking System Menu");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View All Customers");
            System.out.println("6. Exit");
            System.out.print("👉 Enter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("❌ Invalid input. Please enter a number: ");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> checkBalance();
                case 5 -> viewAllCustomers();
                case 6 -> System.out.println("👋 Thank you for using the banking system!");
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private static void createAccount() {
        System.out.print("Enter Customer ID: ");
        String id = scanner.next();
        if (customers.containsKey(id)) {
            System.out.println("❌ Account already exists with this ID.");
            return;
        }
        System.out.print("Enter Customer Name: ");
        String name = scanner.next();
        Customer customer = new Customer(name, id);
        customers.put(id, customer);
        System.out.println("✅ Account created successfully for " + name);
    }

    private static void depositMoney() {
        Customer customer = getCustomer();
        if (customer != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            customer.getAccount().deposit(amount);
        }
    }

    private static void withdrawMoney() {
        Customer customer = getCustomer();
        if (customer != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            customer.getAccount().withdraw(amount);
        }
    }

    private static void checkBalance() {
        Customer customer = getCustomer();
        if (customer != null) {
            System.out.println("💰 Balance: $" + customer.getAccount().getBalance());
        }
    }

    private static void viewAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("❌ No customers found.");
        } else {
            System.out.println("\n📋 List of Customers:");
            for (Customer c : customers.values()) {
                System.out.println(c);
            }
        }
    }

    private static Customer getCustomer() {
        System.out.print("Enter Customer ID: ");
        String id = scanner.next();
        Customer customer = customers.get(id);
        if (customer == null) {
            System.out.println("❌ No customer found with ID " + id);
        }
        return customer;
    }
}
