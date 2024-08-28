# embedded-tomcat
> How embedded tomcat works?

## 1. How to run embedded Tomcat with Plain Java
### 1-1. [Download/unzip Tomcat library](https://tomcat.apache.org/): Required jar files
- tomcat-embed-core.jar
- tomcat-embed-jasper.jar
- tomcat-embed-el.jar
- annotations-api.jar

```tree
project_root/
    ├── src/
    │   └── Main.java
    └── lib-tomcat/
        ├── tomcat-embed-core.jar
        ├── tomcat-embed-jasper.jar
        ├── tomcat-embed-el.jar
        └── annotations-api.jar
```
### 1-2. Write Main.java source code
```java
public static void main(String[] args) throws Exception {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.getConnector();

    Context ctx = tomcat.addContext("", null);
    Tomcat.addServlet(ctx, "hello", new HttpServlet() {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain");
            resp.getWriter().write("Hello, Embedded Tomcat!");
        }
    });
    ctx.addServletMappingDecoded("/hello", "hello");

    tomcat.start();
    log.info("Tomcat started successfully. Server is running. Try accessing http://localhost:8080/hello");
    tomcat.getServer().await();
}
```

### 1-3. Run and test Java class main

#### 1-3-1. Compile Java files
```shell
$ mkdir out
$ javac -d out -cp ".;out;lib-tomcat/*" src/Main.java
```
#### 1-3-2. Run Java
```shell
$ java -cp ".;out;lib-tomcat/*" -Dfile.encoding=UTF-8 Main
8월 28, 2024 11:36:18 오후 org.apache.coyote.AbstractProtocol init
정보: Initializing ProtocolHandler ["http-nio-8080"]
8월 28, 2024 11:36:19 오후 org.apache.catalina.core.StandardService startInternal
정보: Starting service [Tomcat]
8월 28, 2024 11:36:19 오후 org.apache.catalina.core.StandardEngine startInternal
정보: Starting Servlet engine: [Apache Tomcat/9.0.93]
8월 28, 2024 11:36:19 오후 org.apache.coyote.AbstractProtocol start
정보: Starting ProtocolHandler ["http-nio-8080"]
8월 28, 2024 11:36:19 오후 Main main
정보: Tomcat started successfully. Server is running. Try accessing http://localhost:8080/hello
```

#### 1-3-3. curl
```shell
$ curl -v http://localhost:8080/hello
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /hello HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.9.0
> Accept: */*
>
* Request completely sent off
< HTTP/1.1 200
< Content-Type: text/plain;charset=ISO-8859-1
< Content-Length: 23
< Date: Wed, 28 Aug 2024 14:22:27 GMT
<
{ [23 bytes data]
100    23  100    23    0     0    331      0 --:--:-- --:--:-- --:--:--   328Hello, Embedded Tomcat!
* Connection #0 to host localhost left intact
```