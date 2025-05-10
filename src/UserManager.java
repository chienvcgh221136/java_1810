import java.util.ArrayList;

public class UserManager {
    private static ArrayList<Customer> customers;
    
    public static ArrayList<Customer> getCustomers() {
        if (customers != null) return customers;
        
        customers = new ArrayList<>();
        // Add some default customers
        customers.add(new Customer("Harry", "harry@gmail.com", "harry", "123"));
        customers.add(new Customer("John", "john@gmail.com", "john", "456"));
        customers.add(new Customer("Emma", "emma@gmail.com", "emma", "789"));
        
        return customers;
    }
    
    public static Customer authenticateCustomer(String username, String password) {
        ArrayList<Customer> customerList = getCustomers();
        
        for (Customer customer : customerList) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        
        return null;
    }
    
    public static Admin getAdmin() {
        return new Admin("Administrator", "admin@gmail.com", "j97", "123");
    }
}