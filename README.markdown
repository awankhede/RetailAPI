# ApplicationAPI

ApplicationAPI is a Rest Service to retrieve and/or update product data for the company, myRetail.

## Installation

Make sure you have the following tools installed in the project: Java, Java IDE, Maven, MongoDB, Postman

I used the Homebrew Formulae suite to install tools that were not already installed in my workspace as they have an efficient process for downloading tools:


HOMEBREW (Optional Step)

Optional step to download homebrew to aide with the installation of all the tools.

Follow this [installation guide](https://docs.brew.sh/Installation) to download Homebrew.

JAVA

Make sure that you have Java correctly installed in your local workspace.

Direct Install: Follow this [link](https://java.com/en/download/)

Homebrew Install:
```bash
brew cask install java
```

JAVA IDE

Make sure that you have tool to retrieve Git repository data and can host a Java application. I recommend using IntelliJ.

IntelliJ Direct Install: Follow this [link](https://www.jetbrains.com/idea/download)

IntelliJ Homebrew Install:
```bash
brew cask install intellij-idea-ce
```

MAVEN

Make sure that you have Maven installed as this will aide in the build of the project.

Direct Install: Follow this link to [download](https://maven.apache.org/download.cgi) and this link to [install](https://maven.apache.org/install.html)

Homebrew Install:
```bash
brew install maven
```

MONGO DB

Make sure that you have MongoDB installed as this tool will allow you the ability to work with the noSQL database defined in mongo.

Direct Install: Follow this [link](https://www.mongodb.com/try/download/community)

Homebrew Install:
```bash
brew install mongodb-community@4.4
```

REST API TEST TOOL

Make sure that you have a Rest API test tool installed as this  will allow you to perform integration tests on the Rest services. I recommend using Postman

Direct Install: Follow this [link](https://www.postman.com/downloads/)

Homebrew Install:
```bash
brew cask install postman
```

## Running the Application

STEP 1: 

Clone Project from Git into your personal workspace and load it on an IDE.

STEP 2: 

Run the following to install project dependencies and compile project. 
```Bash
mvn clean install
```

STEP 3:

Run the following to install Mongo DB and to populate the local database that can be used for the REST service.
```Bash
mongo
use myretailDB
db.currentprice.insertMany([
{ "id" : "13860428", "value" : "13.49", "currencyCode" : "USD"},
{ "id" : "54456119", "value" : "4.39", "currencyCode" : "USD"},
{ "id" : "13264003", "value" : "5.99", "currencyCode" : "USD"},
{ "id" : "12954218", "value" : "0.99", "currencyCode" : "USD"}])
```

STEP 4:

Run the Unit test suite by running the 'TestAll.java' class. This will launch a suite of unit tests and validate whether the methods are working as expected.

STEP 5: 

Run the Application by launching the 'ApplicationAPI.java' class. This should output startup logs in the console that ends with the following message and no error traces: 

```Bash
com.myretailapi.ApplicationAPI           : Started ApplicationAPI in {X} seconds (JVM running for {X})
```

STEP 6: 

Connect to the RestService via Postman and perform rest commands to connect to the running API service.
    
1. Perform ping call to make sure service is hosted correctly. 
   Set the request type to be 'GET'
```Bash
        http://localhost:8080/products/ping
```
2. Perform the call to get product details based on the product id. 
   Set the request type to be 'GET' and define a header 'Accept' to be 'application/json'. 
   Concat the product id to the end of the URI to be sent in as a path variable. 
```Bash
        http://localhost:8080/products/{id}
```   
3. Perform the call to update product details based on the product id. 
   Set the request type to be 'PUT', define a header 'Accept' to be 'application/json', and define 'Content-Type' to be 'application/json'. 
   Concat the product id to the end of the URI to be sent in as a path variable.
   Send in the new product details to be updated in a JSON request body.
   Select the option to send in raw data and send in the body in the below format and then run the provided URI:

```Bash
        {"id": "13860428",
           "name": "The Big Lebowski (Blu-ray)",
           "current_price": {
               "value": "13.49",
               "currency_code": "USD"
           }
        }
       
       http://localhost:8080/products/{id}
```
