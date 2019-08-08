# hackathon-aplana-2019-qualifying
hackathon-aplana-2019-qualifying

important for development purposes in IntelliJ IDEA:
1. install Lombok support plugin
2. set Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing

build command line:
mvn clean install

run command line:
mvn clean install spring-boot:run

service home page:
http://localhost:8081

service usage:
http://localhost:8081/create

db usage:
http://localhost:8081/h2
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:~/vingen
User Name: sa
Password: