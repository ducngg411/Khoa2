// Queue implementation (FIFO - First In First Out)
public class Queue<T> {
    private Object[] elements;
    private int front;      // Index of the front element
    private int rear;       // Index of the rear element
    private int capacity;   // Maximum capacity of the queue
    private int count;      // Current number of elements in the queue
    
    public Queue(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.count = 0;
    }
    
    public void enqueue(T item) {
        if (isFull()) {
            System.out.println("Queue overflow - Cannot add more elements");
            return;
        }
        // Circular increment of rear index
        this.rear = (this.rear + 1) % this.capacity;
        this.elements[this.rear] = item;
        this.count++;
    }
    
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue underflow - Queue is empty");
            return null;
        }
        // Get the item at the front
        T item = (T) this.elements[this.front];
        // Circular increment of front index
        this.front = (this.front + 1) % this.capacity;
        this.count--;
        return item;
    }
    
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty - Nothing to peek");
            return null;
        }
        return (T) this.elements[this.front];
    }
    
    public boolean isEmpty() {
        return this.count == 0;
    }
    
    public boolean isFull() {
        return this.count == this.capacity;
    }
    
    public int size() {
        return this.count;
    }
}