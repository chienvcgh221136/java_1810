public class Product {
    private int id;
    private String name;
    private String description;
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantityAvailable;
    }

    public String getCategory() {
        return category;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    private double price;
    private int quantityAvailable;
    private String category;

    private static int idCounter = 0; // Static counter to generate unique IDs

    public Product(int id, String name, double price, int quantityAvailable, String category) {
        this.id = ++idCounter;
        this.name = name;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.category = category;
    }

    
}
