# Financing Application

This project implements a financing application with three REST APIs, a simple landing page, and Swagger documentation.

## Overview

The financing application provides various endpoints to handle loan applications and offers. It includes both synchronous and asynchronous APIs for applying for financing, as well as a method to retrieve application information.

## Features

- **REST APIs:** The project includes three main REST APIs:
    - `/api/apply`: Submits a loan application synchronously.
    - `/api/applyAsync`: Submits a loan application asynchronously and receives offers as they become available.
    - `/api/applications/{id}`: Retrieves application details based on the provided ID.

- **Landing Page:** A simple landing page allows users to submit loan applications using the synchronous `/apply` API.

- **Swagger Documentation:** Swagger is integrated to provide interactive API documentation. Access the Swagger UI at `/swagger-ui.html` after running the application.

## Technologies Used

- Java
- Spring Boot
- RESTful APIs
- Swagger for API Documentation

## Getting Started

1. **Clone the Repository:**
   ```shell
   git clone https://github.com/P0dar0k/financing.git
