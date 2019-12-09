FROM tomcat:9.0.29

COPY target/*.jar lib/.
COPY target/classes/context.xml.default conf/Catalina/localhost/.

EXPOSE 8080

CMD ["catalina.sh", "run"]