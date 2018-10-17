# tomee-microprofile-rest-client-demo
Type-Safe Approach to Invoking RESTful Services with MicroProfile Rest Client on TomEE

# To Do, General description of project structure.

## Build
        mvn clean install 

        
### Build specific module
        mvn clean install -pl movie-app/services -am

       
## Run movies service
        mvn clean install -pl movie-app/services tomee:run
Navigate to `http://localhost:8181/moviefun/api/movies/`

## ToDo
Instructions for to run movie-app/client and cinema-app