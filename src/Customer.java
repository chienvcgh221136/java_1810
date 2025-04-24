import java.util.ArrayList;
public class Customer extends User{
    public Customer(String name, String email) {
        super(name, email);
    }
    @Override
    public void displayProducts(ArrayList<Product> products) {
        System.out.println("Available Products:");
        System.out.printf("%-10s | %-20s | %-20s | %-10s | %-10s\n", "ID", "Name", "Description", "Price", "Quantity");
        System.out.println("-------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("%-10d | %-20s | %-20s | %-10.2f | %-10d\n", 
            product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
        }
    }
    
}
