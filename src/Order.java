import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Order {
    private static int orderCounter = 1;
    private int orderId;
    private Customer customer;
    private ArrayList<CartItem> items;
    private double totalAmount;
    private Date orderDate;
    private String status;

    public Order(Customer customer, ArrayList<CartItem> items, double totalAmount) {
        this.customer = customer;
        this.items = new ArrayList<>(items); // Create a copy of the items
        
        // Sort items by quantity (from smallest to largest)
        Collections.sort(this.items, new Comparator<CartItem>() {
            @Override
            public int compare(CartItem item1, CartItem item2) {
                return Integer.compare(item1.getQuantity(), item2.getQuantity());
            }
        });
        
        this.totalAmount = totalAmount;
        this.orderId = orderCounter++;
        this.orderDate = new Date();
        this.status = "Pending";
    }

    public void displayOrder() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        System.out.println("\n Order Details ");
        System.out.println("Order ID: #" + orderId);
        System.out.println("Customer: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("Date: " + dateFormat.format(orderDate));
        System.out.println("Status: " + status);
        System.out.println("\nItems:");
        System.out.printf("%-20s | %-10s | %-10s | %-10s\n", "Product", "Price", "Quantity", "Subtotal");
        System.out.println("-------------------------------------------------------------------");
        
        for (CartItem item : items) {
            System.out.printf("%-20s | $%-9.2f | %-10d | $%-9.2f\n", 
                    item.getProduct().getName(), 
                    item.getProduct().getPrice(), 
                    item.getQuantity(), 
                    item.getProduct().getPrice() * item.getQuantity());
        }
        
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total: $%.2f\n", totalAmount);
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public ArrayList<CartItem> getItems() {
        return items;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
}