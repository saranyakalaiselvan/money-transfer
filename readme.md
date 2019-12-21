### Banking Application:
- Sample application lets user to sign up, create bank account, deposit, withdraw and transfer money to another bank account.
- Also, this let administrators to view all users information and user information based on username

### Assumptions:
- Roles - Customer and administrator
- Currency is of same type

### Technology Stack:
- Spring Boot
- Spring Security, 
- data-jpa, 
- web
- Hibernate
- h2 database(inbuilt)

Spring boot reduces lots of development time and increases productivity. It avoids writing lots of boilerplate Code, using annotations. Also, it is very easy to integrate Spring Boot Application with its Spring Ecosystem like Spring JDBC, Spring Spring Security etc. Spring Security provides easiest approach for authentication and authorization.
Used h2-database for simplicity. 

### endpoints:
see ENDPOINTS.md

### Build execute:
- Download from git and import as existing project in java IDE
- Run as java application - MoneytransferApplication(com.transaction.moneytransfer.MoneytransferApplication)
- Use the curl commands from endpoints.md
- Sign up is possible through user post request. This page does not require authentication.
- Each endpoint needs to be authorized(logged in user credetials will be taken)

### H2 Console:
- http://localhost:8081/h2-console
- First login using the user created(Spring authentication)
- Second using the h2 credentials as per datasource in application.yml
- database info : jdbc:h2:mem:testdb
- username : saran 
- password : saran
