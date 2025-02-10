# Investment Aggregator  
## Overview  
This project is a simple investment aggregator that integrates with the [Brazilian stock exchange API (brapi.dev)](https://brapi.dev/) using **OpenFeign**. It is built with **Spring Boot** and follows a structured architecture with **JPA relationships**, **JUnit with Mockito for testing**, **password encoding**, and **Docker** for containerization.  

## What You Will Learn  

### 🔹 Creating a Simple CRUD with Spring Boot  
The easiest way to make a CRUD using **Spring Boot** is by creating a **Repository** for the entity you want to manage. Most of the time, this is an interface that extends `JpaRepository`, like in the example below:  

```java
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
```
### 🔹Writing Unit Tests with Mockito  

### The Arrange-Act-Assert (AAA) Pattern  
Mockito allows you to **mock dependencies** to isolate business logic during unit testing. A well-structured approach to writing tests is the **Arrange-Act-Assert (AAA) pattern**, which consists of three phases:  

1. **Arrange**: Set up the necessary conditions, create mock objects, and define expected behaviors.  
2. **Act**: Execute the method being tested.  
3. **Assert**: Verify that the actual output matches the expected result.  

### Example of a Unit Test with Mockito  
```java
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldReturnAccountWhenExists() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        Account mockAccount = new Account(accountId, "Investment Account");
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        // Act
        Account result = accountService.getAccountById(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(mockAccount.getDescription(), result.getDescription());
    }
}
```
####  Explanation of the Test
Arrange: We mock accountRepository and define behavior for findById().

Act: We call the getAccountById() method.

Assert: We check if the returned account matches expectations.

### Why Use Mockito?
✅ Isolates business logic by removing database dependencies.

✅ Improves test speed since it doesn’t require a real database connection.

✅ Allows behavior-driven testing, ensuring expected results for various scenarios.

Using Mockito, you can effectively test your Spring Boot services while keeping your tests fast, reliable, and independent from external dependencies. 🚀

### 🔹 Consuming an API with OpenFeign

To integrate with external APIs, this project uses OpenFeign, which simplifies HTTP client requests. The application fetches stock market data from the Brazilian stock exchange API.

You will need : 

#### Define the Feign Client :
```java
@FeignClient(
    name = "BrapiClient",
    url = "https://brapi.dev"
)
public interface BrapiClient {
    
    @GetMapping(value = "/api/quote/{stockId}")
    BrapiResponseDto getQuote(@RequestParam("token") String token,
                              @PathVariable("stockId") String stockId);
}
```
**Explanation**:

**@FeignClient**: 
Specifies the client name and the base URL of the API (https://brapi.dev).

@GetMapping: Defines a GET request to the /api/quote/{stockId} endpoint.

@RequestParam: Adds the token query parameter to the request.

@PathVariable: Binds the {stockId} in the URL to the stockId method parameter.

**Summary**

- OpenFeign makes it easy to call REST APIs using interfaces and annotations.

- Define the client with @FeignClient, specify the endpoint with @GetMapping, and use @RequestParam and @PathVariable for parameters.

- Inject the client into your service or controller to make API calls.

## Features
  
- Fetch stock data(Price) via the [Brazilian stock exchange API](https://brapi.dev/).  
- Manage users, accounts, and stock holdings.  
- Secure authentication with password encoding.  
- Structured architecture: **Service, Controller, Entities, Repository, and Client layers**.  
- Unit tests with **JUnit and Mockito**.  
- Containerized with **Docker**.  


## Database Model  
The project follows a relational database design, as shown in the UML diagram:  
- **User** manages investment **Accounts**.  
- Each **Account** has a **Billing Address**.  
- Accounts hold multiple **Stocks** via a junction table (**Account_Stock**).  

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`TOKEN`


## License

[MIT](https://choosealicense.com/licenses/mit/)
