## Requirements

- Java version 17.0.4.1
- Apache Maven 3.8.6

- Lombok processor Plugin
- **(Optional)** Protocol Buffer Plugin

> Note, this module was created only 
> for sake of testing Spring Boot + gRPC.
> So, here is no meaningful classes or logics.

## Spring Boot Integration with gRPC
> There is no official Spring Boot Starter, 
> so I am going to use community ones starter.

### gRPC starter for Server
There is the spring boot starter for gRPC Server
```xml
<dependency>
    <groupId>net.devh</groupId>
    <artifactId>grpc-server-spring-boot-starter</artifactId>
</dependency>
```

To change gRPC Server port just set the properties
```properties
grpc.server.port=9090
```

To create gRPC Service Class you need to
1) Extend protobuf service as before
2) Annotate class with @GrpcService

### gRPC starter for Client
There is spring boot starter for gRPC Client
```xml
<dependency>
    <groupId>net.devh</groupId>
    <artifactId>grpc-client-spring-boot-starter</artifactId>
</dependency>
```

To establish connection with the server 
you need to add such properties (value should be changed on yours)
```properties
grpc.client."some-service".address=static://localhost:9090
grpc.client."some-service".negotiation-type=plaintext
```

To inject the gRPC Stub just annotate field with 
```
@GrpcClient("some-service") SomeServiceBlockingStub blockingStub;
```