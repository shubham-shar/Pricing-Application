# Pricing Application
A simple restful service built with Spring boot for fetching courses and their corresponding prices 
- Endpoints
    - `/course/{id}/price` \
      where id is the 'id of a course'
    - `/course/all` 
    - `/course/strategy`

## Getting Started
Clone the github Repo and import in intellij (or your choice of IDE) \
*Notes:* 
- If you have the zipped project, just unzip it and import in any IDE
- SQL Queries to generate Schema and populate data are writen in `schema.sql` and `data.sql` files resp. \
  You can find them inside `src/main/resources` 
  
*Assumptions:*
- INR is the default currency.
- Only three currency taken in account - `INR`, `USD`, `EUR`
- Conversion rate can be changed(inside `application.yml`), their current value was taken on (28/03/2021)

### Prerequisites
- Java 11
- gradle-6.8.3 (Gradle wrapper is already present)
- Intellij (or your choice of IDE)

### Installing
- Install java 11 \
  You can use [sdman](https://sdkman.io/install) and choose 11.0.9.j9-adpt as java<br>
  `sdk install java 11.0.9.j9-adpt`
  
### CURLS
- Get course pricing details by id
    ```
    curl -L -X GET 'http://localhost:8080/course/1/price?currency=inr'
    ```
- Get pricing details of all courses 
    ```
    curl -L -X GET 'http://localhost:8080/course/all?currency=inr'
    ```
- Get pricing details of all courses based on pricing strategy
    ```
    curl -L -X GET 'http://localhost:8080/course/strategy?currency=inr&type=ONE_TIME'    
    ```
    Here strategy can be of three types - `FREE`, `ONE_TIME`, `SUBSCRIPTION`
    
## Built With
* [Spring boot ](https://spring.io/projects/spring-boot) - The framework used
* [Gradle](https://gradle.org/) - Dependency Management

## Authors
* **Shubham Sharma** - *Owner* - [Github](https://github.com/shubham-shar)

## Acknowledgments
- [Baeldung](https://www.baeldung.com)
- [StackoverFlow](https://stackoverflow.com/)
