FROM tomcat:9.0.29

COPY target/*.jar $CATALINA_HOME/lib/.
COPY target/classes/context.xml.default $CATALINA_HOME/conf/Catalina/localhost/context.xml.default

EXPOSE 8080

CMD ["catalina.sh", "run"]