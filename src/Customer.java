import java.util.ArrayList;

public class Customer extends User {
    private String username;
    private String password;
    private ShoppingCart cart;
    
    public Customer(String name, String email, String username, String password) {
        super(name, email);
        this.username = username;
        this.password = password;
        this.cart = new ShoppingCart(this);
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public ShoppingCart getCart() {
        return cart;
    }

    @Override
    public void displayProducts(ArrayList<Product> products) {
        System.out.println("\n Available Products:");
        System.out.printf("%-5s | %-30s | %-30s | %-10s | %-10s | %-15s\n", 
                "ID", "Name", "Description", "Price", "Quantity", "Category");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("%-5d | %-30s | %-30s | $%-9.2f | %-10d | %-15s\n",
                    product.getId(), product.getName(), product.getDescription(),
                    product.getPrice(), product.getQuantity(), product.getCategory());
        }
    }

    public ArrayList<Product> filterByCategory(ArrayList<Product> products, String category) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
    
    // Add a product to the shopping cart
    public void addToCart(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            cart.addItem(product, quantity);
            System.out.println("Added " + quantity + " x " + product.getName() + " to your cart");
        } else {
            System.out.println("Sorry, not enough quantity available. Available: " + product.getQuantity());
        }
    }

    public void viewCart() {
        cart.displayCart();
    }    
    
    // Checkout process
    public void checkout() {
        cart.checkout();
    }
    
    // New method to view order history
    public void viewOrderHistory() {
        ArrayList<Order> customerOrders = OrderManager.getOrdersByCustomer(this);
        
        if (customerOrders.isEmpty()) {
            System.out.println("\nYou have no order history.");
            return;
        }
        
        System.out.println("\n=== Your Order History ===");
        System.out.printf("%-8s | %-12s | %-15s | %-10s\n", 
                "Order ID", "Date", "Total Amount", "Status");
        System.out.println("--------------------------------------------------");
        
        for (Order order : customerOrders) {
            System.out.printf("%-8d | %-12s | $%-14.2f | %-10s\n", 
                    order.getOrderId(), 
                    new java.text.SimpleDateFormat("dd-MM-yyyy").format(order.getOrderDate()),
                    order.getTotalAmount(),
                    order.getStatus());
        }
        
        // Ask if user wants to see order details for a specific order
        System.out.print("\nEnter Order ID to see details (or 0 to return): ");
        try {
            java.util.Scanner sc = new java.util.Scanner(System.in);
            int orderId = Integer.parseInt(sc.nextLine().trim());
            
            if (orderId > 0) {
                Order selectedOrder = OrderManager.getOrderById(orderId);
                
                if (selectedOrder != null && selectedOrder.getCustomer().getUsername().equals(this.username)) {
                    selectedOrder.displayOrder();
                } else {
                    System.out.println("Order not found or you don't have access to this order.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu.");
        }
    }
}