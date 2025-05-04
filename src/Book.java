// Book class represents book information
public class Book implements Comparable<Book> {
    private String title;
    private String author;
    private String isbn;
    private double price;
    
    public Book(String title, String author, String isbn, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }
    
    // Getters
    public String getTitle() { return this.title; }
    public String getAuthor() { return this.author; }
    public String getIsbn() { return this.isbn; }
    public double getPrice() { return this.price; }
    
    @Override
    public String toString() {
        return "Book Details: " +
                "\n  Title: " + this.title +
                "\n  Author: " + this.author +
                "\n  ISBN: " + this.isbn +
                "\n  Price: $" + this.price;
    }
    
    @Override
    public int compareTo(Book other) {
        // Compare books by title (for sorting)
        return this.title.compareTo(other.title);
    }
}