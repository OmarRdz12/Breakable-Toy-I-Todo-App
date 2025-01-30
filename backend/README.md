# Breakable-Toy-I-Todo-App (backend)
## Description
The backend API is divided into three essential parts:
- controllers: This is the section where the endpoint route and what is expected to be returned are placed.
- service: This is the connection that exists between the endpoint and what is going to be obtained.
- dao: This is the repository of task information, at this point is where access to tasks, insertions, deletions, updates, etc. is given.

### Technologies used
- Junit: tool for testing in the backend. (https://junit.org/junit5/)
- Mockito: tool for testing. (https://site.mockito.org/)

## Run and test
1. Run :
``` bash
    mvn spring-boot:run 
```
2. Test :
``` bash
    mvn test
```

## Ports used
- **Backend:** `9090`
