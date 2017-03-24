java -Dserver.port=8081 -jar target/hotel-service-client-0.0.1-SNAPSHOT.jar

ab -n 2000 -c 200 http://localhost:8080/search-hotels-async-non-blocking-polling

//TODO
Update to CXF 3.1.11 (will be Spring Boot 1.5.x ready)



