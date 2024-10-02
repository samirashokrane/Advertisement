
# Project Advertisement

## Description
This project is designed to calculate metrics and provide recommendations for advertisers based on impressions and clicks.

## Installation
1. Clone the repository.
2. Build the project using Maven.

```bash
git clone https://github.com/samirashokrane/Advertisement.git
mvn clean install
```

## Usage
Run the application using the following command:
```bash
java -jar target/advertisement-1.0.0.jar
```

## Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

## Database Setup
### MySQL Database
1. Install MySQL.
2. Create a new database:
```sql
CREATE DATABASE advertising_db;
```
3. Configure the database connection in `application.properties` or `application.yml`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/advertising_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update or create/drop for test
```




```


