# Titanic Hexagonal Ship Microservice
 
This is a microservice composed by Kotlin, Jacoco Aggregate Reports and also Pitest Aggregate Reports. It has some other features just to complement the Hexagonal porpoise

## Database

The database is PostgreSQL just for complement the datastore module

## Docker

A docker-compose with the necessary PostgreSQL setting to run it locally

## Gradle

All dependencies are managed by Gradle 7.2

## Modules

1. Core: contains all common features used by the other modules
2. Datastore: database configuration and repositories
3. Domain: business layer
4. HTTP: the entrypoint layer where the consumers can call the available services