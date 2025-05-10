import java.util.ArrayList;

public class ProductManager {
    private static ArrayList<Product> products;

    public static ArrayList<Product> getProducts() {
        if (products != null) return products;
    
        products = new ArrayList<>();
        products.add(new Product("Clean Code", "Agile Software Craftsmanship", 45.0, 20, "Education"));
        products.add(new Product("Effective Java", "Java programming", 55.0, 15, "Education"));
        products.add(new Product("Design Patterns", "Object-Oriented Software", 60.0, 10, "Education"));
        products.add(new Product("The Pragmatic Programmer", "Your Journey to Mastery", 50.0, 25, "Education"));
        products.add(new Product("Head First Java", "A Brain-Friendly Guide", 40.0, 30, "Education"));
        products.add(new Product("Refactoring", "Design of Existing Code", 65.0, 12, "Education"));
        updateProductIds();
        return products;
    }
    
    public static boolean deleteProductById(int id) {
        if (products == null) getProducts();
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (removed) updateProductIds(); // Re-assign IDs after deletion
        return removed;
    }
    public static void addProduct(String name, String description, double price, int quantity, String category) {
        if (products == null) getProducts();
        products.add(new Product(name, description, price, quantity, category));
        updateProductIds(); // Re-assign IDs after addition
    }
    public static Product getProductById(int id) {
        if (products == null) getProducts();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
    public static boolean updateProduct(int id, String name, String description, double price, int quantity, String category) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setCategory(category);
            return true;
        }
        return false;
    }
    private static void updateProductIds() {
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setId(i + 1);
        }
    }
}