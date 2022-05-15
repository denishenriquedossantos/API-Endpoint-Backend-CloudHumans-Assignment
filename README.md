# API-Endpoint-Backend-CloudHumans-Assignment
This is an attempt at solving the Backend Take Home Assignment proposed by Cloud Humans utilizing the Kotlin language.

This project is an attempt at solving the [Cloud Humans Take Home Assignment](https://github.com/cloudhumans/backend-take-home) by creating a simple API endpoint that receives a JSON payload containing a Pro's information and returns another indicating which project they have been paired with along with which ones they are eligible to work on and which ones they're not.

## How to run it
A simple way to run this project is through the use of JetBrain's [Intellij IDEA](https://www.jetbrains.com/idea/):
- Install Intellij IDEA
- Download the project
- Load Gradle project scripts
- Build and run *ApiEndpointDemoApplication.kt*

With the project up and running you should be able to interact with it in your [localhost](http://localhost:8080).

A Pro's information can be input through a `POST` request with a header containing `Content-Type: application/json` and the JSON payload containing the Pro's information as its body.

The output information can be visualized by a `GET` request, which can be done by simply reloading the page.

## Technical decisions made when developing the project
The choice of using the Kotlin language for this project was due to its ease of use and acessible tutorials with the Spring Framework.

The base for such a project can be easily gotten with the [Spring Initializr](https://start.spring.io/). In this case by choosing Gradle Project and Kotlin language along with adding the Spring Web dependency.

The algorithm used was chosen to be separated in two parts: one function for defining the eligibility score and another for determining each project's eligibility.

For the project eligibility function it was chosen to use a list of pairs representing all available projects so as to grant ease of use in the case of adding or removing projects from the list.

## Relevant comments
This project uses Kotlin, Gradle and Spring Web.
The default state of the output is a structured JSON with empty values, so the first thing you should see when requesting a `GET` before a `POST` is a JSON with an integer defaulting to zero, an empty string and two empty lists.
