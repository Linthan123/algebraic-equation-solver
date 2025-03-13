Here’s a sample README file for your Algebraic Equation Solver project:

---

# Algebraic Equation Solver

## Overview

The **Algebraic Equation Solver** is a Spring Boot-based application designed to store and evaluate algebraic equations. It uses the **JEXL** (Java Expression Language) library for equation evaluation. This project provides RESTful APIs to store equations, retrieve all stored equations, and evaluate equations based on user-provided variable values.

## Features

- **Store an equation**: Add an algebraic equation by submitting a `POST` request.
- **Retrieve all equations**: Fetch all stored equations via a `GET` request.
- **Evaluate an equation**: Evaluate a specific equation based on the values of variables provided via a `POST` request.

## Tech Stack

- **Backend Framework**: Spring Boot
- **Expression Evaluation**: Apache Commons JEXL
- **Database**: In-memory storage using a `HashMap`
- **Other Libraries**: 
  - Spring Web
  - Spring Boot DevTools (for easy development)

## Setup and Installation

### Prerequisites

- JDK 11 or higher
- Maven (for building the project)
- IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code (optional)

### Steps to Run Locally

1. **Clone the repository**

   ```bash
   git clone <repository_url>
   cd <project_folder>
   ```

2. **Build the project with Maven**

   ```bash
   mvn clean install
   ```

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on port `8080` by default.

### Accessing the Application

- **Base URL**: `http://localhost:8080/api/equations`

- **POST /api/equations**
  
  Add a new equation to the system.

  **Request Body Example**:
  ```json
  {
      "equation": "x + y"
  }
  ```

  **Response Example**:
  ```json
  {
      "equationId": "equation_id"
  }
  ```

- **GET /api/equations**

  Get all stored equations.

  **Response Example**:
  ```json
  [
    {
        "equationId": "equation_id",
        "equation": "x + y"
    }
  ]
  ```

- **POST /api/equations/{equationId}/evaluate**

  Evaluate a specific equation with the provided variable values.

  **Request Path Example**: `/api/equations/{equationId}/evaluate`

  **Request Body Example**:
  ```json
  {
      "variables": {
          "x": 5,
          "y": 3
      }
  }
  ```

  **Response Example**:
  ```json
  {
      "equationId": "equation_id",
      "variables": {
          "x": 5,
          "y": 3
      },
      "result": 8
  }
  ```

## Testing

You can test the application using **Postman** or any API testing tool.

### Example Postman Scenarios

1. **Store an Equation**

   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/equations`
   - **Body**:
     ```json
     {
         "equation": "x + y"
     }
     ```

2. **Evaluate an Equation**

   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/equations/{equationId}/evaluate`
   - **Body**:
     ```json
     {
         "variables": {
             "x": 5,
             "y": 3
         }
     }
     ```

3. **Retrieve All Equations**

   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/equations`

## Contributing

If you would like to contribute to the project:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature-name`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add new feature'`)
5. Push to the branch (`git push origin feature/your-feature-name`)
6. Create a new pull request

