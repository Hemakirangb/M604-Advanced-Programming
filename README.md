#  Job Application Tracker 

> A full-stack job application management system built with **Java 17**, **Spring Boot 3.2**, **MySQL** (SQL), demonstrating a production-grade hybrid database architecture.


## Overview

The **Job Application Tracker** helps users manage their job search by tracking applications, interview stages, activity logs, and AI-generated resume feedback. It uses a **hybrid database** design:

| Database  | Technology | Purpose                                              |
|-----------|------------|------------------------------------------------------|
| SQL       | MySQL      | Structured data — users, companies, applications     |
---

## Tech Stack

| Layer       | Technology              |
|-------------|-------------------------|
| Language    | Java 17                 |
| Framework   | Spring Boot 3.2         |
| SQL DB      | MySQL 8.0               |
| ORM         | Spring Data JPA         |
| ODM         | Spring Data MongoDB     |
| Build Tool  | Maven                   |
| API Style   | RESTful JSON            |

---

## Architecture

```
┌─────────────────────────────────────────────┐
│              REST API (Spring Boot)          │
│         Controllers → Services → Repos       │
└────────────────┬───────────────┬────────────┘
                 │              
        ┌────────▼──────┐  
        │  MySQL (SQL)  │  
        │  - users      │  
        │  - companies  │  
        │  - job_apps   │  
        │  - interviews │  
        └───────────────┘  
```

---

## Project Structure

```
job-tracker/
├── src/main/java/com/jobtracker/
│   ├── JobTrackerApplication.java
│   ├── controller/
│   │   ├── JobApplicationController.java
│   │   ├── InterviewController.java
│   │   └── ActivityLogController.java
│   ├── service/
│   │   ├── JobApplicationService.java
│   │   ├── InterviewService.java
│   │   └── ActivityLogService.java
│   ├── repository/
│   │   ├── sql/
│   │   │   ├── JobApplicationRepository.java
│   │   │   ├── CompanyRepository.java
│   │   │   └── InterviewRepository.java
│   │   └── nosql/
│   │       ├── ActivityLogRepository.java
│   │       └── ResumeTipRepository.java
│   ├── model/
│   │   ├── sql/
│   │       ├── User.java
│   │       ├── Company.java
│   │       ├── JobApplication.java
│   │       └── Interview.java
│   │
│   ├── dto/
│   │   ├── JobApplicationDTO.java
│   │   └── ActivityLogDTO.java
│   └── exception/
│       ├── ResourceNotFoundException.java
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   └── application.properties
├── database/
│   ├── sql/
│   │   ├── 01_schema.sql
│   │   ├── 02_sample_data.sql
│   │   └── 03_queries.sql
│   
└── pom.xml
```

---

## Database Design

### SQL — MySQL (ER Summary)

```
users (1) ──────< job_applications (N)
companies (1) ──< job_applications (N)
job_applications (1) ──< interviews (N)
```


## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/job-tracker.git
cd job-tracker
```

### 2. Set up MySQL

```bash
mysql -u root -p < database/sql/01_schema.sql
mysql -u root -p job_tracker_db < database/sql/02_sample_data.sql
```


### 4. Configure application.properties

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD


### 5. Run the application

```bash
mvn spring-boot:run
```

App runs at **http://localhost:8080**

---

## API Reference

| Method | Endpoint                        | Description              |
|--------|---------------------------------|--------------------------|
| POST   | `/api/jobs`                     | Create application       |
| GET    | `/api/jobs`                     | Get all applications     |
| GET    | `/api/jobs/{id}`                | Get by ID                |
| GET    | `/api/jobs/status/{status}`     | Filter by status         |
| GET    | `/api/jobs/search?keyword=`     | Full-text search         |
| GET    | `/api/jobs/summary`             | Status aggregation       |
| PUT    | `/api/jobs/{id}`                | Update application       |
| DELETE | `/api/jobs/{id}`                | Delete application       |
| GET    | `/api/logs/{applicationId}`     | Get activity logs        |
| POST   | `/api/logs`                     | Add activity log         |
| GET    | `/api/logs/user/{userId}`       | All logs for a user      |

---

## Sample Queries

### SQL — CRUD + Aggregation

```sql
-- Count applications per status
SELECT status, COUNT(*) AS total FROM job_applications GROUP BY status;

-- Applications with upcoming interviews
SELECT ja.company, ja.position, i.interview_date, i.interview_type
FROM job_applications ja
JOIN interviews i ON ja.id = i.application_id
WHERE i.interview_date >= CURDATE()
ORDER BY i.interview_date;
```




## License

MIT License — see [LICENSE](LICENSE) for details.
