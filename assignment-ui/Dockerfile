FROM fabric8/java-jboss-openjdk8-jdk:1.2.3

ENV JAVA_OPTIONS -Xmx256m 

EXPOSE 9091

RUN chmod -R 777 /deployments/
ADD target/assignment-ui.war /deployments/ 
