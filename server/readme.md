# SimpleDAO Management System Foundation · Back‑end

A back‑end project skeleton built on Spring Boot + SimpleDAO, with an integrated RBAC permission system, ready to use out of the box.

## Tech Stack & Environment Requirements

| Technology | Version |
|------------|---------|
| JDK        | 21 LTS |
| Spring Boot| 3.3.x   |
| Spring JDBC| 6.1.x   |
| SimpleDAO  | 1.2.1   |
| MySQL      | 8.0+    |
| Redis      | 6.2+    |
| Lombok     | 1.18.x  |
| JWT        | 4.4.x   |
| Maven      | 3.9+    |

## Highlights

- **Minimalist SQL Development**: Based on SimpleDAO's sensible layer over Spring JDBC, basic CRUD requires no handwritten SQL; complex SQL assembly is concise — no nested if statements or XML tags.
- **Lightning‑fast Startup**: Starts in just 3 seconds, restarts in 1 second. Extremely high debugging efficiency. Say goodbye to bloat.
- **Highly Maintainable Code**: Clean structure with extremely low cost for modifications and extensions.
- **Full‑stack Code Generation**: Integrated with the companion code generator; generate front‑end and back‑end code in one click, drastically reducing repetitive development work.
- **Automatic Audit Field Population**: `create_time` and `create_by` are populated automatically on insert; `update_time` and `update_by` are populated automatically on update.
- **Automated Soft Deletion**: The `dr` delete marker field is handled automatically — no manual soft‑delete logic required.
- **Superior Debugging Experience**: Complete SQL with parameter values is printed directly to the console, making issue diagnosis far more efficient.

## Quick Start

### 1. Create the Database

Log into MySQL and execute the following command:

```sql
CREATE DATABASE IF NOT EXISTS simple_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 2. Import the Database Script

```bash
mysql -u root -p simple_admin < ../init-schema.sql
```

### 3. Edit Configuration

Edit `src/main/resources/application.yml` and update the database and Redis connection details:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/simple_admin?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
  redis:
    host: localhost
    port: 6379
    password: your_redis_password   # leave blank if no password
```

### 4. Start the Application

```bash
mvn spring-boot:run
```

After startup, open `http://localhost:8888`. If you see the startup success log, the application is running normally.

## Built‑in Features

| Module | Functionality |
|--------|---------------|
| User Management | User info maintenance, status management, password reset, role & department assignment |
| Role Management | RBAC‑based functional authorization, data authorization, user association |
| Menu Management | Dual display via tree structure + list, supports dynamic routing and button‑level permissions |
| Department Management | Tree structure + list form to meet the hierarchical management needs of organizational structures |
| Dictionary Management | Centralized management of common dictionary data with dynamic configuration support |
| Operation Logging | AOP automatically records request parameters and return results for every operation |
| API Monitoring | Captures request duration for each endpoint to accurately pinpoint performance bottlenecks |
| API Security | JWT‑based endpoint authentication mechanism to safeguard access security |

## Development Tools

IntelliJ IDEA is recommended for back‑end development.

## FAQ

**Q: "Access denied for user" error on startup?**  
A: Check whether the database username and password in `application.yml` are correct.

**Q: "Unknown database" error on startup?**  
A: Perform Step 1 to create the database first, then Step 2 to import the script.

**Q: How can I change the logical‑delete marker field?**  
A: Configure `simple-dao.logic-delete.field` in `application.yml`. The default is `dr`.

**Q: Redis connection failure?**  
A: Check whether the Redis service is running and whether the Redis connection configuration in `application.yml` is correct.
