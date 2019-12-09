# Sqreen Java Agent Owner technical exercise - Solution

## Exercise

The exercise consist in forcing a Tomcat server to send a new HTTP header to the user,  *without modifying the server source code nor configuration*:

When running a default Tomcat server, the following occurs:

    $ curl -I http://<my-docker-host>:8888/
    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: text/html;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Thu, 24 Mar 2016 16:23:31 GMT

We would like to make it return one additional header:

    $ curl -I http://<my-docker-host>:8888/
    HTTP/1.1 200 OK
    [...]
    X-Instrumented-By: Sqreen

This should be done by injecting a JAR into Tomcat (https://tomcat.apache.org/tomcat-6.0-doc/class-loader-howto.html).

## Solution
This solution is based on custom ClassLoader technique for Apache Tomcat. I created custom class loader - AgentClassLoader, which is replaces the JspServlet class with the JspServletWrapper class in loading stage. The JspServletWrapper is expands the implementation and adds an extra header in the http response.
This method is preferable, since it is not influenced to changes in the Tomcat code, which means - no need for infinite maintains.

There is another possible solution - based on class replacement technique. I found that the best class to modify headers is `org.apache.catalina.connector.Response`, so we can prepare a counterpart class with added functionality to extend the response headers http.

For Tomcat 5-7 the order loading jar files from lib directory is alphabetical. It uses sort. So we are able to replace any loaded class by another just pretaring jar with correct name to be in load order. 
However, for Tomcat 8-9 the jar files loading order is random decided by underlying file system, this means, that we cannot guarantee that class really will be replaced.
More about this can be read here: 
https://bz.apache.org/bugzilla/show_bug.cgi?id=57129
I decided to keep on first solution, because it's 100% workin on latest version of Tomcat.

## Prerequisites
- Operation System: Windows/Linux/MacOs
- Virtual Machine: Java 1.8 (or higher)
- Build tool: Maven 3.6.1 (or higher)
- Container: Docker 19.0 (or higher)

## Build and run
- Build project using Maven
```
$ mvn clean install
```
- Build docker container
```
$ docker build -t tomcat-agent:latest .
```
- Run docker container
```
$ docker run -p 8080:8080 -d tomcat-agent
```
- Test how it works
```
$ curl -I http://localhost:8080/
```
- Get result response
```
HTTP/1.1 200 
X-Instrumented-By: Sqreen
Content-Type: text/html;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 09 Dec 2019 19:02:40 GMT
```

## Credits

* **Valentin Zakharov** ([email@vzakharov.net](mailto:email@vzakharov.net))