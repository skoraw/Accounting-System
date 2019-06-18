# Accounting System App

It' a simple app that helps manage invoices, designed to be easy extendable. Choose between REST or SOAP web services (Front-end in progress) to communicate with one from four implemented databases. Application is licensed under [MIT](https://en.wikipedia.org/wiki/MIT_License).

## Installation
* JDK 11
* Apache Maven 3.x

## Build and Run
    mvn clean package
    mvn exec:java 
    
### Setup
#### Database
To setup database go to `application.properties`. You can choose from in-memory, in-file and hibernate databases.
```
pl.coderstrust.database=in-memory
pl.coderstrust.database=in-file
pl.coderstrust.database=hibernate
pl.coderstrust.databas=mongo
```
#### Security
```
spring.security.user.name=Login
spring.security.user.password=Password
```
#### Email Service
To receive notification by email, change adress int `application.properties`:
```
spring.mail.properties.receiver=coderstrust.gr10@gmail.com
```
### API
Application is available on localhost:8080. Use `http://localhost:8080/swagger-ui.html` to explore REST API using Swagger. You have to log in with login and password.

To test SOAP use Postman or SoapUI. Send requests to `http://localhost:8080/soap/invoices/invoices.wsdl`.
## Used Tech
<p float="left">
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/320px-Spring_Framework_Logo_2018.svg.png" alt="spring" width="200"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://www.ixpole.com/wp-content/uploads/2018/05/Swagger-logo-300x106.png" alt="swagger" width="200"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://cdn-images-1.medium.com/max/800/1*AiTBjfsoj3emarTpaeNgKQ.png" alt="junit" width="200"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://www.jacoco.org/images/jacoco.png" alt="jacoco" width="200"/>
<img src="https://www.ydop.com/wp-content/uploads/2015/06/json-logo-300x143.png" alt="json" width="200"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="http://fruzenshtein.com/wp-content/uploads/2014/01/Hibernate-logo.png" alt="hibernate" width="200"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://d1.awsstatic.com/rdsImages/postgresql_logo.6de4615badd99412268bc6aa8fc958a0f403dd41.png" alt="postgres" width="200"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://static.javadoc.io/org.mockito/mockito-core/2.27.0/org/mockito/logo.png" alt="mockito" width="200"/>
<img src="http://training.bizleap.com/wp-content/uploads/2018/02/maven-logo.png" alt="maven" width="200"/>   
<img src="https://xebialabs.com/wp-content/uploads/files/tool-chest/mongodb.jpg" alt="mongodb" width="200"/>
<img src="https://1.bp.blogspot.com/-6YJ9_NHn6ao/V1GiFoQ7RSI/AAAAAAAASes/4Nhlho624yQhymyGSB8Wf2h_IwePx3cdgCLcB/s1600/log.png" alt="slf4j" width="200"/>
</p>

## Authors
Project was created as part of Junior Java Developer - First Project course in CodersTrust by Tomasz W. and Wiktor S. under care by Łukasz R. - years of experience as professional java dev.
