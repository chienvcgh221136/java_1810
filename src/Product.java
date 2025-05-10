public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantityAvailable;
    private String category;

    public Product(String name, String description, double price, int quantityAvailable, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantityAvailable; }
    public String getCategory() { return category; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantityAvailable = quantity;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    // Reduce available quantity
    public boolean reduceQuantity(int amount) {
        if (quantityAvailable >= amount) {
            quantityAvailable -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Description: %s, Price: %.2f, Quantity: %d, Category: %s",
                id, name, description, price, quantityAvailable, category);
    }
}