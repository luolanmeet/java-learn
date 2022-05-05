mvn clean package -DskipTests
cd target
java -javaagent:java-base-1.0-SNAPSHOT.jar=Hello -jar java-base-1.0-SNAPSHOT.jar
> 业务 jar 和 探针 jar 写一块了