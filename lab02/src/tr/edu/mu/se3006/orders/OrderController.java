package tr.edu.mu.se3006.orders;

// PUBLIC: Entry point for the Orders context.
public class OrderController {
    
    // TODO: Define OrderService dependency and use Constructor Injection
    OrderService orderService;

    OrderController(OrderService orderService) {
       this.orderService = orderService;
    }

    public void handleUserRequest(Long productId, int quantity) {
        System.out.println(">>> New Request: Product ID=" + productId + ", Quantity=" + quantity);
        // TODO: Call placeOrder inside a try-catch block. 
        try {
            orderService.placeOrder(productId, quantity);
            System.out.println("✅ Order Confirmed");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("❌ ERROR: " + e.getMessage());
        }
        // Print "✅ Order Confirmed" on success, or "❌ ERROR: [message]" on failure.
    }
}
