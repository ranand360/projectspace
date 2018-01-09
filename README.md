# ProjectSpace
-----------------------
A marketplace for software projects

## Requirements
-----------------------

Download and install the following
* JDK 1.8
* Maven 3.5
* Tomcat 7

## Setup Instructions
-----------------------
* Run mvn install
* This should generate the war inside the target folder by name projectspace.war
* Copy the war file into the webapps folder of tomcat
* Start Tomcat server from ${CATALINA_HOME}/Tomcat7
* Assuming Tomcat is running on port 8080 (Default), navigate to http://localhost:8080/ProjectSpace/{api}

## API Guide
--------------
### GET /projects
-----------------
* API to get projects that are available. Allows the GET action to get a project by id (or) get all projects (or) get only open projects
	@param id - id for the project being request (optional)
	@param open - true/false to filter only projects that are open in the result (optional)

### POST /projects
------------------ 
* API to create a project. Allows the POST action to submit a project for auction
	@param requirement - Requirement for the project.
	@param maxBudget - Maximum budget to bid for the project. 
	@param endTime - End time to stop accepting bids for the project
	@param sellerId - seller's Id who is creating the project

### POST /bid
-------------	
* API to bid for a project. Allows the POST action to submit a bid
	@param buyerId - the buyer who is making the bid
	@param projectId - the project for which the bid is being made
	@param bidAmount - the amount to bid for


## Assumptions and Defaults
----------------------------
### Default Behavior
--------------------
* bidValue is Defaulted to 100,000
* endTime is Defaulted to 24 from current time
* 