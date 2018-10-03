# tomee-microprofile-rest-client-demo
Type-Safe Approach to Invoking RESTful Services with MicroProfile Rest Client on TomEE

## Run prject arquillian tests
        mvn clean test
        
## Run with TomEE embedded
        mvn clean install tomee:run
Navigate to `http://localhost:8181/moviefun/api/movies/`
