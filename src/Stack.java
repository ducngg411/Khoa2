// Stack implementation (LIFO - Last In First Out)
public class Stack<T> {
    private Object[] elements;  // Array to store the stack elements
    private int top;            // Index of the top element
    private int capacity;       // Maximum capacity of the stack
    
    public Stack(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.top = -1;  // -1 indicates empty stack
    }
    
    public void push(T item) {
        if (isFull()) {
            System.out.println("Stack overflow - Cannot add more elements");
            return;
        }
        this.top = this.top + 1;
        this.elements[this.top] = item;
    }
    
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack underflow - Stack is empty");
            return null;
        }
        T item = (T) this.elements[this.top];
        this.top = this.top - 1;
        return item;
    }
    
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty - Nothing to peek");
            return null;
        }
        return (T) this.elements[this.top];
    }
    
    public boolean isEmpty() {
        return this.top == -1;
    }
    
    public boolean isFull() {
        return this.top == this.capacity - 1;
    }
    
    public int size() {
        return this.top + 1;
    }
}