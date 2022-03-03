
# Robot Apocalypse

## Requirements
- JDK ~> 1.8 / 11 
- Spring Boot ~> 2.5.2
- Maven \>= 3.6.3
- IDE of your choice (e.g Intellij/Eclipse/STS etc)

## Database
- H2
---

### Project structure
This project consists of 1 deploy-able artefacts:

### Deploy-able
- robot-apocalypse


```
+-- robot-apocalypse
+-- .idea
+-- .mvn
|   +-- src/
|   +-- .gitignore
|   +-- pom.xml
+-- .gitignore
+-- pom.xml
+-- README.md
```
---

### Local development
Run the below commands from the project root:
1. Build the project
```
mvn -B  clean install --file pom.xml
```

2. Open the dev environment application file using an editor of choice
```
vi application.properties
```
3. credentials for the h2 database under the datasource section
```
datasource:
    username: sa
    password: password
    url: jdbc:h2:file:~/robotapocalypsedb
    
```

4. Run the application 
```
mvn spring-boot:run
```
5. Navigate into the robot-apocalypse/src/main/resources/
```
cd robot-apocalypse/src/main/resources/
```
6. Update dev profile with credentials for the H2 database under the datasource section
```
datasource:
    username: sa
    password: password
    url: jdbc:h2:file:~/robotapocalypsedb
```
7. Navigate into robot-apocalypse from the root directory in order to run the service
```
cd robot-apocalypse
```
8. Run the application
```
mvn spring-boot:run
```

9. Access the application documentation on swagger using the below url
```
http://localhost:8080/swagger-ui.html#
```


10. Access the application H2 database on the browser using the below url
```
localhost:8080/h2-console/

```

localhost:8080/h2-console/

**Notes:**
- The service is exposed on `http://localhost:8080` by default.
- Spring Boot uses the `application-*.properties` file(s).
- Improvements would be to deploy the jar in docker file 
- 

---
