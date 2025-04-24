import java.util.ArrayList;

public class ProductManager {
    //Data structure to hold products: Array/ArrayList/LinkedList/...
    private static ArrayList<Product> products;

    public static ArrayList<Product> getProducts() {
        /*
         * if)product_file is exists) {
         *  product = read product_fileand load to products
         * return products;
         * }else {
         * products = new ArrayList<Product>();
         * products.add(new Product(1, "Product 1", 10.0, 100, "Category 1"));
         * products.add(new Product(2, "Product 2", 20.0, 50, "Category 2"));
         * //...
         * Save to product_file
         * return products;
         * }
         */
        // For simplicity, we are just creating a new list of products here.
    
    if(products != null) return products;
    else{
        // Initialize the list of products if it is null
        products = new ArrayList<Product>();
        products.add(new Product(1, "Product 1", 10.0, 100, "Category 1"));
        products.add(new Product(2, "Product 2", 20.0, 50, "Category 2"));
        // Add more products as needed
        return products;
    }

}
}
