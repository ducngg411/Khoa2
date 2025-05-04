// Searching algorithms for Order objects
public class SearchingAlgorithms {
    // Linear Search - finds an order with the specified ID
    public static Order linearSearch(Order[] orders, int n, int orderId) {
        // Check each order one by one
        for (int i = 0; i < n; i++) {
            if (orders[i] != null && orders[i].getOrderId() == orderId) {
                return orders[i];
            }
        }
        // Order not found
        return null;
    }
}