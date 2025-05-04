import java.util.Scanner;

/**
 * Main class - Entry point for the Online Bookstore application
 * Contains the CustomerMenu functionality and the application startup code
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Starting Online Bookstore System ===");
        
        // Initialize the bookstore with capacity for orders and books
        OnlineBookstore bookstore = new OnlineBookstore(100, 50);
        
        // Add sample books to the catalog
        System.out.println("Loading book catalog...");
        populateBookstore(bookstore);
        
        // Start the customer menu
        System.out.println("Starting customer interface...");
        CustomerMenu customerMenu = new CustomerMenu(bookstore);
        customerMenu.start();
    }
    
    /**
     * Populate the bookstore with sample books
     */
    private static void populateBookstore(OnlineBookstore bookstore) {
        bookstore.addBookToCatalog(new Book("Java Programming", "John Smith", "123456789", 29.99));
        bookstore.addBookToCatalog(new Book("Data Structures", "Alice Johnson", "987654321", 39.99));
        bookstore.addBookToCatalog(new Book("Algorithms", "Bob Brown", "456789123", 49.99));
        bookstore.addBookToCatalog(new Book("Python Basics", "Emma Davis", "789123456", 19.99));
        bookstore.addBookToCatalog(new Book("Web Development", "Tom Wilson", "654321987", 34.99));
        bookstore.addBookToCatalog(new Book("Database Systems", "Sarah Miller", "321654987", 44.99));
        bookstore.addBookToCatalog(new Book("Operating Systems", "James Taylor", "147258369", 54.99));
        bookstore.addBookToCatalog(new Book("Machine Learning", "Jennifer White", "963852741", 59.99));
        
        System.out.println("Book catalog loaded with " + bookstore.getBookCount() + " books.");
    }
    
    /**
     * Customer menu class provides an interactive interface for customers
     */
    static class CustomerMenu {
        private OnlineBookstore bookstore;
        private Scanner scanner;
        private String customerName;
        private String shippingAddress;
        private Book[] shoppingCart;
        private int[] cartQuantities;
        private int cartItemCount;
        private static final int MAX_CART_ITEMS = 20;
    
        public CustomerMenu(OnlineBookstore bookstore) {
            this.bookstore = bookstore;
            this.scanner = new Scanner(System.in);
            this.shoppingCart = new Book[MAX_CART_ITEMS];
            this.cartQuantities = new int[MAX_CART_ITEMS];
            this.cartItemCount = 0;
        }
    
        // Start the customer menu system
        public void start() {
            System.out.println("=== Welcome to the Online Bookstore ===");
            
            // Get customer information
            System.out.print("Please enter your name: ");
            this.customerName = scanner.nextLine();
            System.out.print("Please enter your shipping address: ");
            this.shippingAddress = scanner.nextLine();
            System.out.println("Thank you, " + this.customerName + "!");
            
            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== Customer Menu ===");
                System.out.println("1. Browse all books");
                System.out.println("2. Search books by title");
                System.out.println("3. Search books by author");
                System.out.println("4. View shopping cart");
                System.out.println("5. Place order");
                System.out.println("6. Track my order");
                System.out.println("7. Exit");
                System.out.print("Please select an option (1-7): ");
                
                int choice = getIntInput();
                
                switch (choice) {
                    case 1:
                        browseBooks();
                        break;
                    case 2:
                        searchBooksByTitle();
                        break;
                    case 3:
                        searchBooksByAuthor();
                        break;
                    case 4:
                        viewCart();
                        break;
                    case 5:
                        placeOrder();
                        break;
                    case 6:
                        trackOrder();
                        break;
                    case 7:
                        exit = true;
                        System.out.println("Thank you for visiting the Online Bookstore!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    
        // Browse all books in the catalog
        private void browseBooks() {
            System.out.println("\n=== Book Catalog ===");
            displayBooks(this.bookstore.getBookCatalog(), this.bookstore.getBookCount());
            
            addToCartPrompt();
        }
    
        // Search for books by title
        private void searchBooksByTitle() {
            System.out.print("\nEnter title to search for: ");
            String title = scanner.nextLine();
            
            Book[] results = this.bookstore.searchBooksByTitle(title);
            System.out.println("\n=== Search Results ===");
            if (results.length > 0) {
                displayBooks(results, results.length);
                addToCartPrompt();
            } else {
                System.out.println("No books found with title: " + title);
            }
        }
    
        // Search for books by author
        private void searchBooksByAuthor() {
            System.out.print("\nEnter author name to search for: ");
            String author = scanner.nextLine();
            
            Book[] results = this.bookstore.searchBooksByAuthor(author);
            System.out.println("\n=== Search Results ===");
            if (results.length > 0) {
                displayBooks(results, results.length);
                addToCartPrompt();
            } else {
                System.out.println("No books found by author: " + author);
            }
        }
    
        // Display books with indices
        private void displayBooks(Book[] books, int count) {
            for (int i = 0; i < count; i++) {
                if (books[i] != null) {
                    System.out.println("[" + (i + 1) + "] " + books[i].getTitle() + 
                                      " by " + books[i].getAuthor() +
                                      " - $" + books[i].getPrice());
                }
            }
        }
    
        // Prompt to add books to cart
        private void addToCartPrompt() {
            System.out.print("\nWould you like to add a book to your cart? (y/n): ");
            String response = scanner.nextLine();
            
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Enter the book number to add to cart: ");
                int bookNum = getIntInput();
                
                Book[] catalog = this.bookstore.getBookCatalog();
                if (bookNum >= 1 && bookNum <= this.bookstore.getBookCount()) {
                    Book selectedBook = catalog[bookNum - 1];
                    
                    System.out.print("Enter quantity: ");
                    int quantity = getIntInput();
                    
                    if (quantity > 0) {
                        addToCart(selectedBook, quantity);
                    } else {
                        System.out.println("Invalid quantity. Please enter a positive number.");
                    }
                } else {
                    System.out.println("Invalid book number. Please try again.");
                }
            }
        }
    
        // Add a book to the shopping cart
        private void addToCart(Book book, int quantity) {
            // Check if the book is already in the cart
            for (int i = 0; i < this.cartItemCount; i++) {
                if (this.shoppingCart[i].getTitle().equals(book.getTitle())) {
                    // Book already in cart, increase quantity
                    this.cartQuantities[i] += quantity;
                    System.out.println(book.getTitle() + " quantity updated in cart.");
                    return;
                }
            }
            
            // If book not in cart, add it
            if (this.cartItemCount < MAX_CART_ITEMS) {
                this.shoppingCart[this.cartItemCount] = book;
                this.cartQuantities[this.cartItemCount] = quantity;
                this.cartItemCount++;
                System.out.println(book.getTitle() + " added to cart.");
            } else {
                System.out.println("Cart is full. Cannot add more items.");
            }
        }
    
        // View the contents of the shopping cart
        private void viewCart() {
            System.out.println("\n=== Your Shopping Cart ===");
            if (this.cartItemCount == 0) {
                System.out.println("Your cart is empty.");
                return;
            }
            
            double total = 0.0;
            for (int i = 0; i < this.cartItemCount; i++) {
                Book book = this.shoppingCart[i];
                int quantity = this.cartQuantities[i];
                double price = book.getPrice();
                double itemTotal = price * quantity;
                
                System.out.println((i + 1) + ". " + book.getTitle() + 
                                 " - Quantity: " + quantity + 
                                 " - Price: $" + price +
                                 " - Total: $" + String.format("%.2f", itemTotal));
                
                total += itemTotal;
            }
            
            System.out.println("---------------------------------------");
            System.out.println("Cart Total: $" + String.format("%.2f", total));
            
            // Option to remove items
            System.out.print("\nWould you like to remove an item? (y/n): ");
            String response = scanner.nextLine();
            
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Enter item number to remove: ");
                int itemNum = getIntInput();
                
                if (itemNum >= 1 && itemNum <= this.cartItemCount) {
                    removeFromCart(itemNum - 1);
                } else {
                    System.out.println("Invalid item number.");
                }
            }
        }
    
        // Remove an item from the shopping cart
        private void removeFromCart(int index) {
            if (index >= 0 && index < this.cartItemCount) {
                System.out.println(this.shoppingCart[index].getTitle() + " removed from cart.");
                
                // Shift remaining items to fill the gap
                for (int i = index; i < this.cartItemCount - 1; i++) {
                    this.shoppingCart[i] = this.shoppingCart[i + 1];
                    this.cartQuantities[i] = this.cartQuantities[i + 1];
                }
                
                this.cartItemCount--;
            }
        }
    
        // Place an order with the items in the shopping cart
        private void placeOrder() {
            if (this.cartItemCount == 0) {
                System.out.println("Your cart is empty. Cannot place an order.");
                return;
            }
            
            Order order = new Order(this.customerName, this.shippingAddress, MAX_CART_ITEMS);
            
            for (int i = 0; i < this.cartItemCount; i++) {
                order.addBook(this.shoppingCart[i], this.cartQuantities[i]);
            }
            
            this.bookstore.placeOrder(order);
            System.out.println("Your order has been placed successfully!");
            System.out.println("Your order ID is: " + order.getOrderId());
            System.out.println("Please save this ID to track your order status.");
            
            // Clear the cart after placing the order
            this.cartItemCount = 0;
        }
    
        // Track an order by ID
        private void trackOrder() {
            System.out.print("Enter your order ID: ");
            int orderId = getIntInput();
            
            Order order = this.bookstore.trackOrder(orderId);
            if (order != null) {
                System.out.println("\n=== Order Details ===");
                System.out.println(order);
            } else {
                System.out.println("Order not found. Please check your order ID.");
            }
        }
    
        // Helper method to safely get integer input
        private int getIntInput() {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                return -1;
            }
        }
    }
}