import java.util.ArrayList;

// Queue implementation (FIFO - First In First Out)
public class Queue<T> {
    private ArrayList<T> elements;
    private int capacity;   // Maximum capacity of the queue
    
    public Queue(int capacity) {
        this.capacity = capacity;
        this.elements = new ArrayList<>(capacity);
    }
    
    public void enqueue(T item) {
        if (isFull()) {
            System.out.println("Queue overflow - Cannot add more elements");
            return;
        }
        this.elements.add(item);
    }
    
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue underflow - Queue is empty");
            return null;
        }
        return this.elements.remove(0);
    }
    
    public T peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty - Nothing to peek");
            return null;
        }
        return this.elements.get(0);
    }
    
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }
    
    public boolean isFull() {
        return this.elements.size() == this.capacity;
    }
    
    public int size() {
        return this.elements.size();
    }
}