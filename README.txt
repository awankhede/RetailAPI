Setting up the Readme file

- Clone the project from Git.
- Install MongoDB
- Install Maven
- Run Application - ApplicationAPI class
- Connect to RestService via Rest Tool (ex. Postman) & run commands
    Ping to make sure service is hosted correctly -- http://localhost:8080/products/ping
    Get Product details                           -- http://localhost:8080/products/{id}
    Update Product details                        -- http://localhost:8080/products/{id}
                                                       & set requestbody to follow the below JSON format
       {"id": "13860428",
           "name": "The Big Lebowski (Blu-ray)",
           "current_price": {
               "value": "13.49",
               "currency_code": "USD"
           }
       }