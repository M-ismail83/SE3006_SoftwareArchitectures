package tr.edu.mu.se3006.orders;
import tr.edu.mu.se3006.catalog.CatalogService;

// PUBLIC Factory: Wires the orders module. Needs Catalog API to function.
public class OrdersFactory {
    public static OrderController create(CatalogService catalogService) {
        // TODO: Instantiate OrderRepository
        OrderRepository orderRepository = new OrderRepository();
        // TODO: Instantiate OrderService (inject catalogService and repository)
        OrderService orderService = new OrderService(catalogService, orderRepository);
        // TODO: Instantiate OrderController (inject service)
        OrderController orderController = new OrderController(orderService);

        // TODO: Return the controller
        return orderController;
    }
}
