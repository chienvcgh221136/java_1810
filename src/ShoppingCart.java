import java.util.ArrayList;

public class ShoppingCart {
    private Customer customer;
    private ArrayList<CartItem> items;
    
    public ShoppingCart(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }
    
    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        
        items.add(new CartItem(product, quantity));
    }
    
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }
    
    public void updateItemQuantity(int productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                if (newQuantity <= 0) {
                    removeItem(productId);
                } else {
                    item.setQuantity(newQuantity);
                }
                return;
            }
        }
    }
    
    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("\n Your cart is empty!");
            return;
        }
        
        System.out.println("\n Your Shopping Cart:");
        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s\n", 
                "ID", "Product", "Price", "Quantity", "Subtotal");
        System.out.println("--------------------------------------------------------------");
        
        double total = 0;
        for (CartItem item : items) {
            Product product = item.getProduct();
            double subtotal = product.getPrice() * item.getQuantity();
            total += subtotal;
            
            System.out.printf("%-5d | %-20s | $%-9.2f | %-10d | $%-9.2f\n",
                    product.getId(), product.getName(), product.getPrice(), 
                    item.getQuantity(), subtotal);
        }
        
        System.out.println("--------------------------------------------------------------");
        System.out.printf("Total: $%.2f\n", total);
    }
    
    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    public ArrayList<CartItem> getItems() {
        return items;
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public void clear() {
        items.clear();
    }
    
    public void checkout() {
        if (items.isEmpty()) {
            System.out.println(" Your cart is empty! Nothing to checkout.");
            return;
        }

        boolean allAvailable = true;
        for (CartItem item : items) {
            if (item.getQuantity() > item.getProduct().getQuantity()) {
                System.out.println(" Not enough " + item.getProduct().getName() + " in stock. Available: " + 
                        item.getProduct().getQuantity() + ", Requested: " + item.getQuantity());
                allAvailable = false;
            }
        }
        
        if (!allAvailable) {
            System.out.println(" Checkout failed due to insufficient stock.");
            return;
        }
        
        Order order = new Order(customer, items, getTotal());
        OrderManager.addOrder(order);
        System.out.println("\n Order placed successfully!");
        System.out.println("Your order has been sent for approval.");
        order.displayOrder();
        clear();
    }
}