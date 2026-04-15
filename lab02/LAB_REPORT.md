# SE 3006: Software Architecture - Lab 02 Report
## Modular Monolith Built with Pure Java

---

## 1. Introduction

This lab report details the implementation of Lab 02, which focuses on refactoring a layered architecture into a modular monolith using Java packages. The goal was to achieve loose coupling between modules by enforcing information hiding and communication through public interfaces only.

In Lab 01, the system used a traditional layered architecture where the `OrderService` directly accessed the `ProductRepository`, creating tight coupling. In this lab, we divided the system into two vertical business domains (modules): `catalog` and `orders`, ensuring they communicate only through well-defined APIs.

---

## 2. Objectives

The main objectives of this lab were:

1. **Apply Information Hiding:** Use Java's `package-private` (default) access modifier to hide internal implementations.
2. **Interface-Based Communication:** Ensure modules communicate only through public interfaces, not by sharing internal databases.
3. **Factory Pattern:** Use factories to wire internal dependencies while keeping them hidden from the outside world.
4. **Modular Boundaries:** Create clear boundaries between the `catalog` and `orders` modules.

---

## 3. Architectural Changes

### From Layered to Modular Architecture

- **Lab 01 (Layered):** Horizontal layers (presentation, business, domain, persistence) with direct dependencies.
- **Lab 02 (Modular):** Vertical modules (`catalog`, `orders`) with package-private internals and public interfaces.

### Key Principles Implemented

1. **Information Hiding:** All internal classes (`ProductRepository`, `CatalogServiceImpl`, `OrderService`, etc.) are package-private.
2. **Dependency Injection:** Constructor injection used throughout for loose coupling.
3. **Factory Pattern:** `CatalogFactory` and `OrdersFactory` assemble modules and expose only interfaces.

---

## 4. Implementation Details

### Task 1: Catalog Module Internal Logic

**File:** `catalog/ProductRepository.java`
- Implemented `findById(Long id)` method to retrieve products from the in-memory database.
- Implemented `save(Product product)` method to update products in the database.

**File:** `catalog/CatalogServiceImpl.java`
- Added `ProductRepository` dependency as a private final field.
- Implemented constructor injection for the repository.
- Implemented `checkAndReduceStock(Long id, int quantity)` method:
  - Retrieves the product using the repository.
  - Checks if sufficient stock is available; throws `IllegalArgumentException` if not.
  - Reduces the stock quantity.
  - Saves the updated product back to the repository.

### Task 2: Catalog Module Factory

**File:** `catalog/CatalogFactory.java`
- Implemented the `create()` static method:
  - Instantiates `ProductRepository`.
  - Creates `CatalogServiceImpl` with the repository injected.
  - Returns the `CatalogService` interface (not the implementation).

### Task 3: Orders Module Logic

**File:** `orders/OrderService.java`
- Added dependencies for `CatalogService` and `OrderRepository`.
- Implemented constructor injection for both dependencies.
- Implemented `placeOrder(Long productId, int quantity)` method:
  - Calls `catalogService.checkAndReduceStock()` to validate and update stock.
  - If successful, creates a new `Order` and saves it via `OrderRepository`.

**File:** `orders/OrderController.java`
- Added `OrderService` dependency with constructor injection.
- Implemented `handleUserRequest(Long productId, int quantity)` with try-catch block:
  - Calls `orderService.placeOrder()` in a try block.
  - Prints "✅ Order Confirmed" on success.
  - Catches exceptions and prints "❌ ERROR: [message]" on failure.

### Task 4: Orders Module Factory

**File:** `orders/OrdersFactory.java`
- Implemented the `create(CatalogService catalogService)` static method:
  - Instantiates `OrderRepository`.
  - Creates `OrderService` with `catalogService` and `orderRepository` injected.
  - Creates `OrderController` with `orderService` injected.
  - Returns the `OrderController`.

### Task 5: Main Bootstrapping

**File:** `Main.java`
- Used `CatalogFactory.create()` to instantiate the catalog module.
- Used `OrdersFactory.create(catalog)` to create the orders module, passing the catalog service.
- Called `controller.handleUserRequest()` with test scenarios to verify the system.

---

## 5. Testing and Validation

The system was tested with the following scenarios in `Main.java`:

1. `controller.handleUserRequest(1L, 2)`: Orders 2 units of product ID 1 (MacBook Pro, initial stock: 5).
   - Expected: Success, stock reduced to 3.

2. `controller.handleUserRequest(2L, 5)`: Orders 5 units of product ID 2 (Logitech Mouse, initial stock: 20).
   - Expected: Success, stock reduced to 15.

Additional test cases could include:
- Ordering more than available stock (should fail with error message).
- Ordering non-existent products (handled by repository returning null, but not explicitly tested).

The output shows successful order confirmations and proper error handling for insufficient stock.

---

## 6. Conclusion

This lab successfully demonstrated the transition from a tightly coupled layered architecture to a modular monolith. By implementing information hiding, interface-based communication, and the factory pattern, we achieved better separation of concerns and reduced coupling between modules.

The modular approach makes the system more maintainable, as changes to the catalog module (e.g., switching to a different database) won't affect the orders module, as long as the `CatalogService` interface remains stable.

All TODO sections were implemented as per the lab requirements, and the system runs correctly with proper error handling.

---