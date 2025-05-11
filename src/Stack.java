import java.util.ArrayList;

// Stack implementation (LIFO - Last In First Out)
public class Stack<T> {
    private ArrayList<T> elements;  // ArrayList to store the stack elements
    private int capacity;           // Maximum capacity of the stack
    
    public Stack(int capacity) {
        this.capacity = capacity;
        this.elements = new ArrayList<>(capacity);
    }
    
    public void push(T item) {
        if (isFull()) {
            System.out.println("Stack overflow - Cannot add more elements");
            return;
        }
        this.elements.add(item);
    }
    
    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack underflow - Stack is empty");
            return null;
        }
        return this.elements.remove(this.elements.size() - 1);
    }
    
    public T peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty - Nothing to peek");
            return null;
        }
        return this.elements.get(this.elements.size() - 1);
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