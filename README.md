# tomee-microprofile-rest-client-demo
Type-Safe Approach to Invoking RESTful Services with MicroProfile Rest Client on TomEE

## Build
        mvn clean install 

        
### Build specific module
        mvn clean install -pl movie-app/services -am

       
## Run movies service
        mvn clean install -pl movie-app/services tomee:run
Navigate to `http://localhost:8181/moviefun/api/movies/`

## Run cinema web app
        Go to cinema-app/cinema-webapp
        mvn clean install tomee:run
        Navigate to http://localhost:4444/cinema-webapp/api/cinema/movies
        

