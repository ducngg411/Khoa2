// Online Bookstore class
public class OnlineBookstore {
    private Queue<Order> orderQueue;           // Queue of new orders waiting to be processed
    private Stack<Order> processedOrders;      // Stack of processed orders waiting to be completed
    private Order[] completedOrders;           // Array of completed orders
    private int completedOrderCount;
    private Book[] bookCatalog;                // Array of books available in the store
    private int bookCount;                     // Number of books in the catalog
    
    public OnlineBookstore(int maxOrders, int maxBooks) {
        this.orderQueue = new Queue<>(maxOrders);
        this.processedOrders = new Stack<>(maxOrders);
        this.completedOrders = new Order[maxOrders];
        this.completedOrderCount = 0;
        this.bookCatalog = new Book[maxBooks];
        this.bookCount = 0;
    }
    
    // Add a book to the catalog
    public void addBookToCatalog(Book book) {
        if (this.bookCount < this.bookCatalog.length) {
            this.bookCatalog[this.bookCount] = book;
            this.bookCount++;
        } else {
            System.out.println("Cannot add more books to the catalog. Maximum capacity reached.");
        }
    }
    
    // Get all books in the catalog
    public Book[] getBookCatalog() {
        return this.bookCatalog;
    }
    
    // Get the number of books in the catalog
    public int getBookCount() {
        return this.bookCount;
    }
    
    // Search for books by title (returns all books that contain the search term)
    public Book[] searchBooksByTitle(String title) {
        Book[] results = new Book[this.bookCount];
        int resultCount = 0;
        
        for (int i = 0; i < this.bookCount; i++) {
            if (this.bookCatalog[i].getTitle().toLowerCase().contains(title.toLowerCase())) {
                results[resultCount] = this.bookCatalog[i];
                resultCount++;
            }
        }
        
        // Create a new array with the exact size needed
        Book[] trimmedResults = new Book[resultCount];
        for (int i = 0; i < resultCount; i++) {
            trimmedResults[i] = results[i];
        }
        
        return trimmedResults;
    }
    
    // Search for books by author
    public Book[] searchBooksByAuthor(String author) {
        Book[] results = new Book[this.bookCount];
        int resultCount = 0;
        
        for (int i = 0; i < this.bookCount; i++) {
            if (this.bookCatalog[i].getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results[resultCount] = this.bookCatalog[i];
                resultCount++;
            }
        }
        
        // Create a new array with the exact size needed
        Book[] trimmedResults = new Book[resultCount];
        for (int i = 0; i < resultCount; i++) {
            trimmedResults[i] = results[i];
        }
        
        return trimmedResults;
    }
    
    // Places a new order in the bookstore system
    public void placeOrder(Order order) {
        this.orderQueue.enqueue(order);
        System.out.println("Order placed: Order ID " + order.getOrderId());
    }
    
    // Processes the next order in the queue
    public void processNextOrder() {
        if (!this.orderQueue.isEmpty()) {
            Order order = this.orderQueue.dequeue();
            
            // Sort books in the order by title using insertion sort
            SortingAlgorithms.insertionSort(order.getBooks(), order.getBookCount());
            
            order.setStatus("Processed");
            this.processedOrders.push(order);
            
            System.out.println("Order processed: Order ID " + order.getOrderId());
        } else {
            System.out.println("No orders to process - Order queue is empty");
        }
    }
    
    // Completes the next processed order
    public void completeNextOrder() {
        if (!this.processedOrders.isEmpty()) {
            Order order = this.processedOrders.pop();
            
            order.setStatus("Completed");
            this.completedOrders[this.completedOrderCount] = order;
            this.completedOrderCount = this.completedOrderCount + 1;
            
            System.out.println("Order completed: Order ID " + order.getOrderId());
        } else {
            System.out.println("No processed orders to complete - Processed order stack is empty");
        }
    }
    
    // Tracks an order by its order ID
    public Order trackOrder(int orderId) {
        // First check the completed orders using linear search
        Order order = SearchingAlgorithms.linearSearch(this.completedOrders, this.completedOrderCount, orderId);
        
        if (order != null) {
            return order;
        }
        
        // If not found, check the processed orders by popping and pushing back
        Stack<Order> temporaryStack = new Stack<>(this.processedOrders.size());
        Order result = null;
        
        while (!this.processedOrders.isEmpty()) {
            Order currentOrder = this.processedOrders.pop();
            if (currentOrder.getOrderId() == orderId) {
                result = currentOrder;
            }
            temporaryStack.push(currentOrder);
        }
        
        // Restore processed orders
        while (!temporaryStack.isEmpty()) {
            this.processedOrders.push(temporaryStack.pop());
        }
        
        if (result != null) {
            return result;
        }
        
        // If not found in processed orders, check the order queue
        Queue<Order> temporaryQueue = new Queue<>(this.orderQueue.size());
        result = null;
        
        while (!this.orderQueue.isEmpty()) {
            Order currentOrder = this.orderQueue.dequeue();
            if (currentOrder.getOrderId() == orderId) {
                result = currentOrder;
            }
            temporaryQueue.enqueue(currentOrder);
        }
        
        // Restore order queue
        while (!temporaryQueue.isEmpty()) {
            this.orderQueue.enqueue(temporaryQueue.dequeue());
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        // Redirect to the Main class
        System.out.println("Starting Online Bookstore from OnlineBookstore class...");
        Main.main(args);
    }
}