# AmdocsAssignment

This Repository contains 3 different services created as part of the Amdocs Assessment

## Modules Include

- Assignment-UI 
	- a) Simple UI Web application to execute the Functionality mentioned in the document for Login and Create Profile.
	- b) This application can be access using http://localhost:9090/assignment
	- c) Login, Logout and Session handling will be done in this service

- AuthorizationService
	- a) Springboot Rest Microservice Project for User Authentication and also provides proxy gateway for ProfileService
	- b) Authentication API - http://localhost:8081/authservice/login
	- c) Profile Gateway API - http://localhost:8081/authservice/profile
	- d) Inital data for User Login has been setup. Refer src/main/resources/import.sql
	- e) Uses H2 Memory DB
	
- ProfileService
	- a) Springboot Rest Microservice Project for performing Profile CRUD operations
	- b) Profile API - http://localhost:8082/profileservice/profile
	- c) Inital data for a sample Profile has been setup. Refer src/main/resources/import.sql
	- d) Uses H2 Memory DB
