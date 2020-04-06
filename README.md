# TechBooks-Bookshelf

## Key Features Of This App

    1.Spring Security is used extensively to enable role based URI Access
    
    2.JWT is used to be used in a microservice architecture, as no session dependency is on server side.
    
    3.Spring Data JPA is used to use advantages of JPA and Hibernate
    
    4.Uses in memory database as well as my sql which can be changed based upon property change ddefault is in memory      database.
    
    5.Swagger Documented


### How to run application

  java -jar book-lender-store.jar, this jar is as per the last commit in this branch

### Roles in the system

    This appllication has four types of roles, all URI are accessed based upon role
          Super
          Admin
          Lender
          Borrower
          
     By default one Super and Admin user is always present in the application with there password as 1234
     
     Super User -> super@admin.com
     Admin User ->  admin@test.com 

### Login is required to access URIs

     User can login via swagger UI and can use the token generated authorization header of the response which also has userid that identifies that login user
   
     
 ### Swagger Link
http://localhost:8080/bookstore/swagger-ui.html
