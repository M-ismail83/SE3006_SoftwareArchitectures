package tr.edu.mu.se3006.orders;
import tr.edu.mu.se3006.catalog.CatalogService;

class OrderService {
    // TODO: Define CatalogService and OrderRepository dependencies
    CatalogService catalogService;
    OrderRepository orderRepository;
    
    // TODO: Implement Constructor Injection
    OrderService(CatalogService catalogService, OrderRepository orderRepository) {
        this.catalogService = catalogService;
        this.orderRepository = orderRepository;
    }
    
    void placeOrder(Long productId, int quantity) {
        // TODO 1: Call catalogService to check and reduce stock
        catalogService.checkAndReduceStock(productId, quantity);
        // TODO 2: If successful, create a new Order and save it via OrderRepository
        if (catalogService != null) {
            Order order = new Order(productId, quantity);
            orderRepository.save(order);
        }
    }
}
