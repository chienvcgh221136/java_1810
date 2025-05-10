import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

public class App {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Shop Management System");
        
        while (true) {
            User user = loginMenu();
            if (user == null) {
                continue; // Invalid login, restart
            }
            
            boolean restart;
            if (user instanceof Customer) {
                restart = customerMenu((Customer) user);
            } else if (user instanceof Admin) {
                restart = adminMenu((Admin) user);
            } else {
                System.out.println("Invalid user type.");
                break;
            }

            if (!restart) break; // Stop program if user chose "no"
        }

        System.out.println("Program ended. Thank you for using our system!");
    }

    public static User loginMenu() {
        while (true) {
            System.out.println("\n === User Type Selection ===");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("0. Exit Program");
            System.out.print("Choose user type: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            
            if (choice == 0) {
                System.out.println("Exiting program...");
                System.exit(0);
            } else if (choice == 1) {
                return customerLogin();
            } else if (choice == 2) {
                return adminLogin();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static Customer customerLogin() {
        System.out.println("\n=== Customer Login ===");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();
        
        Customer customer = UserManager.authenticateCustomer(username, password);
        if (customer != null) {
            System.out.println("Login successful! Welcome " + customer.getName() + "!");
            return customer;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }
    
    private static Admin adminLogin() {
        System.out.println("\n === Admin Login ===");
        System.out.print("Enter username : ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password : ");
        String password = sc.nextLine().trim();
        
        if (Admin.authenticate(username, password)) {
            Admin admin = UserManager.getAdmin();
            System.out.println(" Admin login successful! Welcome " + admin.getName() + "!");
            return admin;
        } else {
            System.out.println("Invalid admin credentials. Please try again.");
            return null;
        }
    }

    // CUSTOMER MENU
    public static boolean customerMenu(Customer customer) {
        int choice;
        do {
            System.out.println("\n=== Customer Menu (" + customer.getName() + ") ===");
            System.out.println("1. View All Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Sort Products by Price");
            System.out.println("4. Filter Products by Category");
            System.out.println("5. Add Product to Cart");
            System.out.println("6. View Shopping Cart");
            System.out.println("7. Update Cart Item Quantity");
            System.out.println("8. Remove Item from Cart");
            System.out.println("9. Checkout");
            System.out.println("10. View Order History"); // New option
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            ArrayList<Product> products = ProductManager.getProducts();

            switch (choice) {
                case 1:
                    customer.displayProducts(products);
                    break;
                case 2:
                    System.out.print("Enter product name to search: ");
                    String name = sc.nextLine().trim();
                    ArrayList<Product> searchResults = new ArrayList<>();
                    
                    // Search by name
                    for (Product product : products) {
                        if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                            searchResults.add(product);
                        }
                    }
                    
                    if (searchResults.isEmpty()) {
                        System.out.println("No products found matching: " + name);
                    } else {
                        customer.displayProducts(searchResults);
                    }
                    break;
                case 3:
                    // Sort by price
                    ArrayList<Product> sortedProducts = new ArrayList<>(products);
                    sortedProducts.sort(Comparator.comparingDouble(Product::getPrice));
                    customer.displayProducts(sortedProducts);
                    break;
                case 4:
                    System.out.print("Enter category to filter: ");
                    String cat = sc.nextLine().trim();
                    ArrayList<Product> filtered = customer.filterByCategory(products, cat);
                    if (filtered.isEmpty()) {
                        System.out.println("No products found in category: " + cat);
                    } else {
                        customer.displayProducts(filtered);
                    }
                    break;
                case 5:
                    addToCart(customer, products);
                    break;
                case 6:
                    customer.viewCart();
                    break;
                case 7:
                    updateCartItem(customer);
                    break;
                case 8:
                    removeFromCart(customer);
                    break;
                case 9:
                    customer.checkout();
                    break;
                case 10:
                    customer.viewOrderHistory(); // New case for viewing order history
                    break;
                case 0:
                    System.out.println("Logging out...");
                    System.out.print("Do you want to restart the program? (yes/no): ");
                    String again = sc.nextLine().trim().toLowerCase();
                    return again.equals("yes");
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }
    
    private static void addToCart(Customer customer, ArrayList<Product> products) {
        customer.displayProducts(products);
        System.out.print("Enter product ID to add to cart: ");
        try {
            int productId = Integer.parseInt(sc.nextLine().trim());
            Product product = ProductManager.getProductById(productId);
            
            if (product != null) {
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(sc.nextLine().trim());
                
                if (quantity > 0) {
                    customer.addToCart(product, quantity);
                } else {
                    System.out.println("Quantity must be greater than zero.");
                }
            } else {
                System.out.println("Product not found with ID: " + productId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers.");
        }
    }
    
    private static void updateCartItem(Customer customer) {
        customer.viewCart();
        if (customer.getCart().isEmpty()) {
            return;
        }
        
        System.out.print("Enter product ID to update quantity: ");
        try {
            int productId = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter new quantity (0 to remove): ");
            int newQuantity = Integer.parseInt(sc.nextLine().trim());
            
            customer.getCart().updateItemQuantity(productId, newQuantity);
            System.out.println("Cart updated successfully!");
            customer.viewCart();
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers.");
        }
    }
    
    private static void removeFromCart(Customer customer) {
        customer.viewCart();
        if (customer.getCart().isEmpty()) {
            return;
        }
        
        System.out.print("Enter product ID to remove from cart: ");
        try {
            int productId = Integer.parseInt(sc.nextLine().trim());
            customer.getCart().removeItem(productId);
            System.out.println("Item removed from cart!");
            customer.viewCart();
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid product ID.");
        }
    }

    // ADMIN MENU
    public static boolean adminMenu(Admin admin) {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. View Pending Orders (" + OrderManager.getPendingOrderCount() + ")");
            System.out.println("6. View Processed Orders");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    ArrayList<Product> products = ProductManager.getProducts();
                    admin.displayProducts(products);
                    break;
                case 2:
                    addNewProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    managePendingOrders();
                    break;
                case 6:
                    viewProcessedOrders();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    System.out.print("Do you want to restart the program? (yes/no): ");
                    String again = sc.nextLine().trim().toLowerCase();
                    return again.equals("yes");
                default:
                    System.out.println("Invalid choice.");
            }
        } while (true);
    }
    
    private static void addNewProduct() {
        try {
            System.out.println("\nAdd New Product:");
            System.out.print("Enter product name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter description: ");
            String desc = sc.nextLine().trim();
            System.out.print("Enter price: $");
            double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter category: ");
            String category = sc.nextLine().trim();

            ProductManager.addProduct(name, desc, price, qty, category);
            System.out.println("Product added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers for price and quantity.");
        }
    }
    
    private static void updateProduct() {
        ArrayList<Product> products = ProductManager.getProducts();
        Admin admin = UserManager.getAdmin();
        admin.displayProducts(products);
        
        try {
            System.out.print("\nEnter product ID to update: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            
            Product product = ProductManager.getProductById(id);
            if (product == null) {
                System.out.println("Product not found with ID: " + id);
                return;
            }
            
            // Display current product details
            System.out.println("\n Current Product Details:");
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: $" + product.getPrice());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Category: " + product.getCategory());
            
            // Get updated information
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Name [" + product.getName() + "]: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) name = product.getName();
            
            System.out.print("Description [" + product.getDescription() + "]: ");
            String desc = sc.nextLine().trim();
            if (desc.isEmpty()) desc = product.getDescription();
            
            System.out.print("Price [$" + product.getPrice() + "]: $");
            String priceStr = sc.nextLine().trim();
            double price = priceStr.isEmpty() ? product.getPrice() : Double.parseDouble(priceStr);
            
            System.out.print("Quantity [" + product.getQuantity() + "]: ");
            String qtyStr = sc.nextLine().trim();
            int qty = qtyStr.isEmpty() ? product.getQuantity() : Integer.parseInt(qtyStr);
            
            System.out.print("Category [" + product.getCategory() + "]: ");
            String category = sc.nextLine().trim();
            if (category.isEmpty()) category = product.getCategory();
            
            // Update the product
            boolean updated = ProductManager.updateProduct(id, name, desc, price, qty, category);
            if (updated) {
                System.out.println(" Product updated successfully!");
            } else {
                System.out.println("Failed to update product.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println(" Please enter valid numbers.");
        }
    }
    
    private static void deleteProduct() {
        ArrayList<Product> products = ProductManager.getProducts();
        Admin admin = UserManager.getAdmin();
        admin.displayProducts(products);
        
        try {
            System.out.print("\nEnter product ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            
            System.out.print("Are you sure you want to delete this product? (yes/no): ");
            String confirm = sc.nextLine().trim().toLowerCase();
            
            if (confirm.equals("yes")) {
                boolean deleted = ProductManager.deleteProductById(id);
                if (deleted) {
                    System.out.println(" Product deleted successfully!");
                } else {
                    System.out.println("Product not found with ID: " + id);
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Please enter a valid product ID.");
        }
    }
     // Method to handle pending orders (new)
     private static void managePendingOrders() {
        Order nextOrder = OrderManager.getNextPendingOrder();
        if (nextOrder == null) {
            System.out.println("\nNo pending orders.");
            return;
        }
        System.out.println("\n=== Next Pending Order ===");
        nextOrder.displayOrder();
        System.out.println("\nActions:");
        System.out.println("1. Approve Order");
        System.out.println("2. Reject Order");
        System.out.println("3. Skip (review later)");
        System.out.print("Choose an action: ");
        
        try {
            int action = Integer.parseInt(sc.nextLine().trim());
            switch(action) {
                case 1:
                    Order approvedOrder = OrderManager.processCurrentOrder("Approved");
                    if (approvedOrder != null) {
                        System.out.println("\nOrder #" + approvedOrder.getOrderId() + " has been approved!");
                        for (CartItem item : approvedOrder.getItems()) {
                            item.getProduct().reduceQuantity(item.getQuantity());
                        }
                    }
                    break;
                case 2:
                    Order rejectedOrder = OrderManager.processCurrentOrder("Rejected");
                    if (rejectedOrder != null) {
                        System.out.println("\nOrder #" + rejectedOrder.getOrderId() + " has been rejected!");
                    }
                    break;
                case 3:
                    System.out.println("\nOrder skipped for later review.");
                    break;
                default:
                    System.out.println("\nInvalid action selected.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    // Method to view processed orders (updated)
    private static void viewProcessedOrders() {
        ArrayList<Order> processedOrders = OrderManager.getProcessedOrders();
        
        if (processedOrders.isEmpty()) {
            System.out.println("\nNo processed orders yet.");
            return;
        }
        
        System.out.println("\n=== Processed Orders ===");
        System.out.printf("%-8s | %-12s | %-20s | %-15s | %-10s\n", 
                "Order ID", "Date", "Customer", "Total Amount", "Status");
        System.out.println("--------------------------------------------------------------------------");
        
        for (Order order : processedOrders) {
            System.out.printf("%-8d | %-12s | %-20s | $%-14.2f | %-10s\n", 
                    order.getOrderId(), 
                    new java.text.SimpleDateFormat("dd-MM-yyyy").format(order.getOrderDate()),
                    order.getCustomer().getName(),
                    order.getTotalAmount(),
                    order.getStatus());
        }
        
        // Ask if user wants to see order details for a specific order
        System.out.print("\nEnter Order ID to see details (or 0 to return): ");
        try {
            int orderId = Integer.parseInt(sc.nextLine().trim());
            
            if (orderId > 0) {
                Order selectedOrder = OrderManager.getOrderById(orderId);
                
                if (selectedOrder != null && selectedOrder.getStatus().equals("Approved") 
                        || selectedOrder.getStatus().equals("Rejected")) {
                    selectedOrder.displayOrder();
                } else {
                    System.out.println("Order not found or not yet processed.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu.");
        }
    
    }
}