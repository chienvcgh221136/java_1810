import java.util.ArrayList;

public class Admin extends User {
    private String username;
    private String password;
    
    public Admin(String name, String email, String username, String password) {
        super(name, email);
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    @Override
    public void displayProducts(ArrayList<Product> products) {
        System.out.println("\n Available Products:");
        System.out.printf("%-5s | %-30s | %-30s | %-10s | %-10s | %-15s\n", 
                "ID", "Name", "Description", "Price", "Quantity", "Category");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("%-5d | %-30s | %-30s | $%-9.2f | %-10d | %-15s\n",
                    product.getId(), product.getName(), product.getDescription(),
                    product.getPrice(), product.getQuantity(), product.getCategory());
        }
    }
    
    public static boolean authenticate(String username, String password) {
        return username.equals("j97") && password.equals("123");
    }
}