# ApplicationAPI

ApplicationAPI is a Rest Service to retrieve and/or update product data for the company, myRetail.

## Installation

Make sure you have the following tools installed in the project. I used the Homebrew Formulae suite to install tools that were not already installed in my workspace as they have an efficient process for downloading tools:
Java, Java IDE, Maven, MongoDB, Postman

###Homebrew (Optional Step)

Optional step to download homebrew to aide with the installation of all the tools.

Follow this [installation guide](https://docs.brew.sh/Installation) to download Homebrew.

###Java

Make sure that you have Java correctly installed in your local workspace.

Direct Install: Follow this [link](https://java.com/en/download/)

Homebrew Install:
```bash
brew cask install java
```

###Java IDE

Make sure that you have tool to retrieve Git repository data and can host a Java application. I recommend using IntelliJ.

IntelliJ Direct Install: Follow this [link](https://www.jetbrains.com/idea/download)

IntelliJ Homebrew Install:
```bash
brew cask install intellij-idea-ce
```

###Maven

Make sure that you have Maven installed as this will aide in the build of the project.

Direct Install: Follow this link to [download](https://maven.apache.org/download.cgi) and this link to [install](https://maven.apache.org/install.html)

Homebrew Install:
```bash
brew install maven
```

###MongoDB

Make sure that you have Maven installed as this tool will allow you the ability to work with the noSQL database defined in mongo.

Direct Install: Follow this [link](https://www.mongodb.com/try/download/community)

Homebrew Install:
```bash
brew install mongodb-community@4.4
```

###Postman

Make sure that you have Postman installed as this tool will allow you to perform integration tests on the Rest services

Direct Install: Follow this [link](https://www.postman.com/downloads/)

Homebrew Install:
```bash
brew cask install postman
```




- Clone the project from Git.

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