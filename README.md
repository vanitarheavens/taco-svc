# Taco Loco Challenge

Taco Svc is an API that has been built to enable customers to place orders for their
delicious tacos. The service will take as input the
items and quantities ordered, and respond with the order total among other fields.

### Main features
1. Customer can create customer profile
2. Taco Loco admin can create different Taco menu items
3. A customer can place and order and get response with order price total

### Requirements
1. Java 11
2. Spring Boot 2.6.3
3. Maven 3.6.3
4. MySQL Workbench
5. Postman collections

## Taco Svc user guide

Clone the repo\
`git clone https://github.com/vanitarheavens/taco-svc.git`

Install dependencies\
`mvn clean install`

Using MySQL workbench, create a MySQL database called `taco-loco-db` and update the application properties with the database credentials
```spring.datasource.url = jdbc:mysql://localhost/taco-loco-db?allowPublicKeyRetrieval=true&serverTimezone=America/Chicago
spring.datasource.username = root
spring.datasource.password = ***********
server.port=8082
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.show-sql = true
spring.jpa.hibernate.use-new-id-generator-mappings=false
logging.level.root=WARN
```
Open the `TacoSvcApplication` class and run the application

### Endpoints

Create customer: `POST http://localhost:8082/customers`

Request body

```
{
    "firstName": "John",
    "lastName": "Doe"
}
```

Create Taco item: `POST http://localhost:8082/tacos`

Request body:
```
{
    "name": "Veggie Taco",
    "price": 2.5
}
```


Create an order from menu items and quantities: `POST http://localhost:8082/orders/1`

Path variable: Long

Request body: 
```
{
    "orderItems": [
        {
            "tacoId": 1,
            "quantity": 4
        },
        {
            "tacoId": 2,
            "quantity": 4

        }

    ]
}

```

Order response
```
{
    "customerId": 1,
    "orderId": 1,
    "orderTotal": 17.6,
    "orderDate": "2022-08-22"
}

```