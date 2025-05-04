// Sorting algorithms for Book objects
public class SortingAlgorithms {
    // Insertion Sort - sorts books by title in ascending order
    public static void insertionSort(Book[] books, int n) {
        for (int i = 1; i < n; i++) {
            Book currentBook = books[i];
            int j = i - 1;
            
            // Move elements greater than key to one position ahead
            while (j >= 0 && books[j].compareTo(currentBook) > 0) {
                books[j + 1] = books[j];
                j = j - 1;
            }
            
            books[j + 1] = currentBook;
        }
    }
}