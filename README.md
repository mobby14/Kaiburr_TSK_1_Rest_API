# Kaiburr_TSK_1_Rest_API

## Project Overview

A **Spring Boot-based REST API** for task management with shell command execution capabilities, featuring MongoDB integration and comprehensive security measures.

---

## Architecture & Design

### **Technology Stack**
- **Framework**: Spring Boot 3.2.0
- **Database**: MongoDB
- **Language**: Java 17
- **Build Tool**: Maven
- **Security**: Command validation & injection prevention

### **Design Patterns**
- **Layered Architecture** (Controller → Service → Repository)
- **RESTful API Design**
- **Global Exception Handling**
- **Command Pattern** for task execution

---

## Project Structure Analysis

### **Core Components**

| Layer | Components | Responsibility |
|-------|------------|----------------|
| **Controller** | `TaskController` | HTTP request handling & response mapping |
| **Service** | `TaskService`, `CommandExecutorService`, `CommandValidatorService` | Business logic & command execution |
| **Repository** | `TaskRepository` | Data persistence operations |
| **Model** | `Task`, `TaskExecution` | Data representation |
| **Exception** | Custom exceptions & handler | Error management |

---

## Detailed Component Analysis

### **1. TaskController.java** 
**Status**: ✅ **Well Implemented**

#### **Key Features:**
- **RESTful endpoints** for full CRUD operations
- **Dual GET functionality** (single task + all tasks)
- **Command execution endpoint**
- **Comprehensive logging**
- **CORS enabled** for cross-origin requests

#### **Endpoints:**
| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| `GET` | `/tasks` | Get all tasks or by ID | 200 |
| `PUT` | `/tasks` | Create task (client ID) | 201 |
| `POST` | `/tasks` | Create task (server ID) | 201 |
| `DELETE` | `/tasks/{id}` | Delete task | 204 |
| `GET` | `/tasks/search` | Search by name | 200 |
| `PUT` | `/tasks/{id}/execute` | Execute task command | 200 |

### Output
![](https://github.com/mobby14/Kaiburr_TSK_1_Rest_API/blob/main/Screenshot%202025-10-20%20at%201.02.33%20PM.jpeg)
![](https://github.com/mobby14/Kaiburr_TSK_1_Rest_API/blob/main/Screenshot%202025-10-20%20at%201.03.12%20PM.jpeg)
![](https://github.com/mobby14/Kaiburr_TSK_1_Rest_API/blob/main/Screenshot%202025-10-20%20at%201.04.46%20PM.jpeg)
![](https://github.com/mobby14/Kaiburr_TSK_1_Rest_API/blob/main/Screenshot%202025-10-20%20at%201.13.58%20PM.jpeg)
