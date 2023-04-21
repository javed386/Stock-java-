# Stock-java-
A stock project using JPA and Hibernate, with products, categories, suppliers, and sales recording.


This is a stock software project where the stock has several products with their quantity and product information.
Each product has a category and a supplier. The project also includes a sales table for the products, where in the DAO class of this table, 
you can filter sales to see how much a product sold in a certain period of time, what profit we had from that product, and other functions that are in the DAO class.

To compile the project, simply download it and put it in your preferred IDE. Don't forget to adjust the persistence.xml for the database you will be using.
Also, don't forget to download the dependencies below (which are in the pom.xml file):

JUnit Jupiter API (org.junit.jupiter:junit-jupiter-api:5.8.1)
JUnit Jupiter Engine (org.junit.jupiter:junit-jupiter-engine:5.8.1)
Hibernate EntityManager (org.hibernate:hibernate-entitymanager:5.4.27.Final)
MySQL Connector/J (mysql:mysql-connector-java:8.0.32)
Caelum Stella Core (br.com.caelum.stella:caelum-stella-core:2.1.2)
