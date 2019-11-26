# we are extending everything from tomcat:8.0 image ...
FROM tomcat:jdk11-openjdk
COPY target/student-form-1.war /usr/local/tomcat/webapps
EXPOSE 8080
