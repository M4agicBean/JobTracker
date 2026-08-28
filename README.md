# JobTracker

![Status](https://img.shields.io/badge/status-in%20development-orange)
![Java](https://img.shields.io/badge/Java-21-b07219)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F)
![Angular](https://img.shields.io/badge/Angular-20-DD0031)

A job-application tracker built as a Kanban board. Add roles you're chasing, drag
them through the hiring pipeline, keep interviews and notes attached, and see
where your search is actually working.

---

## Stack

**Backend**: Java 21, Spring Boot 4.1, Spring Data JPA, Hibernate, Bean Validation, springdoc-openapi, H2

**Frontend**: _Angular 20, RxJS, Bootstrap 5 ~ work in progress_

## Upcoming Features

- Applications with company, seniority, work mode, salary range, tech tags and offer link
- Kanban board with drag & drop; illegal moves are refused by the server and the UI
- Full audit trail of every status change
- Interview scheduling with an upcoming-interviews view
- Filtering, sorting and pagination across all applications
- Stats: response rate, average time to first reply, applications per week, most-applied tech

## Running locally

Requires **JDK 21** and **Node 20.19+ / 22.12+ / 24**.

```bash
# backend - http://localhost:8080
cd back/app
./mvnw spring-boot:run

# frontend - http://localhost:4200
cd front/jobtracker
npm install
npm start
```

| | |
|---|---|
| UI | http://localhost:4200 |
| API docs | http://localhost:8080/swagger-ui.html |
| Database console | http://localhost:8080/h2-console |
