import java.util.Scanner;
import java.util.ArrayList;

public class App {
    private static final int EXIT_OPTION = 0;

    public static void main(String[] args) throws Exception {
        User user = login();
        if (user == null) {
            System.out.println("Invalid username or password. Please try again.");
            return;
        } else if (user instanceof Admin) {
            // adminMenu(user);
        } else if (user instanceof Customer) {
            customerMenu(user);
        }
    }

    public static User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your role (admin/customer): ");
        String role = scanner.nextLine();

        // Temporary hardcoded user
        return new Customer("Harry", "quandh13@fe.edu.vn");
    }

    public static void customerMenu(User user) {
        System.out.println("Welcome " + user.getName() + " to the customer menu!");
        int choice;
        do {
            choice = getUserChoice(user);
        } while (choice != EXIT_OPTION);
    }

    public static int getUserChoice(User user) {
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("\t1. View Product Details");
        System.out.println("\t2. Search Products by Name");
        System.out.println("\t3. Sort Products by Price");
        System.out.println("\t4. Filter Products by Category");
        System.out.printf("%d. Exit\n", EXIT_OPTION);
        System.out.print("Please select an option: ");
        choice = sc.nextInt();

        ArrayList<Product> products = ProductManager.getProducts(); // giả định bạn đã có ProductManager

        switch (choice) {
            case 1:
                System.out.println("Viewing products...");
                user.displayProducts(products);
                subMenu(user, products);
                break;
            case 2:
                System.out.println("Searching products...");
                break;
            case 3:
                System.out.println("Sorting products...");
                break;
            case 4:
                System.out.println("Filtering products...");
                break;
            case EXIT_OPTION:
                System.out.println("Exiting customer menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return choice;
    }

    private static void subMenu(User user, ArrayList<Product> products) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Submenu:");
            System.out.println("1. View Product Details");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Sort Products by Price");
            System.out.println("4. Filter Products by Category");
            System.out.printf("%d. Exit\n", EXIT_OPTION);
            System.out.print("Please select an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Option 1 selected. Viewing product details...");
                    break;
                case 2:
                    System.out.println("Option 2 selected. Searching products by name...");
                    break;
                case 3:
                    System.out.println("Option 3 selected. Sorting products by price...");
                    break;
                case 4:

                    System.out.println("Type your category: ");
                    sc.next();
                    String category = sc.nextLine();
                    ArrayList<Product> found = user.filterByCategory(products, category);
                    user.displayProducts(found);
                    break;
                case EXIT_OPTION:
                    System.out.println("Back to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != EXIT_OPTION);
    }
    public abstract void displayProducts(ArrayList<Product> products);

    public ArrayList<Product> filterByCategory(ArrayList<Product> products, String category) {
        // return products.stream()
        //         .filter(product -> product.getCategory().equalsIgnoreCase(category))
        //         .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}
