## Rover Service
This is the REST API part of the project. The frontend part is [here](..//web).

These would normally be in separate repositories with their own deployment pipelines, automation tests, etc.
In order to keep both in the interview repository provided they were separated into these _service_ and _web_ directories.

### Setup

#### Requirements
 - JDK8+
 - Maven

#### Checkout
```
git clone https://github.com/roverjobs/interview-200316-mc.git
cd interview-200316-mc/service
```

#### Build
```
mvn package
```

#### Start
```
mvn spring-boot:run
```

#### Try it out
 - List all owners: http://localhost:8080/owners
 - List all sitters ordered by rank: http://localhost:8080/sitters
 - List all sitters with a min score of 4 ordered by rank: http://localhost:8080/sitters?minRatingsScore=4
 - Or use Swagger: http://localhost:8080/swagger-ui.html
