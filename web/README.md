## Rover Web
This is the frontend part of the project. The REST API part is [here](../service).

These would normally be in separate repositories with their own deployment pipelines, automation tests, etc.
In order to keep both in the interview repository provided they were separated into these _web_ and _service_ directories.

### Setup

#### Requirements
 - JDK8+
 - Maven

#### Checkout
```
git clone https://github.com/roverjobs/interview-200316-mc.git
cd interview-200316-mc/web
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
http://localhost/search
