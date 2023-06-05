## Requirements

- Java version 17.0.4.1
- Apache Maven 3.8.6


- **(Optional)** Protocol Buffer Plugin


## How to build proto files

gRPC autogenerate java code based on .proto files 
located in `src/main/proto` directory. So, to make it workable 
you need to compile project previously.

```
mvn clean compile
```

Then in your directory appears `target` folder, 
and if you set up proto plugin successfully 
you are going to see generated java classes.

> Note, that after each adding or updating of proto files 
> you need to recompile your project to get it.


Once you have compiled proto files, you may directly create 
Java, JS, CPP, and other files based on your proto. 
To make it just find inside `target\protoc-plugins\` 
the `protoc-3.21.7-*.exe` executable file and execute it.
Or you can install Protoc Tool to execute it from anywhere.

```
protoc-3.21.7-windows-x86_64.exe --h
```
```
protoc -h
```

Or to get, for example, Python File execute
```
protoc-3.21.7-windows-x86_64.exe --python_out=$DESTINATION *.proto
```
```
protoc --python_out=$DESTINATION *.proto
```


> Your file may be called in another way.

> You can install Protoc Tool 
> in order to call this command from anywhere
> using `protoc` keyword.


## Protoc Tool Installation

The simplest way to install 
the protocol compiler is to download 
a pre-built binary from 
[Protobuf GitHub Release Page](https://github.com/protocolbuffers/protobuf/releases).

Here you can find a detail 
instruction about the installation Protoc Tool 
[link](https://github.com/protocolbuffers/protobuf).


## Proto Types

| Java Type         | Proto Type |
|-------------------|------------|
| int               | int32      |
| long              | int64      |
| float             | float      |
| double            | double     |
| boolean           | bool       |
| String            | string     |
| byte[]            | bytes      |
| Collection / List | repeated   |
| Map               | map        |


## Default Values

| Proto Type        | Default             |
|-------------------|---------------------|
| any numeric type  | 0                   |
| bool              | false               |
| string            | empty string        |
| enum              | first value         |
| repeated          | empty list          |
| map               | wrapper / empty map |

