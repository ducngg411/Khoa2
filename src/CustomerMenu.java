import java.util.Scanner;

public class CustomerMenu {
    // Customer information
    private String customerName;
    private String shippingAddress;
    
    // Shopping cart management
    private Book[] cart;
    private int[] cartQuantities;
    private int itemCount;
    private static final int MAXIMUM_ITEMS_IN_CART = 20;
    
    // Required objects
    private OnlineBookstore store;
    private Scanner input;

    public CustomerMenu(OnlineBookstore store) {
        this.store = store;
        this.input = new Scanner(System.in);
        this.cart = new Book[MAXIMUM_ITEMS_IN_CART];
        this.cartQuantities = new int[MAXIMUM_ITEMS_IN_CART];
        this.itemCount = 0;
    }

    public void start() {
        System.out.println("=== Welcome to Online Bookstore ===");
        
        getCustomerInfo();
        
        showMainMenu();
    }

    /**
     * Get basic customer information
     */
    private void getCustomerInfo() {
        System.out.print("Enter your name: ");
        this.customerName = input.nextLine();
        
        System.out.print("Enter shipping address: ");
        this.shippingAddress = input.nextLine();
        
        System.out.println("Thank you, " + this.customerName + "!");
    }

    /**
     * Display and handle main menu
     */
    private void showMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. View all books");
            System.out.println("2. Search by title");
            System.out.println("3. Search by author");
            System.out.println("4. View cart");
            System.out.println("5. Place order");
            System.out.println("6. Track order");
            System.out.println("7. Exit");
            System.out.print("Select option (1-7): ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    searchByTitle();
                    break;
                case 3:
                    searchByAuthor();
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
                    System.out.println("Thank you for using our service!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Display all books in the store
     */
    private void viewAllBooks() {
        System.out.println("\n=== BOOK CATALOG ===");
        displayBooks(store.getBookCatalog(), store.getBookCount());
        promptAddToCart();
    }

    /**
     * Search books by title
     */
    private void searchByTitle() {
        System.out.print("\nEnter book title to search: ");
        String title = input.nextLine();
        
        Book[] results = store.searchBooksByTitle(title);
        System.out.println("\n=== SEARCH RESULTS ===");
        
        if (results.length > 0) {
            displayBooks(results, results.length);
            promptAddToCart();
        } else {
            System.out.println("No books found with title: " + title);
        }
    }

    /**
     * Search books by author
     */
    private void searchByAuthor() {
        System.out.print("\nEnter author name to search: ");
        String author = input.nextLine();
        
        Book[] results = store.searchBooksByAuthor(author);
        System.out.println("\n=== SEARCH RESULTS ===");
        
        if (results.length > 0) {
            displayBooks(results, results.length);
            promptAddToCart();
        } else {
            System.out.println("No books found by author: " + author);
        }
    }

    /**
     * Display list of books with numbers
     */
    private void displayBooks(Book[] books, int count) {
        for (int i = 0; i < count; i++) {
            if (books[i] != null) {
                System.out.printf("[%d] %s - Author: %s - Price: $%.2f%n",i + 1, books[i].getTitle(), books[i].getAuthor(), books[i].getPrice());
            }
        }
    }

    /**
     * Ask user if they want to add a book to cart
     */
    private void promptAddToCart() {
        System.out.print("\nWould you like to add a book to cart? (y/n): ");
        String response = input.nextLine();
        
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Enter book number to add: ");
            int bookNum = getIntInput();
            
            Book[] catalog = store.getBookCatalog();
            if (bookNum >= 1 && bookNum <= store.getBookCount()) {
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

    /**
     * Adds a book to cart or updates quantity if book already exists
     */
    private void addToCart(Book book, int quantity) {
        // Check if book is already in cart
        for (int i = 0; i < itemCount; i++) {
            if (cart[i].getTitle().equals(book.getTitle())) {
                cartQuantities[i] += quantity;
                System.out.println("Updated quantity of " + book.getTitle() + " in cart.");
                return;
            }
        }
        
        if (itemCount < MAXIMUM_ITEMS_IN_CART) {
            cart[itemCount] = book;
            cartQuantities[itemCount] = quantity;
            itemCount++;
            System.out.println("Added " + book.getTitle() + " to cart.");
        } else {
            System.out.println("Cart is full. Cannot add more items.");
        }
    }

    /**
     * Display cart contents
     */
    private void viewCart() {
        System.out.println("\n=== YOUR SHOPPING CART ===");
        if (itemCount == 0) {
            System.out.println("Your cart is empty.");
            return;
        }
        
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) {
            Book book = cart[i];
            int quantity = cartQuantities[i];
            double price = book.getPrice();
            double itemTotal = price * quantity;
            
            System.out.printf("%d. %s - Quantity: %d - Price: $%.2f - Total: $%.2f%n",
                i + 1,
                book.getTitle(),
                quantity,
                price,
                itemTotal);
            
            total += itemTotal;
        }
        
        System.out.println("---------------------------------------");
        System.out.printf("Cart Total: $%.2f%n", total);
        
        System.out.print("\nWould you like to remove an item? (y/n): ");
        String response = input.nextLine();
        
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Enter item number to remove: ");
            int itemNum = getIntInput();
            
            if (itemNum >= 1 && itemNum <= itemCount) {
                removeFromCart(itemNum - 1);
            } else {
                System.out.println("Invalid item number.");
            }
        }
    }

    /**
     * Remove an item from cart
     */
    private void removeFromCart(int index) {
        // Shift remaining items up
        for (int i = index; i < itemCount - 1; i++) {
            cart[i] = cart[i + 1];
            cartQuantities[i] = cartQuantities[i + 1];
        }
        
        cart[itemCount - 1] = null;
        cartQuantities[itemCount - 1] = 0;
        itemCount--;
        
        System.out.println("Item removed from cart.");
    }

    /**
     * Processes the order and clears the cart if successful
     */
    private void placeOrder() {
        if (itemCount == 0) {
            System.out.println("Cart is empty. Please add items before placing order.");
            return;
        }

        Order order = new Order(customerName, shippingAddress, MAXIMUM_ITEMS_IN_CART);
        
        for (int i = 0; i < itemCount; i++) {
            order.addBook(cart[i], cartQuantities[i]);
        }
        
        store.placeOrder(order);
        System.out.println("\nOrder placed successfully!");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Please save this ID to track your order status.");
        clearCart();
    }

    /**
     * Clear entire cart
     */
    private void clearCart() {
        for (int i = 0; i < itemCount; i++) {
            cart[i] = null;
            cartQuantities[i] = 0;
        }
        itemCount = 0;
    }

    /**
     * Track order status
     */
    private void trackOrder() {
        System.out.print("\nEnter order ID: ");
        int orderId = getIntInput();
        
        Order order = store.trackOrder(orderId);
        if (order != null) {
            System.out.println("\n=== ORDER INFORMATION ===");
            System.out.println(order);
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }

    /**
     * Safely gets integer input from user with error handling
     */
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
} 