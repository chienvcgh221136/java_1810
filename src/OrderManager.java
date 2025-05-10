import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class OrderManager {
    private static Queue<Order> orderQueue = new LinkedList<>();
    private static ArrayList<Order> processedOrders = new ArrayList<>();
    private static ArrayList<Order> allOrders = new ArrayList<>(); // Store all orders (both pending and processed)
    
    // Add order to the queue
    public static void addOrder(Order order) {
        orderQueue.add(order);
        allOrders.add(order); // Also add to the global orders list
    }
    
    // Get the next pending order from the queue
    public static Order getNextPendingOrder() {
        return orderQueue.peek();
    }
    
    // Process the current order (approve or reject)
    public static Order processCurrentOrder(String newStatus) {
        if(orderQueue.isEmpty()) {
            return null;
        }
        
        Order order = orderQueue.poll();
        order.setStatus(newStatus);
        processedOrders.add(order);
        return order;
    }
    
    // Get all processed orders
    public static ArrayList<Order> getProcessedOrders() {
        return processedOrders;
    }
    
    // Get number of pending orders
    public static int getPendingOrderCount() {
        return orderQueue.size();
    }
    
    // Get all pending orders (for display purposes)
    public static ArrayList<Order> getAllPendingOrders() {
        return new ArrayList<>(orderQueue);
    }
    
    // Get orders by specific customer
    public static ArrayList<Order> getOrdersByCustomer(Customer customer) {
        ArrayList<Order> customerOrders = new ArrayList<>();
        
        // Check both pending and processed orders
        for (Order order : allOrders) {
            if (order.getCustomer().getUsername().equals(customer.getUsername())) {
                customerOrders.add(order);
            }
        }
        
        return customerOrders;
    }
    
    // Get order by ID
    public static Order getOrderById(int orderId) {
        for (Order order : allOrders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
}