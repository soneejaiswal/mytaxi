mytaxi backend Applicant Test Service

# Task Description
You should be able to start the example application by executing com.mytaxi.MytaxiServerApplicantTestApplication, which starts a webserver on port 8080 (http://localhost:8080) and serves SwaggerUI where can inspect and try existing endpoints.

The project is based on a small web service which uses the following technologies:

* Java 1.8
* Spring MVC with Spring Boot
* Database H2 (In-Memory)
* Maven
* Intellij as IDE is preferred but not mandatory. We do provide code formatter for intellij and eclipse in the etc folder.


You should be aware of the following conventions while you are working on this exercise:

 * All new entities should have an ID with type of Long and a date_created with type of ZonedDateTime.
 * The architecture of the web service is built with the following components:
 	* DataTransferObjects: Objects which are used for outside communication via the API
   * Controller: Implements the processing logic of the web service, parsing of parameters and validation of in- and outputs.
   * Service: Implements the business logic and handles the access to the DataAccessObjects.
   * DataAccessObjects: Interface for the database. Inserts, updates, deletes and reads objects from the database.
   * DomainObjects: Functional Objects which might be persisted in the database.
 * TestDrivenDevelopment is a good choice, but it's up to you how you are testing your code.

You should commit into your local git repository and include the commit history into the final result.

## Task 1
 * Write a new Controller for maintaining cars (CRUD).
   * Decide on your own how the methods should look like.
   * Entity Car: Should have at least the following characteristics: license_plate, seat_count, convertible, rating, engine_type (electric, gas, ...)
   * Entity Manufacturer: Decide on your own if you will use a new table or just a string column in the car table.
 * Extend the DriverController to enable drivers to select a car they are driving with.
 * Extend the DriverController to enable drivers to deselect a car.
 * Extend the DriverDo to map the selected car to the driver.
 * Add example data to resources/data.sql

## Notes on Task 1
    * Entity Manufacturer considered as a string column in car table
    * Car and Driver relation considered as one to one due to no further requirement definition:
        * to store the log or trace of car and driver history it is recommended to have a new entity like CarDriverHitory whith car and driver and fromDate and thruDate as properties
        this relation is OneToMany from car to carDriverHistory and ManyToOne from CarDriverHistory to Car(or OneToMany from car to carDriverHistory)
        * there is no property name selected (and it is intentionally) in CarDO and selected car distinguished by relation to driver. In CarDTO we have a field for client to find easier if car is selected or not

## Task 2
First come first serve: A car can be selected by exactly one ONLINE Driver. If a second driver tries to select a already used car you should throw a CarAlreadyInUseException.

## Notes on Task 2
    * First comes first served is supported by @Transactional
    * there is no access method to unselect car from car controller but it happens with driver controller to prevent business non managed manipulation

## Task 3
Make use of the filter pattern to implement an endpoint in the DriverController to get a list of drivers with specific characteristics. Reuse the characteristics you implemented in task 1.

## Notes on Task 3
    * From Task one and specification it seems cars need to be filtered due to characteristics to be selected by driver. So this feature is added to CarController to filter cars
    * 3 sample criteria added for POC, obviously it can be extended with more options
    *** Task 3 updated by adding filter to Driver as well to follow the requirement

## Task 4
Security: secure the API. It's up to you how you are going to implement the security.

## Notes on Task 4
    * Basic security is considered for this task. oAuth2 seems a better choice of course regarding limited time considered for this assignment (4h)
     and also amount of time spent for other tasks which already exceeded (4h) the easiest solution considered
    ** In order to login please enter  username/password as john123/password and make the get request again and you can see the results.

NOTE:
Please make sure to not submit any personal data with your tests result. Personal data is for example your name, your birth date, email address etc.

