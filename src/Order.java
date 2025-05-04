// Order class represents a customer order in the online bookstore
public class Order {
    // Static variable to generate unique order IDs
    private static int nextOrderId = 1000;
    
    private int orderId;
    private String customerName;
    private String shippingAddress;
    private Book[] books;
    private int[] quantities;
    private int bookCount;
    private String status;
    
    public Order(String customerName, String shippingAddress, int maxBooks) {
        this.orderId = nextOrderId++;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.books = new Book[maxBooks];
        this.quantities = new int[maxBooks];
        this.bookCount = 0;
        this.status = "Processing";
    }
    
    public void addBook(Book book, int quantity) {
        if (bookCount < books.length) {
            books[bookCount] = book;
            quantities[bookCount] = quantity;
            bookCount++;
        } else {
            System.out.println("Cannot add more books to this order. Maximum capacity reached.");
        }
    }
    
    // Getters and setters
    public int getOrderId() { return this.orderId; }
    public String getCustomerName() { return this.customerName; }
    public String getShippingAddress() { return this.shippingAddress; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public Book[] getBooks() { return this.books; }
    public int[] getQuantities() { return this.quantities; }
    public int getBookCount() { return this.bookCount; }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Order ID: ").append(this.orderId);
        stringBuilder.append("\nCustomer: ").append(this.customerName);
        stringBuilder.append("\nShipping Address: ").append(this.shippingAddress);
        stringBuilder.append("\nStatus: ").append(this.status);
        stringBuilder.append("\nBooks:");
        
        for (int i = 0; i < this.bookCount; i++) {
            stringBuilder.append("\n  - ").append(this.books[i].getTitle());
            stringBuilder.append(" by ").append(this.books[i].getAuthor());
            stringBuilder.append(" (").append(this.quantities[i]).append(" copies)");
        }
        
        return stringBuilder.toString();
    }
}