# SE 3006

## Lab 01 Report

---

### 1. Objective

The goal of this lab was to learn how to build a layered software architecture using plain Java. We separated our code into three distinct layers (Presentation, Business, and Persistence) and used manual Constructor Injection to connect them, without relying on frameworks like Spring Boot.

### 2. What I Did

Following the strict layering rules (data only flows top-to-bottom), I completed the system by building it from the bottom up:

* **Task 1: Persistence Layer (`ProductRepository.java`)**
  * I created a simple mock database using a `HashMap` to store `Product` objects.
  * I wrote the `findById` and `save` methods so this layer can read and update data.

* **Task 2: Business Layer (`OrderService.java`)**
  * I injected the `ProductRepository` using a constructor.
  * I wrote the `placeOrder` method to handle the logic: it checks if there is enough stock. If not, it throws an error. If there is, it reduces the stock and saves the updated product.

* **Task 3: Presentation Layer (`OrderController.java`)**
  * I injected the `OrderService` using a constructor.
  * I wrote the `handleUserRequest` method using a `try-catch` block. This catches the error if there isn't enough stock, or prints a success message if the order goes through.

* **Task 4: System Setup (`Main.java`)**
  * I wired the system together in the correct order: Repository -> Service -> Controller.
  * I ran the required test cases to verify everything worked.

### 3. Test Results

I tested the code using the scenarios provided in the `Main` class:

* **Test 1 (MacBook Pro):** Requested 2 (Stock was 5).

  * **Result:** System successfully reduced stock and printed `✅ Order Confirmed`.
  
* **Test 2 (Logitech Mouse):** Requested 5 (Stock was 20).

  * **Result:** System successfully reduced stock and printed `✅ Order Confirmed`.

I also verified that if a user requests more than the available stock, the `catch` block successfully stops the order and prints an error message without crashing the whole program.

### 4. Conclusion

This lab showed me how different parts of a program should be kept separate based on their jobs. By writing the layers and injecting the dependencies manually, it is much easier to see how strict data flow works and why keeping things separated makes the code easier to fix and manage.
