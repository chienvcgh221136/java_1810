import java.util.ArrayList;

public abstract class User {
    private String name;
    private String email;
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
   public abstract void displayProducts(ArrayList<Product> products); // Abstract method to be implemented by subclasses
    public String getEmail() {
        return email;
    }
    
}
